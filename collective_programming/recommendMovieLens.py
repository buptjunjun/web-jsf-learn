#coding:utf-8
import recommendations as r
import sys
import codecs


def loadMovieLens(path = "ml-100k\\"):
    movie = {}
    item_url = path+'u.item'
    
    # get movie title
    for line in open(item_url,'rb'): 
        if not line:
            break;
        
        if line[:3] == codecs.BOM_UTF8:
            line=line[3:]          
        line = line.decode("utf-8")    
        (id,title) = line.split("|")[0:2]  
        movie[id]= title
    
    # 
    prefs = {}
    item_url = path+'u.data'
    for line in open(item_url,'rb'): 
        if not line:
            break;
        
        if line[:3] == codecs.BOM_UTF8:
            line=line[3:]          
        line = line.decode("utf-8")    
        (user,movieid,rating,time) = line.split("\t")
        prefs.setdefault(user,{})
        if movieid in movie:
            prefs[user][movie[movieid]] = float(rating)
    
    return prefs

prefs = loadMovieLens()
    
topMatch = r.topNMatch(prefs, '88',300,r.sim_distance_correlation_score)
print(topMatch)

# 测试推荐item
topRecom = r.getRecommendation(prefs, "102",140)
print(topRecom)
print("over")