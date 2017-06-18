import pandas as pd
import matplotlib.pyplot as plt

class BabyNames(object):
    
    """
    The class passes in the dataframe to be used by the following functions.
    """
    
    def __init__(self,df):
        
        """
        
        The function initalizes self.df=df
        Args:
        df - the data frame to be passed into the class
        Returns:
        None
        
        """
        
        self.df = df

    def Count(self, state, year):

        """
        
        The function computes total births with 4 use cases:
        1. state and year both exist
        2. state and year both don't exist
        3. only year exists
        4. only state exists
        Function will group and search the dataframe differently depending on whether certain variables exist
        
        Args:
        state - the state you want to compute count for
        year - the year you want to compute count for
        
        Returns:
        A dataframe with the total count for state and year, depending on if these variables exist
        
        """

        if state and year:
            tab1=self.df.groupby(['state','year'],as_index=False)['count'].sum()
            return tab1[(tab1['state'] == state) & (tab1['year'] == year)].reset_index(drop=True)
        elif not state and not year:
            return self.df['count'].sum()      
        elif not state:
            tab1=self.df.groupby(['year'],as_index=False)['count'].sum()
            tab1=tab1[tab1['year'] == year].reset_index(drop=True)
            return tab1
        elif not year:
            tab1=self.df.groupby(['state'],as_index=False)['count'].sum()
            tab1=tab1[tab1['state'] == state].reset_index(drop=True)
            return tab1
    
    def Top10BabyNames(self, state,year):

        """
        
        The function creates a dataframe with top 10 male and female baby names for 4 use cases:
        1. state and year both exist
        2. state and year both don't exist
        3. only year exists
        4. only state exists
        Function will group and search the dataframe differently depending on whether certain variables exist
        
        The function fills in tab1 with is a pivot tale of sex, name, and count
        Mtab is a slice of tab1 that searches and sorts only for males
        Ftab is a slice of tab1 that searches and sorts only for females
        Finaltab is a merge of mtab and ftab based on rank for final output.
        
        Args:
        state - the state you want to compute count for
        year - the year you want to compute count for
        
        Returns:
        A dataframe with columns rank male female
        
        """
    
        tab1=pd.DataFrame()
    
        if state and year:
            tab1=self.df[(self.df['state'] == state) & (self.df['year'] == year)]
            tab1=tab1.groupby(['name','sex'],as_index=False)['count'].sum()
        if not state and not year:
            tab1=self.df.groupby(['name','sex'],as_index=False)['count'].sum()
        elif not state:
            tab1=self.df[self.df['year'] == year]
            tab1=tab1.groupby(['name','sex'],as_index=False)['count'].sum()
        elif not year:
            tab1=self.df[self.df['state'] == state]
            tab1=tab1.groupby(['name','sex'],as_index=False)['count'].sum()
           
        tab1=tab1.pivot(columns='sex',index='name',values='count')
        tab1.reset_index(inplace=True)
        tab1.columns.name = None
    
        mtab=tab1[['name','M']].sort_values(by=['M'], ascending=False).head(10)
        mtab['Rank']=range(1,mtab.shape[0]+1)
        mtab=mtab.rename(columns={'name': 'Male'})
        mtab=mtab[['Rank','Male']]
    
        ftab=tab1[['name','F']].sort_values(by=['F'], ascending=False).head(10)
        ftab['Rank']=range(1,ftab.shape[0]+1)
        ftab=ftab.rename(columns={'name': 'Female'})
        ftab=ftab[['Rank','Female']]
    
        finaltab=pd.merge(mtab, ftab, on='Rank' , how='inner')
        return finaltab

    def ChangeOfPopularity (self, fromYear, toYear, top):

        """
        
        Change of Popularity is measured in absolute not relative %  difference between from and to year.        
        
        The function will continually make you input values for the argument variables 
        if the following conditions are not met.
        1. if fromyear is less than min self.df year or toyear is more than max self.df year
        2. if fromyear or toyear is negative
        3. if toyear<=fromyear
        4. if top is more than the data frame # of rows, less than 0, or not an integer
        
        We have two dataframe fromtab which pulls data from starting year and totab which pulls data from ending year.
        These are then merged into combtab on name with an outer join, since some names don't exist in certain years.
        NaN values are filled with 0 and a new column abschange is computed which is ending - starting year births.
        Afterwards, this table is sorted from most increase to most decrease.
        Composttabb pulls from the head of combtab and combnegtab pulls from the tail.
        Top variable dictates how many rows are pulled from the head and tail of combtab.
        Both are then concatenated together along columns to produce final dataframe.
        
        Args:
        fromyear - starting year
        toyear - ending year
        top - how many you want to pull for most increase and decrease in popularity
        
        Returns:
        A dataframe with columns rank male female
        
        """
        
        while fromYear<min(self.df['year']) or fromYear<0 or fromYear>=toYear:
            fromYear = int(input('Starting year is incorrectly formated. Please reenter.'))
        
        while toYear>max(self.df['year']) or toYear<0 or toYear<fromYear:
            toYear = int(input('Ending year is incorrectly formated. Please reenter.'))

        fromtab=self.df[self.df['year'] == fromYear]
        fromtab= fromtab.groupby(['name'],as_index=False)['count'].sum()
    
        totab=self.df[self.df['year'] == toYear]
        totab= totab.groupby(['name'],as_index=False)['count'].sum()
    
        combtab=pd.merge(fromtab, totab, on='name' , how='outer')
        combtab.fillna(value=0, inplace=True)
        combtab['abschange']=combtab['count_y']-combtab['count_x']
        combtab=combtab.sort_values(by=['abschange'], ascending=False)
    
        while top>combtab.shape[0] or top<0 or not isinstance(top, (int)):
            top = int(input('Number of names to pull is incorrect. Please reenter.'))
        
        combpostab=pd.DataFrame(combtab['name'].head(top).reset_index(drop=True))
        combpostab=combpostab.rename(columns={'name': 'Increase Most in Popularity'})
        combnegtab=pd.DataFrame(combtab['name'].tail(top).reset_index(drop=True))
        combnegtab=combnegtab.rename(columns={'name': 'Decrease Most in Popularity'})
        combtab=pd.concat([combpostab, combnegtab], axis=1)
    
        return combtab

    def Top5NamesPerYear(self,year,sex):

        """
        
        An empty dataframe temptab is first created that serves as a skeleton for the final dataframe.
        2 use cases:
        1. If sex exists - search the dataframe for sex and year, 
        groupby state name and total count, print the appropriate message for table title
        2. If sex doesn't exist - search the dataframe for only the year, 
        groupby state name and total count, print the appropriate message for table title

        For the for loop, we search through tab1 for the corresponding state, reindex, 
        sort by count, and only take top 5 (statetab).
        The empty dataframe tab1 is then filled in using .ix which we can search 
        through dataframe labels and indexes
        for any column and row.
        
        Args:
        year - year you want to see the top 5 names for
        sex - gender of top 5 names you want to search for
        
        Returns:
        A dataframe with top 5 names with birth counts by state and sex, depending on if sex exists.
        
        """
    
        temptab=pd.DataFrame(index=self.df['state'].unique(), \
        columns=['Rank1','Num', 'Rank2', 'Num','Rank3', 'Num', 'Rank4', 'Num', 'Rank5', 'Num'])
        temptab=temptab.reset_index()
        temptab=temptab.rename(columns={'index': 'State'})
    
        tab1=pd.DataFrame()
            
        if sex:
            tab1=self.df[(self.df['sex'] == sex) & (self.df['year'] == year)]
            tab1=tab1.groupby(['state','name'],as_index=False)['count'].sum() 
            if sex=='M':
                print('Top Five Male Names for Births in %d' % (year))
            else:
                print('Top Five Female Names for Births in %d' % (year))
        else: 
            tab1=self.df[self.df['year'] == year]
            tab1=tab1.groupby(['state','name'],as_index=False)['count'].sum()
            print('Top Five Names for Births in %d' % (year))
                
        for state in self.df['state'].unique():
            statetab=tab1[tab1['state'] ==state].sort_values(by=['count'], ascending=False).head(5)
            statetab=statetab.reset_index(drop=True)
            temptab.ix[temptab['State']==state,'Rank1']=statetab.ix[0,'name']
            temptab.ix[temptab['State']==state,2]=statetab.ix[0,'count']
            temptab.ix[temptab['State']==state,'Rank2']=statetab.ix[1,'name']
            temptab.ix[temptab['State']==state,4]=statetab.ix[1,'count']
            temptab.ix[temptab['State']==state,'Rank3']=statetab.ix[2,'name']
            temptab.ix[temptab['State']==state,6]=statetab.ix[2,'count']
            temptab.ix[temptab['State']==state,'Rank4']=statetab.ix[3,'name']
            temptab.ix[temptab['State']==state,8]=statetab.ix[3,'count']
            temptab.ix[temptab['State']==state,'Rank5']=statetab.ix[4,'name']
            temptab.ix[temptab['State']==state,10]=statetab.ix[4,'count']
            
        return temptab

    def NamePopularityPlot (self, name, yearRange, state, sex):

        """
        
        Following exceptions are built in for yearRange tuple 
        that will continually ask for the corrept input:
        
        1. starting year less than min self.df year
        2. ending year more than max self.df year
        3. if any year is negative
        4. if any year is not an integer
        
        totaltab - searches through self.df that is within yearrange and state
            group by state and year and sum up birth counts
            rename count to totalcount
        nametab - searches through self.df that is within yearrange and state, 
            along with the matching name and gender
        finaltab - merge of nametab and totaltab using outer, since some years
            might not have babies born with certain names.
            proportion is calculated dividing namecount and totalcount
            NaNs are replaced with 0
            finaltab is reduced with an index for year and just the proportion
        
        plot is made from finaltab with title included with the appropriate message
        
        Args:
        name - baby name you want to create plot for
        yearRange - tuple of starting and ending year
        state - state you want to create plot for
        sex - gender of the name, since some might serve as both male and female names
        
        Returns: Nothing, plots are created inside the function as to avoid having to 
        assign a variable with method .show()
        
        """

        while yearRange[0]<min(self.df['year']) or yearRange[1]>max(self.df['year']) or \
        yearRange[0]<0 or yearRange[1]<0 or not isinstance(yearRange[0], (int)) or \
        not isinstance(yearRange[1], (int)) or yearRange[1]<yearRange[0]:
            fromyear = int(input('Year range is incorrectly formatted. Please reenter starting year'))
            toyear = int(input('Please reenter ending year'))
            yearRange=(fromyear,toyear)
        
        totaltab=self.df[(self.df['year']>=yearRange[0]) & (self.df['year']<=yearRange[1]) & (self.df['state']==state)]
        totaltab=totaltab.groupby(['state','year'],as_index=False)['count'].sum()
        totaltab=totaltab.rename(columns={'count': 'totalcount'})[['year','totalcount']]
    
        nametab=self.df[(self.df['sex'] == sex) & (self.df['name'] == name) &
        (self.df['year']>=yearRange[0]) & 
        (self.df['year']<=yearRange[1]) & (self.df['state']==state)]
        nametab=nametab.rename(columns={'count': 'namecount'})
    
        finaltab=pd.merge(nametab, totaltab, on='year', how='outer')
        finaltab['proportion']=finaltab['namecount']/finaltab['totalcount']
        finaltab.fillna(value=0, inplace=True)
        finaltab=finaltab['proportion']
        finaltab.index=totaltab['year']
    
        s=''
        if sex=='M':
            s='Change in Popularity for the male name %s in %s' % (name, state)
        else:
            s='Change in Popularity for the female name %s in %s' % (name, state)
        
        f=plt.figure()
        sp = f.add_subplot(111)
        sp.set_title(s)
        sp.plot(finaltab)   
        sp.set_ylabel('Proportion')
        sp.set_xlabel('Year')
        sp.plot(finaltab)   
        
        return

    def NameFlip(self, n):

        """
        
        First pulls data for females and males separately, and groups
        by year and name, summing up birth counts.
        
        Female and males are merged in outer join on name and year and 
        NaNs filled with 0. 
        The difference between male and females birthcounts and female and male birthcounts
        for each year/name combination is calculated.
        
        Sort this merged dataframe by name and year.
        
        Calculate what is the mean MFdiff and mean FMdiff that is greater than 0.
        This will be used to subset and find names that are more distinctly male or female than average.
        
        Take the average MFdiff and FMdiff across the names.
        If names reversed, then the mean MFdiff and FMdiff will be lower, maybe even negative.
        
        Append mtab and ftab into one table and sort again, since
        we are not distinguishing between female names that switched the most to male
        and male names that switched the most to female.
        
        Sort this table again by the ReverseMetric ascending.
        
        Plot to show these names actually did reverse and print top n
        names that reversed with the ReverseMetric.
        
        Args:
        n - number of top reversed baby names to return in list
        
        Returns: 
        List of top n reversed baby names with reverse metric. 
        Plots are created inside the function as to avoid having to 
        assign a variable with method .show()
        
        """
        
        ftab=self.df[self.df['sex']=='F'][['name','year','count']]
        ftab=ftab.groupby(['name','year'],as_index=False)['count'].sum()
        ftab=ftab.rename(columns={'count': 'FCount'})

        mtab=self.df[self.df['sex']=='M'][['name','sex','year','count']]
        mtab=mtab.groupby(['name','year'],as_index=False)['count'].sum()
        mtab=mtab.rename(columns={'count': 'MCount'})
    
        mtof=pd.merge(mtab,ftab, on=['name', 'year'], how='outer')
        mtof.fillna(value=0, inplace=True)
        mtof['MFdiff']=mtof['MCount']-mtof['FCount']
        mtof['FMdiff']=mtof['FCount']-mtof['MCount']
        mtof=mtof.sort_values(['name', 'year'], ascending=[True, True])
    
        mfdiffavg=mtof[mtof['MFdiff']>0]['MFdiff'].mean()
        fmdiffavg=mtof[mtof['FMdiff']>0]['FMdiff'].mean()
    
        mtab=mtof[['name','MFdiff']]
        mtab=mtab.ix[mtab['MFdiff']>0,:]
        mtab=mtab.groupby(['name'], as_index=False)['MFdiff'].mean()
        mtab=list(mtab[mtab['MFdiff']>=mfdiffavg]['name'])
        mtab=mtof[mtof['name'].isin(mtab)]
        mtab=mtab.groupby(['name'], as_index=False)['MFdiff'].mean()
        mtab=mtab.sort_values(['MFdiff'], ascending=True)
        mtab=mtab.rename(columns={'MFdiff': 'ReverseMetric'})
    
        ftab=mtof[['name','FMdiff']]
        ftab=ftab.ix[ftab['FMdiff']>0,:]
        ftab=ftab.groupby(['name'], as_index=False)['FMdiff'].mean()
        ftab=list(ftab[ftab['FMdiff']>=fmdiffavg]['name'])
        ftab=mtof[mtof['name'].isin(ftab)]
        ftab=ftab.groupby(['name'], as_index=False)['FMdiff'].mean()
        ftab=ftab.sort_values(['FMdiff'], ascending=True)
        ftab=ftab.rename(columns={'FMdiff': 'ReverseMetric'})

        finaltab=pd.concat([mtab,ftab])
        finaltab=finaltab.sort_values(['ReverseMetric'], ascending=True)
        finaltab=finaltab.reset_index(drop=True)
    
        for name in finaltab['name'].head(n):
            plottab=mtof[mtof['name']==name][['year','MCount','FCount']]
            plottab=plottab.set_index(['year'])
            f=plt.figure()
            sp = f.add_subplot(111)
            sp.set_title('Change in BirthCount for %s by Gender' % (name))
            sp.set_ylabel('BirthCount')
            sp.set_xlabel('Year')
            sp.legend(loc='upper left')
            sp.plot(plottab['MCount'],label='M') 
            sp.plot(plottab['FCount'],label='F')
        
        return finaltab.head(n)

"""
If baby library loaded correctly in another .py file, this will not print hello
since this will not import.
"""
if __name__ == "__main__":
    print("hello")

