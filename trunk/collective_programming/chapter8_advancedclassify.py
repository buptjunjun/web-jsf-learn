from pylab import *
from random import random
import math

class matchrow:
    
    def __init__(self,row,allnum=False):
        '''
        allnum represents if all the value of the row is number
        '''
        if allnum:
            self.data=[float(row[i]) for i in range(len(row)-1)]
        else:
            self.data = row[0:len(row)-1]
        # if they are match 
        self.match = int(row[len(row)-1])

def loadmatch(f,allnum = False):
    rows=[]
    for line in open(f):
        line = line.strip("\n")
        #print(line)
        splitLine = line.split(",")
        #print(splitLine)
        rows.append(matchrow(splitLine,allnum))
    return rows

def plotagematches(rows):
    xdm,ydm= [r.data[0] for r in rows if r.match == 1],[r.data[1] for r in rows if r.match == 1]
     
    xdn,ydn=[r.data[0] for r in rows if r.match==0],[r.data[1] for r in rows if r.match==0]
    
    plot(xdm,ydm,"go")
    plot(xdn,ydn,"ro")
    show()


def lineartrain(rows):
    averages={}
    counts={}
    for row in rows:
        # get the class of this point  , class means mathed or not
        # cl = class
        cl = row.match
        
        averages.setdefault(cl, [0.0]*(len(row.data))) 
        counts.setdefault(cl, 0)
        
        # Add this point to the averages
        for i in range(len(row.data)):
            averages[cl][i] += float(row.data[i])
        
        #keep track of how many points in each class
        counts[cl] += 1
        
    print(averages)
    print(counts)
    # Divide sums by counts to get the averages
    for cl,avg in averages.items():
        for i in range(len(avg)):
            avg[i] /= counts[cl]
            
            
    return averages

def dotproduct(v1,v2):

    result = [ v1[i]*v2[i] for i in range(len(v1))]
    return sum(result)

# dot-product classify
#(X – (M0+M1)/2) . (M0-M1) = X.M0 – X.M1 + (M0.M0 – M1.M1)/2
def dpclassify(point,avgs):
    b=(dotproduct(avgs[1],avgs[1])-dotproduct(avgs[0],avgs[0]))/2
    y=dotproduct(point,avgs[0])-dotproduct(point,avgs[1])+b
    if y>0: return 0
    else: return 1   


def matchcount(interest1,interest2):
    '''
      the number of interests they both hvae
    '''
    l1 = interest1.split(":")
    l2 = interest2.split(":")
    x = 0
    for v in l1:
        if v in l2:x+=1
    return x

def yesno(v):
    '''
    map yes and no to number
    '''
    if v=='yes': return 1
    elif v == "no":return -1
    else:return 0

def miledistance(a1,a2):
    '''
    the distance of two people
    '''
    return 10* random() +10

def veclength(a):
    '''
    caculate the length of a vector ----|a|
    '''
    return sum([a[i] for i in range(len(a))])**.5

def loadnumerical():
    oldrows = loadmatch('matchmaker.csv')
    newrows = []
    for row in oldrows:
        d = row.data
        data = [float(d[0]),yesno(d[1]),yesno(d[2]),
                float(d[5]),yesno(d[6]),yesno(d[7]),
                matchcount(d[3],d[8]),
                miledistance(d[4],d[9]),
                row.match]
        newrows.append(matchrow(data))
    return newrows


def scaledata(rows):
    '''
    scale data to 0-1
    '''
    low=[99999999.0] * len(rows[0].data)
    high=[-99999999.0] * len(rows[0].data)
    
    # Find the lowest and highest values
    for row in rows:
        d = row.data
        for i in range(len(d)):
            if d[i] < low[i] : low[i] = d[i]
            if d[i] > high[i]:high[i] = d[i]
    
    # Create a function that scales data
    def scaleinput(d):
        return [(d.data[i] - low[i]) / (high[i] - low[i]) for i in range(len(low))]
    
    # Scale all the data
    newrows = [matchrow(scaleinput(row) + [row.match]) for row in rows]
    
    # return the new data and the function
    return newrows,scaleinput
    
def rbf(v1,v2,gamma = 20):
    '''
    rbf = radial-basis function
    '''
    dv = [v1[i] - v2[i] for i in range(len(v1))]
    l = veclength(dv)
    return  math.e **(-gamma*l)


def nlclassify(point,rows,offset,gamma=10):
    '''
    nlclassify = none linear classify
    '''
    sum0 = 0.0
    sum1 = 0.0
    count0 = 0
    count1 = 0
    for row in rows:
        if row.match == 0:
            sum0+=rbf(point,row.data,gamma)
            count0+=1   
        else: 
            sum1 += rbf(point,row.data,gamma)
            count1 += 1
    y = (1.0/count0)*sum0-(1.0/count1)*sum1 + offset
    
    if y < 0: return 0
    else : return 1
    
def getoffset(rows,gamma=10):
    l0=[]
    l1=[]
    
    for row in rows:
        if row.match ==0:l0.append(row.data)
        else : l1.append(row.data)
    sum0 = sum(sum([rbf(v1,v2,gamma) for v1 in l0]) for v2 in l0)
    sum1 = sum(sum([rbf(v1,v2,gamma) for v1 in l1]) for v2 in l1)
    
    return (1.0/(len(l1)**2))*sum1 - (1.0/(len(l0)**2))*sum0


#agesonly = loadmatch('agesonly.csv',allnum = True)
#matchmaker = loadmatch('matchmaker.csv')
#print(agesonly)
#print(matchmaker)
#
#plotagematches(agesonly)
#
#average = lineartrain(agesonly)
#print(average)
#cls = dpclassify([30,30],average)
#print(cls)

numericalData = loadnumerical()
scaledset,scalef=scaledata(numericalData)
average = lineartrain(scaledset)
print(scaledset[2].data)
print(scaledset[2].match)

test = dpclassify(scaledset[2].data,average)
print(test)