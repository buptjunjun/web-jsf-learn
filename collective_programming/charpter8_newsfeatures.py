import feedparser 
import re

feedlist=['http://today.reuters.com/rss/topNews',
        'http://today.reuters.com/rss/domesticNews',
        'http://today.reuters.com/rss/worldNews',
        'http://hosted.ap.org/lineups/TOPHEADS-rss_2.0.xml',
        'http://hosted.ap.org/lineups/USHEADS-rss_2.0.xml',
        'http://hosted.ap.org/lineups/WORLDHEADS-rss_2.0.xml',
        'http://hosted.ap.org/lineups/POLITICSHEADS-rss_2.0.xml',
        'http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml',
        'http://www.nytimes.com/services/xml/rss/nyt/International.xml',
        'http://news.google.com/?output=rss',
        'http://feeds.salon.com/salon/news',
        'http://www.foxnews.com/xmlfeed/rss/0,4313,0,00.rss',
        'http://www.foxnews.com/xmlfeed/rss/0,4313,80,00.rss',
        'http://www.foxnews.com/xmlfeed/rss/0,4313,81,00.rss',
        'http://rss.cnn.com/rss/edition.rss',
        'http://rss.cnn.com/rss/edition_world.rss',
        'http://rss.cnn.com/rss/edition_us.rss']

def stripHTML(h):
    p=''
    s=0
    for c in h:
        if c == '<': s=1
        elif c == ">":
            s = 0
            p += ' '
        elif s==0: p+= str(c)
    return p

def seperatewords(text):
    splitter = re.compile('\\w*')
    return [s.lower() for s in splitter.split(text) if len(s) > 3]

def getarticlewords():
    allwords={}
    articlewords=[]
    articletitles=[]
    ec = 0
    
    # Loop over every feed
    for feed in feedlist:
        f = feedparser.parse(feed)
        print("feed --" , feed)
        # Loop over every articals
        for e in f.entries:
            # Ignore the identical articles
            if e.title in articletitles:continue
            print("title -- ",e.title)
            
            # extract the words
            txt = str(e.title.encode('utf8')) + stripHTML(str(e.description.encode('utf8')))
            words = seperatewords(txt)
            
            articlewords.append({})
            articletitles.append(e.title)
            
            # increase the counts for this word in allwords and in articlewords
            for word in words:
                allwords.setdefault(word,0)
                allwords[word]+=1
                articlewords[ec].setdefault(word,0)
                articlewords[ec][word]+=1
            ec+=1
    return allwords,articlewords,articletitles

#allw,artw,artt = getarticlewords()
#print(allw)
#print(artw)
#print(artt)
#    

from numpy import *
l1 = [[1,2,3],[2,3,4]]
l2 = [[1,2],[2,3],[3,4]]
m1 = matrix(l1)
m2 = matrix(l2)
print(m1*m2)
