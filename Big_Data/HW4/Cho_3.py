# libraries/modules
from pyspark.mllib.regression import LabeledPoint
from pyspark.mllib.tree import RandomForest
from pyspark import SQLContext
import re
from pyspark.sql import functions
from pyspark import SparkContext
import os

# call from virtual environment
os.environ["PYSPARK_PYTHON"] = "./hw/home/mcho/.conda/envs/myvenv/bin/python"
os.environ["PYSPARK_DRIVER_PYTHON"] = "./hw/home/mcho/.conda/envs/myvenv/bin/python"
 
# set up sql and spark context
sc = SparkContext( )
sqlContext = SQLContext(sc)

# read from csv file
lines = sc.textFile('hdfs://wolf.iems.northwestern.edu/user/mcho/crimes.csv')

# get rid of commas in double quotes and get rid of double quotes afterwards so columns don't spill into each other
lines=lines.map(lambda line: re.sub(',(?=[^"]*"[^"]*(?:"[^"]*"[^"]*)*$)',"",line))
lines=lines.map(lambda line: re.sub('\"',"",line))
lines=lines.map(lambda line: line.split(","))

# remove header (might have to run this command a few times for it to register)
header = lines.first()
lines=lines.filter(lambda line: line != header)

# initial df
df = sqlContext.createDataFrame(lines, header)

#get week from unix timestamp
df = df.withColumn('timestamp',functions.unix_timestamp('Date','MM/dd/yyyy').cast('timestamp'))
df = df.withColumn('week',functions.weekofyear('timestamp'))
df = df.withColumn('month',functions.month('timestamp'))
df = df.withColumn('day',functions.dayofmonth('timestamp'))
df = df.withColumn('IUCRstr',functions.regexp_extract(df['IUCR'],'[0-9]+',0))
df = df.withColumn('IUCRnum',df['IUCRstr'].cast("integer"))
df.registerTempTable('df')

# mean iucr is 1094 we'll use this 
meaniucr=df.agg({'IUCRnum':'mean'}).collect()
# max time is 5/19/2015 which is a Tuesday and in week 21, so since this won't produce a full week,
# weeks start from monday to sunday
# so use week 20 (full week) as what we are validating against
maxtime = sqlContext.sql("select max(timestamp) as maxtime from df")
example = sqlContext.sql("select week from df where month=5 and Year='2015' and day=17")

# column for severe crimes vs non sever crime boolean with mean iucr value as threhold
df = df.withColumn('Severe',df['IUCRnum']>1094)
df.registerTempTable('df')

# only restrict data to 2015 and take past 4 weeks (16,17,18,19) to predict week 20
df=sqlContext.sql("select * from df where Year='2015' and week>=16 and week<=20")
df.registerTempTable('df')

# separate into severe and nonsevere
severedf=sqlContext.sql("select * from df where Severe=True")
severedf.registerTempTable('severedf')
nonseveredf=sqlContext.sql("select * from df where Severe=False")
nonseveredf.registerTempTable('nonseveredf')

# makes columns for each week and join on beat for severe
svrweek16=sqlContext.sql("select Beat,count(*) as Week16cnt from severedf where Year='2015' and week=16 group by Beat")
svrweek17=sqlContext.sql("select Beat,count(*) as Week17cnt from severedf where Year='2015' and week=17 group by Beat")
svrweek18=sqlContext.sql("select Beat,count(*) as Week18cnt from severedf where Year='2015' and week=18 group by Beat")
svrweek19=sqlContext.sql("select Beat,count(*) as Week19cnt from severedf where Year='2015' and week=19 group by Beat")
svrweek20=sqlContext.sql("select Beat,count(*) as Week20cnt from severedf where Year='2015' and week=20 group by Beat")
svrdf = svrweek16.join(svrweek17, on="Beat").join(svrweek18, on="Beat").join(svrweek19, on="Beat").join(svrweek20, on="Beat")

