#coding:utf-8
import recommendations as r

def loadMovieLens(path = "ml-100k\\"):
    movie = {}
    item_url = path+'''u.item'''
    
    print(item_url)
    
    for line in open(item_url):
    #    print(line)
        line = line.split("|")[0:2]
        id = line[0]
        title = line[1]
        movie[id] = title

    
    return movie

movie = loadMovieLens()
print(movie)
