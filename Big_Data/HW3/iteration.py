# -*- coding: utf-8 -*-
"""
Created on Wed May 17 22:05:22 2017

@author: mwcho
"""

import sys
import commands

kclusters=sys.argv[1]
iterations=sys.argv[2]

# string command to write pick random n rows (kclusters) to centroids.txt file in hdfs
initzstr='hdfs dfs -cat clusterin/*|shuf -n '
initzstr+=kclusters
initzstr+= ' |hdfs dfs -put - centroids.txt'

# initial centroids
print(commands.getstatusoutput(initzstr))

# string command to run the java Kmeans files
javastr='yarn jar ~/mr-app/target/mr-app-1.0-SNAPSHOT.jar Kmeans clusterin/* clusterout '
javastr+=kclusters

# string command to remove existing centroids.txt file, concatenate all output from reducer, and write
# output to centroids.txt with new centroids
rmvstr='hdfs dfs -rm centroids.txt'
mergestr='hdfs dfs -cat clusterout/* | hdfs dfs -put - centroids.txt'

# string command to remove the output folder for next iteration
rmvdirstr='hdfs dfs -rmr clusterout'

# run through iterations running linux commands
# print shows error messages in hadoop as well
for i in range(int(iterations)):
    
    print(commands.getstatusoutput(javastr))
    print(commands.getstatusoutput(rmvstr))
    print(commands.getstatusoutput(mergestr))
    print(commands.getstatusoutput(rmvdirstr))