## makes columns for each week and join on beat for nonsevere
nsvrweek16=sqlContext.sql("select Beat,count(*) as Week16cnt from nonseveredf where Year='2015' and week=16 group by Beat")
nsvrweek17=sqlContext.sql("select Beat,count(*) as Week17cnt from nonseveredf where Year='2015' and week=17 group by Beat")
nsvrweek18=sqlContext.sql("select Beat,count(*) as Week18cnt from nonseveredf where Year='2015' and week=18 group by Beat")
nsvrweek19=sqlContext.sql("select Beat,count(*) as Week19cnt from nonseveredf where Year='2015' and week=19 group by Beat")
nsvrweek20=sqlContext.sql("select Beat,count(*) as Week20cnt from nonseveredf where Year='2015' and week=20 group by Beat")
nsvrdf = nsvrweek16.join(nsvrweek17, on="Beat").join(nsvrweek18, on="Beat").join(nsvrweek19, on="Beat").join(nsvrweek20, on="Beat")

# make labeled points for response and predictors
# split into 80, 20 split
svrdffin = svrdf.map(lambda x: LabeledPoint(x[5], [x[1],x[2],x[3],x[4]]))
(svrtrain, svrtest) = svrdffin.randomSplit([0.8, 0.2])
nsvrdffin = nsvrdf.map(lambda x: LabeledPoint(x[5], [x[1],x[2],x[3],x[4]]))
(nsvrtrain, nsvrtest) = nsvrdffin.randomSplit([0.8, 0.2])

# use random forest train and predict
# find Rsquared and MSE

# severe model
svrmodel = RandomForest.trainRegressor(svrtrain, categoricalFeaturesInfo={},
                                       numTrees=50, featureSubsetStrategy="auto",
                                      impurity='variance', maxDepth=3, maxBins=32)
predsvr = svrmodel.predict(svrtest.map(lambda x: x.features))
meansvr = svrtest.map( lambda x: x.label).mean()
actpredsvr = svrtest.map(lambda x: x.label).zip(predsvr)
svrMSE = actpredsvr.map(lambda x: (x[0] - x[1]) * (x[0] - x[1])).sum() / float(svrtest.count())
svrSSE = actpredsvr.map(lambda x: (x[0] - x[1]) * (x[0] - x[1])).sum()
svrSST = actpredsvr.map(lambda x: (x[0] - meansvr) * (x[0] - meansvr)).sum()
svrr2 = 1 - svrSSE / svrSST
thefile = open('Cho_3_severe.txt', 'w')
thefile.write("R square: " + str(round(svrr2*100,2)) + " Test MSE: " + str(round(svrMSE,4)))
thefile.close()

# non-severe model
nsvrmodel = RandomForest.trainRegressor(nsvrtrain, categoricalFeaturesInfo={},
                                       numTrees=50, featureSubsetStrategy="auto",
                                      impurity='variance', maxDepth=3, maxBins=32)
prednsvr = nsvrmodel.predict(nsvrtest.map(lambda x: x.features))
meannsvr = nsvrtest.map( lambda x: x.label).mean()
actprednsvr = nsvrtest.map(lambda x: x.label).zip(prednsvr)
nsvrMSE = actprednsvr.map(lambda x: (x[0] - x[1]) * (x[0] - x[1])).sum() / float(nsvrtest.count())
nsvrSSE = actprednsvr.map(lambda x: (x[0] - x[1]) * (x[0] - x[1])).sum()
nsvrSST = actprednsvr.map(lambda x: (x[0] - meannsvr) * (x[0] - meannsvr)).sum()
nsvrr2 = 1 - nsvrSSE / nsvrSST
thefile2 = open('Cho_3_nonsevere.txt', 'w')
thefile2.write("R square: " + str(round(nsvrr2*100,2)) + " Test MSE: " + str(round(nsvrMSE,2)))
thefile2.close()

# stop spark context
sc.stop()

