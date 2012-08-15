import re
import math

def getwords(doc):
   # print(doc)
    splitter = re.compile('\\W*')
    words = [s.lower() for s in splitter.split(doc) if len(s) > 2 and len(s) < 20]
    
    # Return the unique set of words ony
    return dict([(w,1) for w in words])

class classifier:
    def __init__(self, getfeatures, filename=None):
        # Counts of feature/category combinations
        self.fc = {}
        # Counts of documents in each category
        self.cc = {}
        self.getfeatures = getfeatures
        
    # Increate the count of a feature/category pair
    def incf(self,f,cat):
        self.fc.setdefault(f,{})
        self.fc[f].setdefault(cat,0)
        self.fc[f][cat]+=1
        
    # Increate the count of a category 
    def incc(self,cat):
        self.cc.setdefault(cat,0)
        self.cc[cat]+=1
        
    # the number of times a feature has appeared in a category
    def fcount(self,f,cat):
        if f in self.fc and cat in self.fc[f]:
            return float(self.fc[f][cat])
        return 0.0
    
    # the number of item in a category
    def catcount(self, cat):
        if cat in self.cc:
            return float(self.cc[cat])
        return 0
    
    # the total number of items
    def totalcount(self):
        return sum(self.cc.values())
    
    # the list of all categories
    def categories(self):
        return self.cc.keys()
    
    # item is a document, cat is a category
    def train(self,item,cat):
        features = self.getfeatures(item)
        # Increate the count for every feature with this category
        for f in features:
            #print(f)
            self.incf(f, cat)       
        # Increase the count for this category
        self.incc(cat)
    
    def fprob(self,f,cat):
        if self.catcount(cat) == 0: return 0
        # The total number of times this feature appeard in this
        # category divided by to total number of items in this category
        return self.fcount(f,cat)/self.catcount(cat)
    
    def weightedprob(self, f, cat, prf, weight=1.0, ap=0.5):
        # caculate current probability
        basicprob=prf(f,cat)
        
        # count the number of times thsi feature has appeared in all categories
        totals = sum([self.fcount(f, c) for c in self.categories()])
    
        # caculate the weighted average
        bp = (weight*ap + totals*basicprob)/(weight + totals)
        return bp

def sampletrain(cl):
        cl.train('Nobody owns the water.','good')
        cl.train('the quick rabbit jumps fences','good')
        cl.train('buy pharmaceuticals now','bad')
        cl.train('make quick money at the online casino','bad')
        cl.train('the quick brown fox jumps','good')
        

#naive bayesian classifier
class naivebayes(classifier):
    def docprob(self, item, cat):
        features = self.getfeatures(item)
        
        # mulitiply all the probabilities  of all features together
        p = 1
        for f in features: p *= self.weightedprob(f, cat, self.fprob)
        
        return p


cl=classifier(getwords)
sampletrain(cl)
print(cl.fcount("quick", "good")) 
bp =  cl.weightedprob("money","good",cl.fprob)
print(bp)
sampletrain(cl)
bp =  cl.weightedprob("money","good",cl.fprob)
print(bp)

#ret = getwords('make quick money in the online casino')
#print(ret)