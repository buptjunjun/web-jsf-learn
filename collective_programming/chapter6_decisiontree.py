my_data=[['slashdot','USA','yes',18,'None'],
        ['google','France','yes',23,'Premium'],
        ['digg','USA','yes',24,'Basic'],
        ['kiwitobes','France','yes',23,'Basic'],
        ['google','UK','no',21,'Premium'],
        ['(direct)','New Zealand','no',12,'None'],
        ['(direct)','UK','no',21,'Basic'],
        ['google','USA','no',24,'Premium'],
        ['slashdot','France','yes',19,'None'],
        ['digg','USA','no',18,'None'],
        ['google','UK','no',18,'None'],
        ['kiwitobes','UK','no',19,'None'],
        ['digg','New Zealand','yes',12,'Basic'],
        ['slashdot','UK','no',21,'None'],
        ['google','UK','yes',18,'Basic'],
        ['kiwitobes','France','yes',19,'Basic']]

class decisionnode:
    def __init__(self,col=-1,value=None,results=None,tb=None,fb=None):
        '''
        col is the index of the criteria
        value is the value that the column must  match to get a true result
        tb = true+b  fb = false+b
        result stores a dictionary of results for this branch  
        '''
        self.col = col
        self.value=value
        self.results = results
        self.tb = tb
        self.fb = fb
        
def diviest(rows, column,value):
    split_function=None
    if isinstance(value,int) or isinstance(value,float):
        split_function = lambda row:row[column] >= value 
    else:
        split_function = lambda row:row[column] == value
        
    #devide the rows into two sets and return them
    set1 = [row for row in rows if split_function(row)]
    set2 = [row for row in rows if not split_function(row)]
    
    return (set1,set2)

# caculate the number of each posible result
# for example 'None': 7, 'Premium': 3, 'Basic': 6
def uniquecounts(rows):
    results = {}
    for row in rows:
        # the result is the last column
        r = row[len(row)-1]
        if not r in results: results[r] = 0
        results[r]+=1
    return results

# Probability that a randomly placed item will be in the wrong category
def giniimpurity(rows):
    total = len(rows)
    counts=uniquecounts(rows)   
    imp = 0
    for k1 in counts:
        p1 = float(counts[k1])/total
        for k2 in counts:
            if k1 == k2: continue;
            p2 = float(counts[k2]) /total
            imp += p1*p2
    return imp

def entropy(rows):
    from math import  log
    log2 = lambda x: log(x)/log(2)
    results = uniquecounts(rows)
    
    # now caculate the entropy
    ent = 0.0
    for r in results.keys():
        p = float(results[r])/len(rows)
        ent=ent-p*log2(p)
    return ent   

def buildtree(rows, scoref=entropy):
    if len(rows) == 0: return decisionnode()
    current_score = scoref(rows)
    
    #set up some variables to track the best criteria
    best_gain = 0.0
    best_criteria = None
    best_sets=None
    
    column_count=len(rows[0]) - 1
    
    for col in range(0,column_count):
        # generate the list of different values in this columns
        column_values = {}
        for row in rows:
            column_values[row[col]] =1
        
        # now try dividing the rows up for each value in this columns
        for value in column_values.keys():
            (set1,set2) = diviest(rows,col,value)
            
            # information gain
            p = float(len(set1)/len(rows))
            gain = current_score - p*scoref(set1) - (1-p)*scoref(set2)
            
            if gain > best_gain and len(set1) > 0 and len(set2) > 0:
                best_gain = gain
                best_criteria = (col,value)
                best_sets=(set1,set2)
    # create the subbranches
    if best_gain > 0:
        trueBranch = buildtree(best_sets[0])
        falseBranch = buildtree(best_sets[1])
        return decisionnode(col=best_criteria[0],value=best_criteria[1],tb=trueBranch,fb = falseBranch)
    else:
        return decisionnode(results=uniquecounts(rows))
    
# observation = ['(direct)','USA','yes',5]
def classify(observation,tree):
    if tree.results != None:
        return tree.results
    else : 
        v = observation[tree.col]
        branch = None
        if isinstance(v,int) or isinstance(v,float):
            if v > tree.value: branch = tree.tb
            else : branch = tree.fb
        else :
            if v == tree.value: branch = tree.tb
            else :branch = tree.fb
        return classify(observation,branch)

