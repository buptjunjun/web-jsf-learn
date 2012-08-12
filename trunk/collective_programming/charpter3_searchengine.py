'''
    searchEngine 
    buptjunjun
    2012-8-5
'''
import urllib3
import urllib
from bs4 import BeautifulSoup
import MySQLdb
import re
from math import tanh 
ignoreWords = ["is","are","am"]
class Crawler:
       
    global ignoreWords ;
    # Initialize the crawler with the name of database
    def __init__(self,dbname):
        self.con = MySQLdb.Connect(host = '127.0.0.1', user = 'root', passwd = '',db=dbname)
    
    def __del__(self):
        self.con.close()
    
    def dbcommit(self):
        self.con.commit()
    
    # Auxilliary function for getting a entryid and 
    # adding it if it is not present
    def getentryid(self, table,field,value, createnew=True):
        cursor = self.con.cursor()
        cursor.execute("select rowid from %s where %s = '%s'"% (table,field,value))
        ret = cursor.fetchone();
        if ret == None:
            print("insert into %s (%s) value ('%s')" %(table,field,value))
            cur = cursor.execute("insert into %s (%s) value ('%s')" %(table,field,value))
            self.dbcommit()
            cursor.execute("select rowid from %s where %s = '%s'"% (table,field,value))
            ret = cursor.fetchone()
            return ret[0]
        else:
            return ret[0]
        cursor.close()
    
    # index an individula page
    def addtoindex(self, url, soup):
        if self.isindexed(url):return
        
        print("indexing %s " %url)

        #get the individual words
        text = self.gettextonly(soup)
        words = self.seperatewords(text)
        
        #link each words to the url
        urlid = self.getentryid("urllist", 'url', url)
        
        #link each word to this url
        
        for i in range(len(words)):
            word = words[i]
            # if the word should be ignored
            if word in ignoreWords: continue;
            wordid = self.getentryid("wordlist", "word", word)
            cursor = self.con.cursor()
            print("inert into wordlocation (urlid,wordid,location) value (%d,%d,%d)" %(urlid,wordid,i))
            #cursor.execute("inert into wordlocation (urlid,wordid,location) value (%d,%d,%d)" %(urlid,urlid,i))
            cursor.execute("insert into wordlocation  value (%d,%d,%d)" %(urlid,wordid,i))
            cursor.close()
        self.dbcommit()
    
    
    # extract the text from an HTML page(no tags)
    def gettextonly(self, soup):
        v = soup.string
        if v == None:
            c=soup.contents
            resulttext = ""
            for t in c:
                subtext = self.gettextonly(t)
                resulttext+=subtext+"\n"
            return resulttext
        else :
            return v.strip()
        
        
    # seperate the words by any no-whitespace character
    def seperatewords(self, text):
       splitter=re.compile('\\W*')
       return [s.lower( ) for s in splitter.split(text) if s!='']
   
    # return true if the url is already indexed
    def isindexed(self,url):
        cursor = self.con.cursor()
        cursor.execute("select rowid from urllist where url = '%s'"% url)
        ret = cursor.fetchone();
        if ret != None:
            urlid = ret[0]
            
            cursor.execute("select * from wordlocation  where urlid = '%s'"% urlid)
            ret = cursor.fetchone()
            if ret != None:
                return True
        cursor.close()      
        return False
    
    
    #Add a link between two pages
    def addlinkref(self,urlFrom, urlTo, linkText):

        fromID = self.getentryid("urllist", "url", urlFrom)
        toID = self.getentryid("urllist", "url", urlTo)
               
        cursor = self.con.cursor()
        cursor.execute("insert into link value (%d,%d)" % (fromID,toID))
        cursor.close()
        self.dbcommit()
        
    #Start with a list of pages, 
    #search to the given depth
    def crawl(self, pages,depth=2):
        for i in range(depth):
            newpages = set()
            for page in pages:
                try:
                   http = urllib3.PoolManager()
                   r = http.request('GET', page)                 
                except:
                    print("could not open %s" % page)
                    continue
                soup = BeautifulSoup(r.data)
                self.addtoindex(page, soup)
                
                links = soup('a')
                for link in links:
                    if('href' in dict(link.attrs)):
                        url = urllib.parse.urljoin(page,link['href'])
                        if url.find("'") != -1: continue
                        url.split('#')[0] #removie location protion
                        if url[0:4] == 'http' and not self.isindexed(url):
                            newpages.add(url)
                        linkText = self.gettextonly(link)
                        self.addlinkref(page, url, linkText)
                self.dbcommit()
            pages =newpages
    
    def caculatepagerank(self,iterations = 20):
        cursor = self.con.cursor()
        # create pagerank table
        cursor.execute("drop table if exists pagerank")
        cursor.execute("create table pagerank (urlid int primary key,score float(20,19))")
    
        # initialize every url with PageRank of 1
        cursor.execute("insert into pagerank select rowid,100.0 from urllist")
        self.dbcommit()
        for i in range(iterations):
            print("iteration: %d" %i)
            cursor.execute("select * from urllist")
            urls = cursor.fetchall()
            for url in urls:
                pr = 0.15
                
                urlid = url[0]
                cursor.execute("select distinct fromid from link where toid = %d " %urlid )
                fromids = cursor.fetchall()
                
                # 
                for fromid in fromids:
                    fromid = fromid[0]
                    cursor.execute("select score from pagerank where urlid = %d" % fromid)
                    ret = cursor.fetchone()
                    if ret ==None : continue
                    score = ret[0]
                    if score == 0 : score = 100
                    
                    cursor.execute("select count(*) from link where fromid = %d" % fromid)
                    ret = cursor.fetchone()
                    if ret == None:continue
                    outlinks = ret[0]
                   # print("score = %d outlinks = %d" %(score,outlinks))
                    if outlinks == 0: 
                        continue
                    
                    pr += 0.85*score/outlinks;
               # print("url = %s urlid = %d pr = %f" %(url,urlid,pr))
                self.con.cursor().execute('update pagerank set score=%d where urlid=%d' % (pr,urlid))
                self.dbcommit()
                cursor.close()
                # get the
                    
                    
        
        
    # create the database tables
    def crateIndexTable(self): 
        cur = self.con.cursor()
        cur.execute("CREATE TABLE urllist (rowid INT primary key NOT NULL AUTO_INCREMENT, url VARCHAR(100))")
        cur.execute("CREATE TABLE wordlist (rowid INT primary key NOT NULL AUTO_INCREMENT, word VARCHAR(100))")
        cur.execute("CREATE TABLE wordlocation (urlid INT, wordid INT, location INT)")
        cur.execute("CREATE TABLE link (fromid INT , toid INT)")
        cur.execute("CREATE TABLE linkwords (wordid INT , linkid INT)")
        cur.close()
        self.dbcommit();
    
