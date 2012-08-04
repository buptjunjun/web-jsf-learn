# cluster example
import math
import random
import chapter2_cluster_class as c2


# read the data from the file
def readFile(fileName):
    lines = [line for line in open(fileName)]
    
    # first line the the column title
    colNames = lines[0].strip().split('\t')
    
    rowNames = []
    data = []
    
    for line in lines[1:]:
        p = line.strip().split('\t')
        #first column of each row is the rowName
        rowNames.append(p[0])
        data.append([float(x) for x in p[1:]])
    return rowNames, colNames, data
# define the pearson correlative score of two blog
def pearson(v1,v2):
    
    sum1 = sum(v1)
    sum2 = sum(v2)
    
    average1 = sum1/len(v1)
    average2 = sum2/len(v2)
    
    top = sum ([ (v1[i]- average1)* (v2[i] - average2) for i in range(0,len(v1))])
    
    bottom1  =  sum ([ pow(v - average1,2) for v in v1]) 
    bottom2  =  sum ([ pow(v - average1,2) for v in v2])
    
    if bottom1 == 0 or bottom2 == 0: return 0   
    return 1-  top / (math.sqrt(bottom1) * math.sqrt(bottom2))

def pearson1(v1,v2):
    # Simple sums
    sum1=sum(v1)
    sum2=sum(v2)
    # Sums of the squares
    sum1Sq=sum([pow(v,2) for v in v1])
    sum2Sq=sum([pow(v,2) for v in v2])
    # Sum of the products
    pSum=sum([v1[i]*v2[i] for i in range(len(v1))])
    # Calculate r (Pearson score)
    num=pSum-(sum1*sum2/len(v1))
    den=math.sqrt((sum1Sq-pow(sum1,2)/len(v1))*(sum2Sq-pow(sum2,2)/len(v1)))
    if den==0: return 0
    return 1.0 - num/den    

def hcluster(rows,distance = pearson1):
    distances = {}
    currentclustid = -1
    
    # clusters are initially just the rows
    clust = [c2.Bicluster(rows[i], id = i) for i in range(len(rows))]
    
    while len(clust) > 1:
        lowestpair = (0,1)
        closest = distance(clust[0].vector ,clust[1].vector)
        
        # loop to find the smallest distance
        for i in range(len(clust)):
            for j in range(i+1, len(clust)):
                #distance is the cache of the distance calculations
                if not (clust[i].id, clust[j].id) in distances:
                    distances[(clust[i].id, clust[j].id)] = distance(clust[i].vector, clust[j].vector)
                d = distances[(clust[i].id, clust[j].id)]
                
                if d < closest:
                    closest = d
                    lowestpair = (i,j)
        #caculate the average of the two cluster
        mergeVector = [ (clust[lowestpair[0]].vector[i] + clust[lowestpair[1]].vector[i])/2  for i in range(len(clust[0].vector))]
        
        newCluster = c2.Bicluster(mergeVector,left=clust[lowestpair[0]], right = clust[lowestpair[1]],distance = closest,id = currentclustid)
        
        # currentclustid is negative if it is id of merged node 
        currentclustid -= 1
        del clust[lowestpair[1]]
        del clust[lowestpair[0]]
        
        clust.append(newCluster)
         
    return clust[0]

def kmeanCluster(rows,distance=pearson1, k = 5):
    #Determine the minimum and maximum values for column
    ranges=[(min([row[i] for row in rows]),max([row[i] for row in rows]) ) for i in range(len(rows[0]))]
    
    # create k randomly placed centroids
    clusters=[[random.random( )*(ranges[i][1]-ranges[i][0])+ranges[i][0] for i in range(len(rows[0]))] for j in range(k)]
    
    lastmatches = None
    bestmatches=[[] for i in range(k)]
    for t in range(100):
        print("iteration %d" %t)

        #find which  centroid is the closest for each row
        for j in range(len(rows)):
            flag = False
            for best in bestmatches:
                if j in best: 
                    flag = True
                    break;
                
            if flag == True:
                print("%d continue" % j)
                continue
            
            row = rows[j]
            bestmatch=0
            for i in range(k):
                d = distance(clusters[i],row)
                if d < distance(clusters[bestmatch],row): bestmatch = i
            bestmatches[bestmatch].append(j);
            
        if bestmatches == lastmatches: break;
        lastmatches = bestmatches;
        
        #move the centroids to the average of their members
        for i in range(k):
            avgs = [0.0] * len(rows[0])
            if len(bestmatches[i])> 0:
                for rowid in bestmatches[i]:
                    for m in range(len(rows[rowid])):
                        avgs[m]+=rows[rowid][m]
                for j in range(len(avgs)):
                    avgs[j] /= len(bestmatches[i])
            clusters[i] = avgs
    return bestmatches 
        

def printclust(clust,labels=None,n=0):
    # indent to make a hierarchy layout
    for i in range(n): print(" ",end="")
   
    if clust.id<0:
        # negative id means that this is branch
        print('-',end="")
    else:
        # positive id means that this is an endpoint
        if labels==None: print (clust.id,end="\n")
        else :
            if clust.id < len(labels): print (labels[clust.id],end="\n")
    # now print the right and left branches
    if clust.left!=None: printclust(clust.left,labels=labels,n=n+1)
    if clust.right!=None: printclust(clust.right,labels=labels,n=n+1)
    
def rotatematrix(data):
    newdata=[]
    for i in range(len(data[0])):
        newrow=[data[j][i] for j in range(len(data))]
        newdata.append(newrow)
    return newdata

fileName = "D:\\myfile\\machine learning\\collective intelligence\\clusterBlog.txt"
blogName, words,data = readFile(fileName)

clust = hcluster(data)
printclust(clust,labels=blogName)

#bestmatches = kmeanCluster(data,k=5)

#print("\n 0 --------------------------")
#sum = 0
#for i in range( len(bestmatches)):
#    bestmatch = bestmatches[i]
#    sum+=len(bestmatch)
#    for j in bestmatch:
#        print("%s" % blogName[j],end=",")
#    print("\n%d --------------------------" % i)
#    
#print("sum = %d" %sum)
#data1 = rotatematrix(data)
#print(data[0])


