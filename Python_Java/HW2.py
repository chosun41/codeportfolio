
# coding: utf-8

# ## Bubblesort
# 
# Bubblesort iterates through the list and check pairs of elements from beginning to end and see which one is bigger or smaller.
# It swaps the elements according to key and reverse. After it reaches the end. it starts back at the start of the list and goes through the same process but stops one index short of the previous iteration.

# In[26]:

from timeit import default_timer as timer


def BubbleSort(A, reverse=False, key=lambda x:x):

    start = timer()
    
    swapcount=0
    compcount=0
    
    # make copy of the data so other functions can use A as well
    M=A[:]

    if reverse==True:
        # do comparison i times in range(k), k decreases by 1 until it reaches 0
        for k in range(len(M)-1,0,-1):
            for i in range(k):
                compcount+=1 # next step is a comparison so always increment here
                if key(M[i])<key(M[i+1]):
                    M[i],M[i+1]=M[i+1], M[i]
                    swapcount+=1
        end = timer()

    else:
        # do comparison i times in range(k), k decreases by 1 until it reaches 0
        for k in range(len(M)-1,0,-1):
            for i in range(k):
                compcount+=1 # next step is a comparison so always increment here
                if key(M[i])>key(M[i+1]):
                    M[i],M[i+1]=M[i+1], M[i]
                    swapcount+=1
        end = timer()

    return (M,compcount, swapcount, end-start)


# ## Mergesort
# 
# Mergesort recursively breaks down a list into halves (left and right) until it reaches single elemnts which it compares and merges back into bigger lists. On occassion, we might see that there are no more elements in a left or right sublist, thus we just fill in the rest of the non empty list into the list copy M. Here we have broken up merge and sort into two different 

# In[27]:

from timeit import default_timer as timer

def MergeSort(A, reverse= False, key=lambda x:x):
    
    start=timer()
    M=A

    if len(M)>1:
        mid=len(M)//2
        lefthalf=M[:mid]
        righthalf=M[mid:]
        lM,lcompcount, lswapcount,ltime=MergeSort(lefthalf, reverse, key)
        rM,rcompcount, rswapcount,rtime=MergeSort(righthalf, reverse, key)
        i=j=k=0
        # descending order
        # indexes i for left, j for right, and k for actual sublist 
        if reverse==True:
            compcount=lcompcount+rcompcount
            swapcount=lswapcount+rswapcount
            while i<len(lefthalf) and j<len(righthalf):
                compcount+=1
                if key(lefthalf[i])<key(righthalf[j]):
                    M[k]=righthalf[j]
                    swapcount+=1
                    j+=1
                else:
                    M[k]=lefthalf[i] 
                    i+=1   
                k+=1
             # if your only stuck with a lefthalf or right half at a recursion sublevel
             # just fill in the rest when merging
            while i<len(lefthalf):
                  M[k]=lefthalf[i] 
                  i+=1
                  k+=1
            while j<len(righthalf):
                  M[k]=righthalf[j]
                  j+=1
                  k+=1
            end=timer()
            return(M, compcount,swapcount, end-start)
            
        # ascending order
        # indexes i for left, j for right, and k for actual sublist              
        else:
            compcount=lcompcount+rcompcount
            swapcount=lswapcount+rswapcount
            while i<len(lefthalf) and j<len(righthalf):
                compcount+=1 # next step is a comparison so always increment here
                if key(lefthalf[i])>key(righthalf[j]):
                    M[k]=righthalf[j]
                    swapcount+=1
                    j+=1
                else:
                    M[k]=lefthalf[i] 
                    i+=1  
                k+=1

             # if your only stuck with a lefthalf or right half at a recursion sublevel
             # just fill in the rest when merging
            while i<len(lefthalf):
                  M[k]=lefthalf[i] 
                  i+=1
                  k+=1
            while j<len(righthalf):
                  M[k]=righthalf[j]
                  j+=1
                  k+=1   
            end=timer()
            return(M, compcount,swapcount, end-start)
    else:
        end=timer()
        return (M, 0, 0, end-start)


# Both functions returns tuples of (list, count of comparisons, count of swaps, computation time) that can be called by index.
# Both functions have parameters for list, reverse, key with reverse=False and key=lambda x:x as defaults.
# Objects can be compared by passing them as an argument to key in both functions and one can specficy their own function for key i.g. lambda, len, etc.

# In[28]:

# randomly generated numbers
import random
randnumlist=random.sample(range(100), 10)
print("Random number list:",randnumlist)

print("\r\n")

print("Testing random number list in ascending order")

print("\r\n")

bubbleascnum=BubbleSort(randnumlist)
print("Sorted bubble sort list is:", bubbleascnum[0])
print("Total number of comparisons is:", bubbleascnum[1])
print("Total number of swaps is:", bubbleascnum[2])
print("Total time for function is:", bubbleascnum[3])

print("\r\n")

mergeascnum=MergeSort(randnumlist)
print("Sorted merge sort list is:", mergeascnum[0])
print("Total number of comparisons is:", mergeascnum[1])
print("Total number of swaps is:", mergeascnum[2])
print("Total time for function is:", mergeascnum[3])


# In[29]:

# randomly generated strings
def randomword():
    nouns = ("a", "ab", "abc", "abcd", "abcde")
    verbs = ("b", "bc", "bcd", "bce", "bbd") 
    adj = ("fs", "fl", "flgh", "zz", "yyst")
    sentlist=[nouns,verbs,adj]
    sentcs=''.join([random.choice(i) for i in sentlist])
    return sentcs

print("\r\n")

randwordlist=[]
for i in range(10):
    randwordlist.append(randomword())
print("Random string list:",randwordlist)

print("\r\n")

print("Testing string list in descending order (reverse=True)")

print("\r\n")

bubbldescnum=BubbleSort(randwordlist, True)
print("Sorted bubble sort list is:", bubbldescnum[0])
print("Total number of comparisons is:", bubbldescnum[1])
print("Total number of swaps is:", bubbldescnum[2])
print("Total time for function is:", bubbldescnum[3])

print("\r\n")

mergeadescstr=MergeSort(randwordlist, True)
print("Sorted merge sort list is:", mergeadescstr[0])
print("Total number of comparisons is:", mergeadescstr[1])
print("Total number of swaps is:", mergeadescstr[2])
print("Total time for function is:", mergeadescstr[3])


# Testing string list in descending order (reverse=True) with length key -> Should show that function can take comparable \
# objects and lambda functions -> Those strings with larger length appear toward the front, those with equal length appear in order of the original list

# In[30]:

randwordlist2=[]
for i in range(10):
    randwordlist2.append(randomword())
print("Random string list:",randwordlist2)

print("\r\n")

bubbldescnum=BubbleSort(randwordlist2, True, len)
print("Sorted bubble sort list is:", bubbldescnum[0])
print("Total number of comparisons is:", bubbldescnum[1])
print("Total number of swaps is:", bubbldescnum[2])
print("Total time for function is:", bubbldescnum[3])

print("\r\n")

mergeadescstr=MergeSort(randwordlist2, True, len)
print("Sorted merge sort list is:", mergeadescstr[0])
print("Total number of comparisons is:", mergeadescstr[1])
print("Total number of swaps is:", mergeadescstr[2])
print("Total time for function is:", mergeadescstr[3])


# In[ ]:

## Performance test
#Running a test on 10 randomly generated lists of strings of 10 words each and testing bubblesort and mergesort on both. Plot
#and show summary statistics for performance.


# In[36]:

from statistics import mean, stdev
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
get_ipython().magic('matplotlib inline')

mergesortperf=[]
bubblesortperf=[]
def perf():
    for i in range(10):
        randwordlist3=[]
        for j in range(10):
            randwordlist3.append(randomword())
        print("Random string list:",randwordlist3)  
        bubblesortperf.append(BubbleSort(randwordlist3, True, len)[3])
        mergesortperf.append(MergeSort(randwordlist3, True, len)[3])
        
perf()

print("\r\n")

print("Mergesort times:", mergesortperf)

print("\r\n")

print("Bubblesort times:",bubblesortperf)

print("\r\n")

plt.plot(range(1,11), mergesortperf)
plt.plot(range(1,11), bubblesortperf)
plt.ylabel("computation time")
plt.xlabel("List No.")
plt.legend(['mergesort', 'bubblesort'], loc='upper right')  

sumtable=pd.DataFrame({'Max': [max(mergesortperf),max(bubblesortperf)], 'Min':[min(mergesortperf),min(bubblesortperf)], 'Mean':[mean(mergesortperf),mean(bubblesortperf)],'Stdev':[stdev(mergesortperf),stdev(bubblesortperf)]},                       index=(['Mergesort','Bubblesort']))
print(sumtable)

