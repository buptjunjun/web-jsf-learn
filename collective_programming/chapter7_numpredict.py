from random import random,randint
import math

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
    
    if price < 0:price = 0
    return price
def wineset1():
    rows = []
    for i in range(300):
        # create a random age and rating
        rating = random()*50 + 50
        age=random()*50
        
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
    return math.e**(dist**2/(2*sigma**2))
 
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
    # return the average of 
    return error / len(testset)

# now process cross validation
def crossvalidate(algf ,data, trails=100,testp=0.15):
    errors = 0
    for i in range(trails):
        trainset, testset = dividedata(data,testp)
        errors+= testalgorithm(algf,trainset,testset)
    return errors/trails





dataset1 = wineset1()
print(dataset1)
input = [92.01363472818139, 47.079372404427744]
result = knnestimate(dataset1,input)
print(result)

wresult = weightedknn(dataset1,input)
print(wresult)

trainset,testset = dividedata(dataset1)
print(len(trainset))
print(len(testset))

print("-------------------------")
def knn1(d,v): return  weightedknn(d,v,1)
def knn2(d,v): return  weightedknn(d,v,2)
def knn3(d,v): return  weightedknn(d,v,3)
def knn4(d,v): return  weightedknn(d,v,4)

error1 = crossvalidate(knn1,dataset1)
print("error1 = %d " % error1 )
error2 = crossvalidate(knn2,dataset1)
print("error2 = %d " % error2 )
error3 = crossvalidate(knn3,dataset1)
print("error3 = %d " % error3 )
error4 = crossvalidate(knn4,dataset1)
print("error4 = %d " % error4 )