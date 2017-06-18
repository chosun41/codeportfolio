# -*- coding: utf-8 -*-
"""
Created on Wed Feb  8 03:39:49 2017

@author: Michael
#"""
#
import baby as babe
import zipfile, urllib.request, shutil
import glob
import pandas as pd

"""
following code uses urllib and zipfile library to pull zip file from web 
and extract all the text files
"""

url = "https://www.ssa.gov/oact/babynames/state/namesbystate.zip"
file_name = 'myzip.zip'

with urllib.request.urlopen(url) as response, open(file_name, 'wb') as out_file:
    shutil.copyfileobj(response, out_file)
    with zipfile.ZipFile(file_name) as zf:
        zf.extractall()
     
"""
for any file that has a .txt ending, we read and append these individual dataframes
into frames list. afterwards we concatenate rowwise all these dataframes for
each state into a dataframe called df
"""
        
frames = []
columns = ['state','sex','year','name', 'count']
for file in glob.glob("*.txt"):
    new_frame=pd.read_csv(file,names=columns)
    frames.append(new_frame)
df=pd.concat(frames, ignore_index=True)

"""
df is passed into the BabyNames class from the babe.py and assigned
to the variable checklib
"""

checklib=babe.BabyNames(df)

"""
test cases for Count depending on whether state and year exist
"""

print(checklib.Count('WY',1910))
print("\r\n")
print(checklib.Count('',''))
print("\r\n")
print(checklib.Count('',1910))
print("\r\n")
print(checklib.Count('WY',''))
print("\r\n")

"""
test cases for Top10BabyName depending on whether state and year exist
"""

print(checklib.Top10BabyNames('WY',1910))
print("\r\n")
print(checklib.Top10BabyNames('',''))
print("\r\n")
print(checklib.Top10BabyNames('',1910))
print("\r\n")
print(checklib.Top10BabyNames('WY',''))
print("\r\n")

"""
test cases for Top10BabyName depending on whether state and year exist
1. normal
2. negative starting year
3. starting year less than min year
4. ending year more than max year
5. number of rows to pull is more than rows of table
6. number of rows is negative
"""
print(checklib.ChangeOfPopularity (2014, 2015, 10))
print("\r\n")
print(checklib.ChangeOfPopularity (-2014, 2015, 10))
print("\r\n")
print(checklib.ChangeOfPopularity (1776, 2015, 10))
print("\r\n")
print(checklib.ChangeOfPopularity (2014, 2020, 10))
print("\r\n")
print(checklib.ChangeOfPopularity (2014, 2015, 100000000000))
print("\r\n")
print(checklib.ChangeOfPopularity (2014, 2015, -50))
print("\r\n")

"""
test case for Top5Names for male and female and neither
"""
print(checklib.Top5NamesPerYear(2015, 'M'))
print("\r\n")
print(checklib.Top5NamesPerYear(2015, 'F'))
print("\r\n")
print(checklib.Top5NamesPerYear(2015, ''))
print("\r\n")

"""
test case for NamePopularityPlot for James which exists in all years of year range 
and Jim which only exists in 1 year of the specified year range
"""
checklib.NamePopularityPlot ('James', (2000,2015), 'IL', 'M')
#a.show()
checklib.NamePopularityPlot ('Jim', (2000,2015), 'IL', 'M')
#b.show()

"""
test case for NameFlip for top 10
"""
print(checklib.NameFlip(10))
