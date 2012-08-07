'''
    searchEngine 
    buptjunjun
    2012-8-5
'''
import urllib3
import urllib
from bs4 import BeautifulSoup

class crawler:
    # Initialize the crawler with the name of database
    def __init__(self,dbname):
        pass
    
    def __del__(self):
        pass
    
    def dbmommit(self):
        pass
    
    # Auxilliary function for getting a entryid and 
    # adding it if it is not present
    def getentryid(self, table,field,value, createnew=True):
        return None
    
    # index an individula page
    def addtoindex(self, url, soup):
        print("indexing %s " %url)
    
    # extract the text from an HTML page(no tags)
    def gettextonly(self, soup):
        return None
    # seperate the words by any no-whitespace character
    def seperatewords(self, text):
        return None
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
        pass
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    