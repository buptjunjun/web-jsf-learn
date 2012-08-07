'''
    searchEngine 
    buptjunjun
    2012-8-5
'''
import urllib3
import urllib
from bs4 import BeautifulSoup
import MySQLdb

class Crawler:
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
        return None
    
    # index an individula page
    def addtoindex(self, url, soup):
        if self.isindexed(url):return
        
        print("indexing %s " %url)

        #get the individual words
        text = self.gettextonly(soup)
        words = self.seperatewords(text)
        
    
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
    
    # create the database tables
    def crateIndexTable(self): 
        cur = self.con.cursor()
        cur.execute("CREATE TABLE urllist (rowid INT primary key, url VARCHAR(100))")
        cur.execute("CREATE TABLE wordlist (rowid INT primary key, word VARCHAR(100))")
        cur.execute("CREATE TABLE wordlocation (urlid INT, wordid INT, location INT)")
        cur.execute("CREATE TABLE link (fromid INT , toid INT)")
        cur.execute("CREATE TABLE linkwords (wordid INT , linkid INT)")
        cur.close()
        self.dbmommit();
    
    
crawler = Crawler("searchengine")
crawler.crateIndexTable() 
    
    
    
    

    