import urllib.request
from http.client import HTTPConnection
HTTPConnection.debuglevel = 1

a_url = 'http://www.36kr.com/feed'
response = urllib.request.urlopen(a_url)
print(response.headers.as_string()) 