class Query:
   
    def __init__(self,dbname):
        self.con = MySQLdb.Connect(host = '127.0.0.1', user = 'root', passwd = '',db=dbname)
    
    def __del__(self):
        self.con.close()
        
    # Strings to build the query
    def getmatchrows(self,q):
        fieldlist = 'w0.urlid'
        tablelist=''
        clauselist=''
        wordids=[]
        # Split the words by spaces
        words=q.split(' ')
        tablenumber=0
      
        for word in words:
            # Get the word ID
            cursor=self.con.cursor()
            cursor.execute( "select rowid from wordlist where word='%s'" % word)
            wordrow = cursor.fetchone()
            if wordrow!=None:
                wordid=wordrow[0]
                wordids.append(wordid)
                if tablenumber>0:
                    tablelist+=','
                    clauselist+=' and '
                    clauselist+='w%d.urlid=w%d.urlid and ' % (tablenumber-1,tablenumber)
                fieldlist+=',w%d.location' % tablenumber
                tablelist+='wordlocation w%d' % tablenumber
                clauselist+='w%d.wordid=%d' % (tablenumber,wordid)
                tablenumber+=1
       
        # Create the query from the separate parts
        print('select %s from %s where %s' % (fieldlist,tablelist,clauselist))
        fullquery='select %s from %s where %s' % (fieldlist,tablelist,clauselist)
        cursor.execute(fullquery)
        cur=cursor.fetchall()
        rows=[row for row in cur]
        print(rows)
        cursor.close()
        return rows,wordids 
           

    # get the url form database 
    def geturlname(self,id):
        cursor = self.con.cursor()
        cursor.execute("select * from urllist where rowid = %d " % id)
        ret = cursor.fetchone()
        cursor.close()
        if ret == None:
            return None
        return ret[1]
    
    # normalize the score between 0 and 1
    def normalizescore(self,scores,smallIsBetter=True):
        vsmall = 0.00001 
        if smallIsBetter:
            minscore = min(scores.values())
            return dict([(u,float(minscore)/max(vsmall,l)) for (u,l) in scores.items()])
        else :
            maxscore=max(scores.values( ))
            if maxscore==0: maxscore=vsmall
            return dict([(u,float(c)/maxscore) for (u,c) in scores.items( )])
    # caculate the  freqency of word 
    def frequencyscore(self,rows):
        counts = dict([(row[0],0) for row in rows])
        for row in rows: counts[row[0]]+=1
        return self.normalizescore(counts, smallIsBetter = False)
    
    # if the words are more nearer to the top of the page the more 
    def locationscore(self,rows):
        locations = dict([row[0],100000] for row in rows)
        for row in rows:
            loc = sum(row[1:])
            if loc < locations[row[0]]:  locations[row[0]] = loc
          
        # smallIsBetter = True , because the loc is smaller the score is higher
        return self.normalizescore(locations, smallIsBetter = True)
    
    # get the distance between the words
    def distancescore(self,rows):
        # if there is only one word
        mindistance = None 
        if len(rows[0]) <= 2:
            mindistance = dict([(row[0],1.00) for row in rows])
            return mindistance
        
        # initialize the distance to a large number
        mindistance = dict([(row[0],1000000) for row in rows])
        for row in rows:
            dist=sum([abs(row[i]-row[i-1]) for i in range(2,len(row))])
            if dist<mindistance[row[0]]: mindistance[row[0]]=dist
        print("in  distancescore")
        print(mindistance)
        return self.normalizescore(mindistance, smallIsBetter = True)
        
             
           
    # get the score list of each url
    def getscorelist(self,rows, wordids):
        totalscores= dict([(row[0],0) for row in rows])
        
        # every metrics have a weight 
        weights=[(0.3,self.distancescore(rows)),(0.3,self.locationscore(rows)), (0.4,self.frequencyscore(rows))]

       
        for (weight, scores) in weights:
            for url in totalscores:
                totalscores[url]+=weight*scores[url]
                
        return totalscores
            
    def query(self,q):
        rows ,wordids = self.getmatchrows(q)
        scores = self.getscorelist(rows, wordids)
        rankedscores = sorted([ (score,url) for (url,score) in scores.items()],reverse = 1)
        for (score,urlid) in rankedscores[0:10]:
            print("%f\t%s" % (score,self.geturlname(urlid)))

