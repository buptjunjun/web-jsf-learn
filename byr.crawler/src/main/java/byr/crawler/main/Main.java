package byr.crawler.main;

import java.util.concurrent.TimeUnit;

import byr.crawler.docwriter.MongoHtmlWriter;
import byr.crawler.extractor.HTMLExtractor;
import byr.crawler.fetcher.HttpFetcherByHtmlUnit;
import byr.crawler.framework.Config;
import byr.crawler.framework.Crawler;
import byr.crawler.framework.DocWriter;
import byr.crawler.framework.Extractor;
import byr.crawler.framework.Fetcher;
import byr.crawler.framework.URLScheduler;
import byr.crawler.urlscheduler.RotateHostURLScheduler;

public class Main {

	public static void main(String[] args) {
		

		for (int i = 0; i < Config.threads; i++) 
		{
			Fetcher fetcher = new HttpFetcherByHtmlUnit(true);
			Extractor extractor= new HTMLExtractor();
			DocWriter docWriter = new MongoHtmlWriter(Config.db_name);
			URLScheduler scheduler = new  RotateHostURLScheduler(Config.host,Config.first_url,Config.db_name);			
			
			Crawler c = new Crawler( fetcher,  extractor, scheduler,  docWriter,  Config.host);
			c.setDaemon(true);
			c.startCrawl();
		}

		while (true) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*static public void main(String [] args)
    {
    	String keywords = "±±ÓÊ";
    	String url = "http://bbs.byr.cn/default";
    	Url u = new Url();
    	u.setUrl(url);
    	CrawlURI curl = new CrawlURI();
    	curl.setStatus(-1);
    	curl.setUrl(u);
    	new HttpFetcherByHtmlUnit(true).fetch(curl);
    	System.out.println(curl.getContent());
    	Analyzer sha = new SinaHtmlAnalyzer();
    	SearchResult srid = sha.analyze(ab);
    	WeiboMysql weiboh2 = new WeiboMysql();  	
		weiboh2.insert(srid);
    	System.out.println(srid.toString());
    
    }*/

}
