import urllib3
from bs4 import BeautifulSoup


soup = BeautifulSoup("<p>Some<b>bad<i>HTML")
print (soup.prettify())

http = urllib3.PoolManager()
r = http.request('GET', 'http://google.com/')
print (r.status, r.data)