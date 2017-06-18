# libraries/modules

import os
from pyspark.sql import SQLContext,Row
from pyspark import SparkContext
import datetime as dt
import re

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

# filter to just get crime where arrests where made
crimes_arrest=parts.filter(lambda x: x[8] == 'true')

# get month, weekday, and daytime hour
# create data frame and register table
crimes_arrest = crimes_arrest.map(lambda x: Row(ID = x[0], 
                month = x[2].split(" ")[0].split("/")[0], 
                weekday = dt.datetime.strptime(x[2].split(" ")[0], '%m/%d/%Y').strftime('%A'), 
                daytime = dt.datetime.strptime(x[2],'%m/%d/%Y %I:%M:%S %p').hour))
schema = sqlContext.createDataFrame(crimes_arrest)
schema.registerTempTable('arrest_bydt')

# sql to get month data, collect, and show
monthdat = sqlContext.sql('SELECT month, count(*) as monthly_arrests FROM arrest_bydt group by month')
monthdat.collect()
monthdat.rdd.saveAsTextFile('hdfs://wolf.iems.northwestern.edu/user/mcho/month')

# sql to get weekday data, collect, and show
weekdaydat = sqlContext.sql('SELECT weekday, count(*) as weekday_arrests FROM arrest_bydt group by weekday')
weekdaydat.collect()
weekdaydat.rdd.saveAsTextFile('hdfs://wolf.iems.northwestern.edu/user/mcho/weekday')

# sql to get daytime data, collect, and show
daytime = sqlContext.sql('SELECT daytime, count(*) as daytime_arrests FROM arrest_bydt group by daytime')
daytime.collect()
daytime.rdd.saveAsTextFile('hdfs://wolf.iems.northwestern.edu/user/mcho/daytime')

# stop the spark context for next job
sc.stop()
 
# merge text output respectively for
os.system("hdfs dfs -getmerge month/ Cho_4_month.txt")
os.system("hdfs dfs -getmerge weekday/ Cho_4_weekday.txt")
os.system("hdfs dfs -getmerge daytime/ Cho_4_daytime.txt")