#artifical nerus network
class Searchnet:
    def __init__(self,dbname):
        self.con = MySQLdb.Connect(host = '127.0.0.1', user = 'root', passwd = '',db=dbname)
    def __del__(self):
        self.con.close()
        
    def maketable(self):
        cur = self.con.cursor()
        cur.execute("CREATE TABLE hiddennode (create_key varchar(200))")
        cur.execute("CREATE TABLE wordhidden (fromid int, toid int,strength float)")
        cur.execute("CREATE TABLE hiddenurl (fromid int, toid int ,strength float)")
        cur.close()
        self.dbcommit();
    def dbcommit(self):
        self.con.commit()
          
    def getstrength(self,fromid,toid,layer):
        cursor = self.con.cursor()
        if layer == 0:
            table = "wordhidden"
        elif layer == 1:
            table = "hiddenurl"
        cursor.execute("select strength from %s where fromid = %d and toid = %d" %(table,fromid,toid))
        ret = cursor.fetchone()
        if ret == None:
            #cursor.execute("insert into %s (fromid,toid,strength) value (%d,%d,%f)" %(table,fromid,toid,strength))
            if layer == 0: return -0.2
            if layer == 1: return 0
        return ret[0]
        
    def setstrength(self,fromid,toid,layer,strength):
        cursor = self.con.cursor()
        if layer == 0:
            table = "wordhidden"
        elif layer == 1:
            table = "hiddenurl"
        cursor.execute("select strength from %s where fromid = %d and toid = %d"  % (table,fromid,toid))
        ret = cursor.fetchone()
        if ret == None:
            cursor.execute("insert into %s (fromid,toid,strength) value (%d,%d,%f)" %(table,fromid,toid,strength))
        else: 
            cursor.execute("update %s set strength = %f where  fromid = %d and toid = %d" %(table,strength,fromid,toid))
        cursor.close()
        
    def generatehiddennode(self,wordids,urls):
        if len(wordids) > 3 :return None
        
        # Check if we already created a node for this set of words
        createkey="_".join(sorted(str(wi) for wi in wordids))
        cursor = self.con.cursor()
        cursor.execute("select * from hiddennode where create_key = '%s' " % createkey )
        res = cursor.fetchone() 
        
        # if not create if
        if res == None:
            cursor.execute("insert into hiddennode (create_key) value ('%s')" % createkey)
            hiddenid = cursor.lastrowid
            for wordid in wordids:
                self.setstrength(wordid, hiddenid, 0, 1.0/len(wordids))
            for urlid in urls:
                self.setstrength(hiddenid, urlid, 1, 0.1)
            self.dbcommit()
            cursor.close()
            
    def getallhiddenids(self,wordids,urlids):
        l1={}
        cursor = self.con.cursor()
        for wordid in wordids:   
            cursor.execute("select toid from wordhidden where fromid = %d " % wordid )
            res = cursor.fetchall()
            for row in res:
                l1[row[0]] = 1 
        for urlid in urlids:   
            cursor.execute("select fromid from hiddenurl where toid = %d " % urlid )
            res = cursor.fetchall()
            for row in res:
                l1[row[0]] = 1 
        return [ key for key in l1.keys()]
    
    def setupnetwork(self,wordids,urlids): 
         # value lists
        self.wordids=wordids
        self.hiddenids=self.getallhiddenids(wordids,urlids)
        self.urlids=urlids
        # node outputs
        self.ai = [1.0]*len(self.wordids)
        self.ah = [1.0]*len(self.hiddenids)
        self.ao = [1.0]*len(self.urlids)
        # create weights matrix
        self.wi = [[self.getstrength(wordid,hiddenid,0)
        for hiddenid in self.hiddenids]
        for wordid in self.wordids]
        self.wo = [[self.getstrength(hiddenid,urlid,1)
        for urlid in self.urlids]
        for hiddenid in self.hiddenids]
      
    def feedforward(self):
        # the only inputs are the query words
        for i in range(len(self.wordids)):
            self.ai[i] = 1.0
     
        # hidden activateions
        for j in range(len(self.hiddenids)):
            sum = 0.0
            for i in range(len(self.wordids)):
                sum = sum + self.ai[i] * self.wi[i][j]
            self.ah[j] = tanh(sum)
        
        # output activations
        for k in range(len(self.urlids)):
            sum = 0.0
            for j in range(len(self.hiddenids)):
                sum = sum + self.ah[j]*self.wo[j][k]
            self.ao[k] = tanh(sum)
        
        return self.ao[:]
    
    def getresult(self,wordids,urlids):
          self.setupnetwork(wordids, urlids)
          return self.feedforward()
      
    def dtanh(self,y):
        return 1-y*y
    
    def backPropagate(self,targets,N=0.5):
        #caculate errors for output
        output_deltas = [0.0]*len(self.urlids)
        for k in range(len(self.urlids)):
            error = targets[k] - self.ao[k]
            output_deltas[k] =self.dtanh(self.ao[k])*error
        
        # caculate error for hidden layer
        hidden_deltas = [0.0] * len(self.hiddenids)
        for j in range(len(self.hiddenids)):
            error = 0.0
            for k in range(len(self.urlids)):
                error += output_deltas[k] * self.wo[j][k]
            hidden_deltas[j] = self.dtanh(self.ah[j]) * error
             
        # update output weight
        for j in range(len(self.hiddenids)):
            for k in range(len(self.urlids)):
                change = output_deltas[k] * self.ah[j]
                self.wo[j][k] += N * change

        # upate input weights
        for j in range(len(self.wordids)):
            for k in range(len(self.hiddenids)):
                change = hidden_deltas[k] * self.ai[j]
                self.wi[j][k] += N * change
            
    def trainquery(self,wordids,urlids,selectedurls):
        # generate a hidden node if necessary
        self.generatehiddennode(wordids, urlids)
        
        self.setupnetwork(wordids, urlids)
        self.feedforward()
        
        targets = [0.0] * len(urlids)
        for selectedurl in selectedurls:
            targets[urlids.index(selectedurl)] = 1.0
        
        error = self.backPropagate(targets)
        self.updatedatabase()
    
    def updatedatabase(self):
        #set them to database value
        
        for i in range(len(self.wordids)):
            for j in range(len(self.hiddenids)):              
                self.setstrength(self.wordids[i], self.hiddenids[j], 0, self.wi[i][j])
                
        for i in range(len(self.hiddenids)):
            for j in range(len(self.urlids)):
                self.setstrength(self.hiddenids[i], self.urlids[j], 1, self.wo[i][j])
        self.dbcommit()
            
crawler = Crawler("searchengine")
#crawler.crateIndexTable() 
#id = crawler.getentryid("wordlist", 'word', "abcddd3ds3")
#print(id)
#crawler.caculatepagerank(iterations=20)

#query = Query("searchengine")
#q = "site index andy  programming"
#query.getmatchrows(q)
#urlname = query.geturlname(2)
#print(urlname)
#query.query(q)
    
searchnet = Searchnet("searchengine")
#searchnet.maketable()
wWorld,wRiver,wBank =101,102,103
uWorldBank,uRiver,uEarth =201,202,203
#searchnet.generatehiddennode([wWorld,wBank],[uWorldBank,uRiver,uEarth])
searchnet.trainquery([wWorld,wBank],[uWorldBank,uRiver,uEarth],[uWorldBank,uRiver])
res = searchnet.getresult([wWorld,wBank],[uWorldBank,uRiver,uEarth])
print(res)
    

    