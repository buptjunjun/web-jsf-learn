from pylab import *

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

agesonly = loadmatch('agesonly.csv',allnum = True)
matchmaker = loadmatch('matchmaker.csv')
print(agesonly)
print(matchmaker)

plotagematches(agesonly)

average = lineartrain(agesonly)
print(average)
cls = dpclassify([30,30],average)
print(cls)
