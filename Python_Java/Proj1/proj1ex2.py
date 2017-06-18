
# coding: utf-8

# In[2]:

import pandas as pd
import numpy as np
import pymysql

"""
connection string to access world database. please change with your own credentials
"""

cnx = pymysql.connect(user='chosun1',       password='2bamaster!',       host= '127.0.0.1',       port=3306,       db='world',       autocommit=True)

"""
need to first query tables country, city, and country before using pandas to transform the data
"""

country= pd.read_sql_query('select * from country', con=cnx)
city=pd.read_sql_query('select * from city', con=cnx)
countrylanguage=pd.read_sql_query('select * from countrylanguage', con=cnx)

"""
select all from country where population is greater than 50 million, sort descending by population, reindex to match
sql output, and only print out 10 rows
"""

q1panda=country[(country.Population > 50000000)]
q1panda.sort_values(by=['Population'], ascending=False, inplace=True)
q1panda=q1panda.reset_index(drop=True)
print("Problem 1 pandas")
print(q1panda.head(10))
print("\r\n")

q1sql= pd.read_sql_query('select * from country where population > 50000000  order by population DESC limit 10', con=cnx)
print("Problem 1 sql")
print(q1sql)
print("\r\n")

"""
select all from country where population is greater than 0. 
make two dataframes - 
df1 where group by continent and count rows for each continent
df2 where group by continent and sum up population
merge df1 and df2 by innerjoin on continent
sort values by continent in ascending alphabetical order as it is the 1st column indicated by order by 1
"""

q2panda=country[(country.Population > 0)]
df1=pd.DataFrame(q2panda.groupby('Continent', as_index=False)['LocalName'].count()) 
df1.rename(columns={'LocalName': 'Number_Countries'}, inplace=True)
df2= pd.DataFrame(q2panda.groupby('Continent', as_index=False)['Population'].sum())
df3=pd.merge(df1,df2, how='inner',on='Continent')
df3.sort_values(by=['Continent'], ascending=True, inplace=True)
print("Problem 2 pandas")
print(df3)
print("\r\n")

q2sql= pd.read_sql_query('select Continent, count(*) As Number_Countries, sum(population) As Population from country where population > 0 group by Continent order by 1 ASC', con=cnx)
print("Problem 2 sql")
print(q2sql)
print("\r\n")

"""
df1-
find only entries in city table with countrcode equal to USA and columns name, population, and countrycode and place in df2
rename name variable to city
sort df1 by descending population

df2-
dataframe of county code

q3panda-

merge df1 and df2 by innerjoin on countrycode=code

only keep city and population

print only 10 entries
"""

df1=city.query('CountryCode=="USA"')[['Name','Population','CountryCode']]
df1.rename(columns={'Name': 'City'}, inplace=True)
df1.sort_values(by=['Population'], ascending=False, inplace=True)
df2=pd.DataFrame(country['Code'])
q3panda=pd.merge(df1, df2, left_on='CountryCode', right_on='Code' , how='inner')
q3panda=q3panda[['City','Population']]
print("Problem 3 pandas")
print(q3panda.head(10))
print("\r\n")

q3sql= pd.read_sql_query('select city.Name As City, city.population from city inner join country ON city.CountryCode = country.code where country.code = "USA" order by city.population DESC limit 10', con=cnx)
print("Problem 3 sql")
print(q3sql)
print("\r\n")

"""
df1-
from countrlanguage table, only keep columns countrycode, language, and percentage, and where isoffical is true

df2-
from country, only keep columns name, code, and population

q4panda -
merge df2 and df1 by innerjoin on code and countrycode
calculate '(Percentage * population) / 100'
only keep name, language, and '(Percentage * population) / 100'
sort by '(Percentage * population) / 100' descending which is the 3rd column as indicated by order by 3
reset index to match sql output
only print out 10 rows
"""

df1=countrylanguage.query('IsOfficial=="T"')[['CountryCode','Language','Percentage']]
df2=country[['Name','Code','Population']]
q4panda=pd.merge(df2, df1, left_on='Code', right_on='CountryCode' , how='inner')
q4panda['(Percentage * population) / 100']=q4panda['Percentage']*q4panda['Population']/100
q4panda=q4panda[['Name','Language','(Percentage * population) / 100']]
q4panda.sort_values(by=['(Percentage * population) / 100'], ascending=False, inplace=True)
q4panda=q4panda.reset_index(drop=True)
print("Problem 4 panda")
print(q4panda.head(10))
print("\r\n")

q4sql=pd.read_sql_query('select country.Name, Language, (Percentage * population) / 100 from countrylanguage inner join country on countrylanguage.CountryCode = country.code where IsOfficial = True order by 3 DESC limit 10', con=cnx)
print("Problem 4 sql")
print(q4sql)
print("\r\n")

"""
df1-
from countrlanguage table, only keep countrycode, language, and percentage

df2-
from country, only keep code and population

q5panda -
merge df2 and df1 by innerjoin on code and countrycode
calculate '(Percentage * population) / 100'
only keep language and calculate '(Percentage * population) / 100'
groupby language and sum up calculate '(Percentage * population) / 100'
rename calculate '(Percentage * population) / 100' as 'sum((Percentage * population) / 100)'
sort by 'sum((Percentage * population) / 100)' descending as indicated by order by 2
reset index to match sql output
only print 5 rows
"""

df1=countrylanguage[['CountryCode','Language','Percentage']]
df2=country[['Code', 'Population']]
q5panda=pd.merge(df2, df1, left_on='Code', right_on='CountryCode' , how='inner')
q5panda['(Percentage * population) / 100']=q5panda['Percentage']*q5panda['Population']/100
q5panda=q5panda[['Language', '(Percentage * population) / 100']]
q5panda=q5panda.groupby('Language', as_index=False)['(Percentage * population) / 100'].sum()
q5panda.rename(columns={'(Percentage * population) / 100': 'sum((Percentage * population) / 100)'}, inplace=True)
q5panda.sort_values(by=['sum((Percentage * population) / 100)'], ascending=False, inplace=True)
q5panda=q5panda.reset_index(drop=True)
print("Problem 5 panda")
print(q5panda.head(5))
print("\r\n")

q5sql= pd.read_sql_query('select Language, sum((Percentage * population) / 100) from countrylanguage inner join country ON countrylanguage.CountryCode = country.code group by Language order by 2 desc limit 5', con=cnx)
print("Problem 5 sql")
print(q5sql)
print("\r\n")


