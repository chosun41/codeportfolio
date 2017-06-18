# libraries/modules
import os
from pyspark.sql import SQLContext,Row
from pyspark import SparkContext
import re
from operator import itemgetter
import matplotlib 
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import numpy as np

# call from virtual environment
os.environ["PYSPARK_PYTHON"] = "./hw/home/mcho/.conda/envs/myvenv/bin/python"
os.environ["PYSPARK_DRIVER_PYTHON"] = "./hw/home/mcho/.conda/envs/myvenv/bin/python"
 
# set up sql and spark context
sc = SparkContext( )
sqlContext = SQLContext(sc)

# read from csv file
lines = sc.textFile('hdfs://wolf.iems.northwestern.edu/user/mcho/crimes.csv')

# remove header (might have to run this command a few times for it to register)
header = lines.first()
lines=lines.filter(lambda line: line != header)

# get rid of commas in double quotes and get rid of double quotes afterwards so columns don't spill into each other
lines=lines.map(lambda line: re.sub(',(?=[^"]*"[^"]*(?:"[^"]*"[^"]*)*$)',"",line))
lines=lines.map(lambda line: re.sub('\"',"",line))

# split by commas and map for each column name
parts=lines.map(lambda line: line.split(","))

# create data frame and register table with only id, month, and year
crimes = parts.map(lambda x: Row(ID = x[0], 
                month = x[2].split(" ")[0].split("/")[0], 
                year = x[17]))
schema = sqlContext.createDataFrame(crimes)
schema.registerTempTable('crimes')

# get the month, year, count from sparksql query
monthyearcnt=sqlContext.sql("SELECT month, year, count(*) as Count from crimes group by month, year")

# register table monthyearcnt
monthyearcnt.registerTempTable("monthyearcnt")

# get final average for each month across all years
avgcrime=sqlContext.sql("select month, avg(Count) as avg_crime from monthyearcnt group by month")
avgcrime.collect()
avgcrime.rdd.saveAsTextFile('hdfs://wolf.iems.northwestern.edu/user/mcho/avgcrime')

# collect columns separately to put into plot
month = avgcrime.map(itemgetter(0)).collect()
avg = avgcrime.map(itemgetter(1)).collect()

# create plot
xs = np.arange(len(month)) 
width = 1
fig = plt.figure()                                                               
ax = fig.gca()  #get current axes
ax.bar(xs, avg, width, align='center', edgecolor='black')
ax.set_xticks(xs)
ax.set_xticklabels(month)
ax.set_title('Avg. Chicago Crime per Month')
ax.set_xlabel('Month')
ax.set_ylabel('Avg. Crime')
plt.savefig('Cho_1.png')

# stop the spark context for next job
sc.stop()
 
# merge text output respectively for
os.system("hdfs dfs -getmerge avgcrime/ Cho_1.txt")

