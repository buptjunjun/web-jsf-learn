import charpter3_searchengine as searchengine

pagelist=['http://kiwitobes.com/wiki/Perl.html']
crawler=searchengine.Crawler('searchengine')
crawler.crawl(pagelist)