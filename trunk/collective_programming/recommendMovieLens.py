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

# get the common item of id1 and id2
def getCommonItem(prefs ,id1, id2):
    if not ( id1 in prefs) or not ( id2 in prefs):
        return None
    
    items1 = prefs[id1]
    items2 = prefs[id2]
    
    keys1 = items1.keys()
    keys2 = items2.keys()
    
    count = 0
    for key in keys1:
        if key in keys2:
            count+=1
            #print("key =%s  id1 = %s value1=%d , id2 = %s , value2 = %d"  % (key, id1, prefs[id1][key],  id2, prefs[id2][key] ) )
    
    return count
    
prefs = loadMovieLens()
getCommonItem(prefs, '100', '941')
print("------------------------")
getCommonItem(prefs, '100', '150')


# 测试欧氏距离
sim_score1 = r.sim_distance_euclidean(prefs,"100","941")
sim_score2 = r.sim_distance_euclidean(prefs,"100","150")
print("sim_score1 = %f" % sim_score1)
print("sim_score2 = %f" % sim_score2)

# 测试 相关系数
corrlate_score1 = r.sim_distance_correlation_score(prefs,"100","200")
corrlate_score2 = r.sim_distance_correlation_score(prefs,"100","150")
print("sim_score1 = %f" % corrlate_score1)
print("sim_score2 = %f" % corrlate_score2)

topMatch = r.topNMatch(prefs, '100',300)
print(topMatch)

# 测试推荐item
topRecom = r.getRecommendation(prefs, "102",140)
print(topRecom)
print("over")


# the similarity of two movie
# movies = r.transformPrefs(prefs)
# keys  = movies.keys()
#for key in keys:
#    print("%s --- %s"  %(key, movies[key]))

#topMatch    =  r.topNMatch(movies, 'This Is Spinal Tap (1984)', 10)
#print(topMatch)

simMatrix = r.caculateSimilarItems(prefs)
#for item in simMatrix:
#    print(item,simMatrix[item])

recomItems = r.getRecommendItems(prefs, simMatrix, '100')
print(recomItems)