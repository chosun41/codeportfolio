# libraries/modules
import os
from pyspark import SparkContext
import re
from pyspark.mllib.stat import Statistics
from pyspark.mllib.linalg import Vectors
import pandas as pd
from scipy import stats
import math

# call from virtual environment
os.environ["PYSPARK_PYTHON"] = "./hw/home/mcho/.conda/envs/myvenv/bin/python"
os.environ["PYSPARK_DRIVER_PYTHON"] = "./hw/home/mcho/.conda/envs/myvenv/bin/python"
 
# set up sql and spark context
sc = SparkContext( )

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

#%% Part 1 - top 10 blocks 
    
# filter by last 3 years
# take only block of column index 3 and combine in a tuple with 1 to stand for 1 count
# reduce by key to group by key and sum up the 1s in an iterator
# takeOrdered does descending sort by -x[1] (values) and only takes 10 of the blocks with the largest sum
# of crimes
block1=parts.filter(lambda x: x[17] in ['2013','2014','2015']) \
            .map(lambda x: (x[3].split(" ")[0],1)) \
            .reduceByKey(lambda x, y: x + y).takeOrdered(10, key = lambda x: -x[1])

# takeordered returns a list instead of a rdd at the end so write the list to a txt file
thefile = open('Cho_2_1.txt', 'w')
for block, sumcrime in block1:
    thefile.write("\n".join(["%s %s" % (block, sumcrime)]) + "\n")
thefile.close()
    
#%% Part 2 - correlation    

# filter by last 5 years
# make tuple with ((beat, year), 1)
# sum by mapping to value of len of iterator
beatyearsum=parts.filter(lambda x: x[17] in ['2011','2012','2013','2014','2015'])  \
                 .map(lambda x: ((x[10], x[17]), 1)) \
                 .groupByKey().mapValues(lambda x:len(x))

# change tuple to (beat, (year, sumcrime))
# group by beat now and filter out beats that have crime in all 5 years
# and collect to put into list for filtering beatyearsum next
finalbeats=beatyearsum.map(lambda x: (x[0][0], (x[0][1],x[1])))  \
                      .groupByKey().mapValues(lambda x: len(x)) \
                      .filter(lambda x: x[1]==5).map(lambda x:x[0]).collect()


# filter beatyearsum by finalbeats list now 
# sort by key which is (beat, year)
# remap so year is key and group by year
# map the iterator value to tup2vec which transforms them into a dense vector
def tup2vec(x):
    tmpdict = {}
    veclst=[]
    for elem in x:
        tmpdict[elem[0]]=elem[1]
        veclst.append(tmpdict[elem[0]])
    return Vectors.dense(veclst)

beatsts=beatyearsum.filter(lambda x:x[0][0] in finalbeats).sortByKey(lambda x: x) \
                   .map(lambda x:(x[0][1],(x[0][0],x[1]))).groupByKey().mapValues(tup2vec)

# run correlation
crimemat = beatsts.map(lambda x:x[1])
corrmat = Statistics.corr(crimemat,method='pearson')

# sort the beatyearsum list
finalbeats.sort()

# put into pandas data frame, exclude correlation of 1 where beats match to itself
# use stack to get top 40 correlations and check against map
# might include duplicates
#1614 1631 0.9999117171200606 has highest correlation among adjacent beats
corrdf = pd.DataFrame(corrmat,index=finalbeats,columns=finalbeats)
mask = corrdf.isin([1])
corrdf=corrdf[~mask]
top40corr=corrdf.stack().nlargest(40)
top40corr.to_csv(r'Cho_2_2.txt', header=None, sep=' ')
#1614 1631 0.9999117171200606

#%% Part 3 - mayors    

# separate time periods between emanuel and daley
# Rahm Emanuel took office on 5/16/2011 so check date against to filter
yearemanuel=2011
monthemanuel=5
dayemanuel=16

def emanuelfltr(x):
    thisdate = x[2].split(" ")[0].split("/")
    thismonth=int(thisdate[0])
    thisday=int(thisdate[1])
    thisyear=int(thisdate[2])
    if thisyear > yearemanuel:
        return True
    elif thisyear < yearemanuel:
        return False
    else:
        if thismonth < monthemanuel:
            return False
        elif thismonth > monthemanuel:
            return True
        else:
            return thisday >= dayemanuel
        
#Filter mayors by inaguration date of Rahm Emanuel
emanuel = parts.filter(lambda x: emanuelfltr(x)==True)
daly = parts.filter(lambda x: emanuelfltr(x)==False)  

# get number of years for both mayors
emanuelnumyrs = emanuel.map(lambda x: x[2].split(" ")[0].split("/")[2]).distinct().count()
dalynumyrs = daly.map(lambda x: x[2].split(" ")[0].split("/")[2]).distinct().count()

# group by district, sum upby reduce by key, and divide sum to get average crime events per year per district
emanuelfin = emanuel.map(lambda x:(x[10],1)).reduceByKey(lambda x,y: x + y).map(lambda x:(x[0],x[1]/emanuelnumyrs))
dalyfin = daly.map(lambda x:(x[10],1)).reduceByKey(lambda x,y: x + y).map(lambda x:(x[0],x[1]/dalynumyrs))

# join dataset for comparison
mayorjoin = dalyfin.join(emanuelfin)

# take diff, find mean and standard deviation, get t stat and p value
crimediff = mayorjoin.map(lambda x: x[1][0] - x[1][1])
mean_diff = crimediff.mean()
sd_diff = crimediff.sampleStdev()
n = crimediff.count()
tstat = mean_diff / (sd_diff/ math.sqrt(n))
p_value = stats.t.sf(float(tstat), n-1)  
thefile2 = open('Cho_2_3.txt', 'w')
thefile2.write(str(p_value))
thefile2.close()

# stop spark context
sc.stop()