# observation = ['(direct)','USA','yes',5]
# deal with the missing feature when classifying a observation
def mdclassify(observation,tree):
    if tree.results != None:
        return tree.results
    else : 
        v = observation[tree.col]
        # deal with missing feature
        if v==None:
            tr,fr = mdclassify(observation,tree.tb), mdclassify(observation,tree.fb)
            tcount = sum(tr.values())
            fcount = sum(fr.values())
            # true weight,and false weight
            tw = float(tcount)/(fcount+tcount)
            fw =  float(fcount)/(fcount+tcount)
            result = {}
            
            for k,v in tr.items(): result[k]=v*tw
            for k,v in fr.items(): result[k]=v*tw
            return result
        else:
            branch = None
            if isinstance(v,int) or isinstance(v,float):
                if v > tree.value: branch = tree.tb
                else : branch = tree.fb()
            else :
                if v == tree.value: branch = tree.tb
                else :branch = tree.fb
            return mdclassify(observation,branch)


def prune(tree,mingain):
    # if the branches are not leaves then prune them 
    if tree.tb.results == None:
        prune(tree.tb,mingain)
        
    if tree.fb.results == None:
        prune(tree.fb,mingain)

    # if both nodes are now leaves see if they should merge
    if tree.tb.results != None and  tree.fb.results != None:
        #build a combined dataset
        tb,fb =[],[]
        for v,c in tree.tb.results.items():
            tb+=[[v]]*c
        for v,c in tree.fb.results.items():
            fb+=[[v]]*c
        print(tb+fb)  
        # test the reduction in entropy
        delta = entropy(tb+fb) - (entropy(tb)+entropy(fb))/2
        
        if delta < mingain:
        # Merge the branches 
            tree.tb, tree.fb = None,None
            tree.results=uniquecounts(tb+fb)
        
# print tree on console
def printtree(tree,indent=" "):
    # is this a leaf node?
    if tree.results != None:
        print(tree.results)
    else:
        #print the vriteria
        print (str(tree.col)+":"+str(tree.value)+"?")
        #print the branches
        print(indent+"T->",end=" ")
        printtree(tree.tb,indent+" ")
        print(indent+"F->",end="")
        printtree(tree.fb,indent+" ")
        
        
# draw image 
def getwidth(tree):
    if tree.tb==None and tree.fb==None: return 1
    return getwidth(tree.tb)+getwidth(tree.fb)       
def getdepth(tree):
    if tree.tb==None and tree.fb==None: return 0
    return max(getdepth(tree.tb),getdepth(tree.fb))+1

from PIL import Image,ImageDraw

def drawtree(tree,jpeg='tree.jpg'):
    w=getwidth(tree)*100
    h=getdepth(tree)*100+120
    img=Image.new('RGB',(w,h),(255,255,255))
    draw=ImageDraw.Draw(img)
    drawnode(draw,tree,w/2,20)
    img.save(jpeg,'JPEG')
    
def drawnode(draw,tree,x,y):
    if tree.results==None:
        # Get the width of each branch
        w1=getwidth(tree.fb)*100
        w2=getwidth(tree.tb)*100
        # Determine the total space required by this node
        left=x-(w1+w2)/2
        right=x+(w1+w2)/2
        # Draw the condition string
        draw.text((x-20,y-10),str(tree.col)+':'+str(tree.value),(0,0,0))
        # Draw links to the branches
        draw.line((x,y,left+w1/2,y+100),fill=(255,0,0))
        draw.line((x,y,right-w2/2,y+100),fill=(255,0,0))
        # Draw the branch nodes
        drawnode(draw,tree.fb,left+w1/2,y+100)
        drawnode(draw,tree.tb,right-w2/2,y+100)
    else:
        txt=' \n'.join(['%s:%d'%v for v in tree.results.items( )])
        draw.text((x-20,y),txt,(0,0,0))


#(s1,s2) = diviest(my_data,1,'UK')
#print(s1)
#print(s2)

#res1 = uniquecounts(my_data)
#print(res1)
#gini1 = giniimpurity(s1)
#print(gini1)
#gini2 = giniimpurity(s2)
#print(gini2)   
#
#gini1 = entropy(s1)
#print(gini1)
#gini2 = entropy(s2)
#print(gini2)         
#tree= buildtree(my_data)
#drawtree(tree)
#ret = mdclassify(['(direct)','USA',None,None],tree)
#print(ret)
#prune(tree,0.9)
#printtree(tree)
#drawtree(tree)