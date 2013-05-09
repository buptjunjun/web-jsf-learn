import httplib2
url = 'http://xiangping.com'
httplib2.debuglevel = 1   
h = httplib2.Http(".chache")
response, content = h.request(url) 
print(response.status)
print(content[:52] )
print(len(content))
print(response.fromcache) 
print(dict(response.items())) 



