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

ignoreWords = ["is","are","am"]
class Crawler:
       
    global ignoreWords ;
    # Initialize the crawler with the name of database
    def __init__(self,dbname):
        self.con = MySQLdb.Connect(host = '127.0.0.1', user = 'root', passwd = '',db=dbname)
    
    def __del__(self):
        self.con.close()
    
    def dbmommit(self):
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
            self.dbmommit()
            cursor.execute("select rowid from %s where %s = '%s'"% (table,field,value))
            ret = cursor.fetchone()
            return ret[0]
        else:
            return ret[0]
    
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
        self.dbmommit()
    
    
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
            
        return False
    
    
    #Add a link between two pages
    def addlinkref(self,urlFrom, urlTo, linkText):
        pass   
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
                self.dbmommit()
            pages =newpages
    
    def caculatepagerank(self,iterations = 20):
        cursor = self.con.cursor()
        # create pagerank table
        cursor.execute("drop table if exists pagerank")
        cursor.execute("create table pagerank(urlid primary key,score float)")
    
        # initialize every url with PageRank of 1
        cursor.execute("insert into pagerank select rowid,1.0 from urllist")
        self.dbmommit()
        for i in range(iterations):
            print("iteration: %d" %i)
            cursor.execute("select * form urllist")
            urls = cursor.fetchall()
            for url in urls:
                pre = 0.15
                
                urlid = url[0]
                cursor.execute("select distinct fromid where toid = %d " %urlid )
                fromids = cursor.fetchall()
                
                # 
                for fromid in fromids:
                    fromid = fromid[0]
                    cursor.execute("select score form pagerank where urlid = %d" % fromid)
                    cursor.fetchall()[0]
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
        self.dbmommit();
    
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
        return rows,wordids 
           

    # get the url form database 
    def geturlname(self,id):
        cursor = self.con.cursor()
        cursor.execute("select * from urllist where rowid = %d " % id)
        ret = cursor.fetchone()
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
  
#crawler = Crawler("searchengine")
#crawler.crateIndexTable() 
#id = crawler.getentryid("wordlist", 'word', "abcddd3ds3")
#print(id)

query = Query("searchengine")
q = "site index andy  programming"
#query.getmatchrows(q)
urlname = query.geturlname(2)
print(urlname)
query.query(q)
    
    

    