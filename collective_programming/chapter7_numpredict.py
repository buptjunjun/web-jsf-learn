from random import random,randint
import math
import chapter4_groupTravel

# use wineprice and winset1 to create random wine price 
def wineprice(rating, age):
    peak_age=rating -50
    
    # Calculate the price on rating
    price = rating/2
    if age > peak_age:
        # past its peak goes abd in 5 years
        price  = price *(5-(peak_age))
    else:
      # increases to 5x original value as it approches its peak
      price = price * (5 * ((age+1)/peak_age))
    
    if price < 0:price = 2+5*random()
    return price
def wineset1():
    rows = []
    for i in range(300):
        # create a random age and rating
        rating = random()*50 + 60
        age=random()*50+2
        
        # get reference price 
        price  = wineprice(rating,age)
        
        # add some noise
        price *= (random()*0.4 + 0.8)
        
        # add to the dataset
        rows.append({'input':(rating,age),'result':price})
    return rows

# Define the distance between two Items
# 欧几里得距离
def euclidean(v1,v2):
    d = 0.0
    for i in range(len(v1)):
        d+= (v1[i] - v2[i])**2
    return math.sqrt(d)

# get one distance between sample and every other items in dataset
def getdistances(data,vect1):
    distancelist = []
    for i in range(len(data)):
        vect2 = data[i]['input']
        distancelist.append( (euclidean(vect1,vect2),i))
    distancelist.sort()
    return distancelist

# estimate price using KNN
def knnestimate(data,vect1,k = 3):
    distlist = getdistances(data,vect1)
    avg = 0.0
    
    # take the average of top k
    for i in range(k):
        # the index of a item in data
        idx = distlist[k][0]
        avg += data[i]['result']
    avg /= k
    return avg
       
# use weight to improve the KNN algorithm
# invere function
def inverseweight(dist,num=1.0,const=0.1):
    return num/(dist+const)


# use weight to improve the KNN algorithm
# substract  function
def subtracteweight(dist,const=1.0):
    if dist > const:
        return 0
    return const - dist 

# use weight to improve the KNN algorithm
#Gaussian function
def gaussion(dist,sigma=10.0):
    return math.e**(-dist**2/(2*sigma**2))
 
# weighted KNN using the 3 weighted function
def weightedknn(data,vect1,k=3,weightf=gaussion):
    distlist = getdistances(data,vect1)
    avg = 0.0
    totalweight=0.0
    
    # take the average of top k
    for i in range(k):
        # the index of a item in data
        idx = distlist[k][0]
        vect2 = data[i]['input']
        weight = euclidean(vect1,vect2)
        
        avg += weight * data[i]['result']
        totalweight += weight
        
    avg /= totalweight
    return avg


# crossover validation
def dividedata(dataset,testp=0.05):
    '''
    divide dataset to two parts ,5% for testing ,95% for training 
    '''
    testset = []
    trainset = []
    for row in dataset:
        if random() < testp:
            testset.append(row)
        else: 
            trainset.append(row)
    return trainset,testset


def testalgorithm(algf,trainset,testset): 
    '''
    test if the algf is effective 
    '''
    error = 0.0
    for row in testset:
        # the guess value of row['input']
        guessvalue = algf(trainset,row['input'])
        error  += (guessvalue-row['result'])**2;
    # return the average of  error
    return error / len(testset)

# now process cross validation
def crossvalidate(algf ,data, trails=100,testp=0.15):
    errors = 0
    for i in range(trails):
        trainset, testset = dividedata(data,testp)
        errors+= testalgorithm(algf,trainset,testset)
    return errors/trails

# normalize the  input data
def rescale(data,scale):
    scaleddata = []
    for row in data:
        scaled = [scale[i]*row['input'][i] for i in range(len(scale))]
        scaleddata.append({'input':scaled,'result':row['result']})
    return scaleddata

# using optimize algorithm to choose the most suitable scale vector
def createcostfunction(algf,data):
    def costf(scale):
        sdata=rescale(data,scale)
        return crossvalidate(algf,sdata,trails=10)
    return costf;


# guess the probability of the guess of price range fron a to b
# for example the probability between 10 and 100 
def probguess (data, vec1,low ,high, k = 3, weightf = gaussion):
    dlist = getdistances(data,vec1)
    nweight = 0.0
    tweight = 0.0
    
    for i in range(k):
        dist = dlist[i][0] # distance
        idx = dlist[i][1]  # data index
        weight = weightf(dist)
        v = data[idx]['result']
        
        # is this point in the range?
        if v >= low and v <=high:
            nweight += weight
        tweight+=weight
        
    if tweight == 0:return 0
        
    # the probability  is the weights in the range divided by all the weights
    return float(nweight / tweight)

# graph the probability 
def probabilitygraph(data,vec1,high,k=5,weightf=gaussion,ss = 5.0):
    # make the range for the prices
    t1 = arange(0.0,high,0.1)
    
    # get the probabilities of the entire range
    probs = [probguess(data,vec1,v,v+0.5,k,weightf) for v in t1]
    
    # smooth them by adding the gaussian of nearby probilites
    smoothed=[]
    for i in range(len(probs)):
        sv = 0.0
        for j in range(0,len(probs)):
            dist = abs(i-j)*0.5
            weight = gaussion(dist)
            sv += weight * probs[j]
        smoothed.append(sv)
        
    smoothed = array(smoothed)
    print(smoothed)
    plot(t1,smoothed)
    show()

dataset1 = wineset1();
print(dataset1)
input = [92.01363472818139, 47.079372404427744]
result = knnestimate(dataset1,input)
print(result)

wresult = weightedknn(dataset1,input)
print(wresult)

trainset,testset = dividedata(dataset1)
print(len(trainset))
print(len(testset))

#print("-------------------------")
#def knn1(d,v): return  weightedknn(d,v,1)
#def knn2(d,v): return  weightedknn(d,v,2)
#def knn3(d,v): return  weightedknn(d,v,3)
#def knn4(d,v): return  weightedknn(d,v,4)
#
#error1 = crossvalidate(knn1,dataset1)
#print("error1 = %d " % error1 )
#error2 = crossvalidate(knn2,dataset1)
#print("error2 = %d " % error2 )
#error3 = crossvalidate(knn3,dataset1)
#print("error3 = %d " % error3 )
#error4 = crossvalidate(knn4,dataset1)
#print("error4 = %d " % error4 )

#weightDomain = [(0,20)]*2
#costfunction = createcostfunction(knnestimate,dataset1)
#vect = chapter4_groupTravel.annealingoptimize(weightDomain,costfunction)
#print(vect)

prob =probguess(dataset1,[99,20],80,120)
print(prob)

from pylab import *
#a=array([1,2,3,4])
#b=array([4,2,3,1])
##plot(a,b)
##show( )
#t1=arange(0.0,10.0,1)
#plot(t1,sin(t1))
#show( )
print("------------------")
cost = weightedknn(dataset1,[79,40])
print(cost)
probabilitygraph(dataset1,[79,40],200)