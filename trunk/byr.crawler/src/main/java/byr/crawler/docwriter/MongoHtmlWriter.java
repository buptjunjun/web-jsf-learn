package byr.crawler.docwriter;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;


import byr.crawler.framework.CrawlURI;
import byr.crawler.framework.DocWriter;
import byr.crawler.framework.Html;
import byr.crawler.framework.Url;
import byr.crawler.persist.DAOMongo;
import byr.crawler.utils.CommonUtils;

public class MongoHtmlWriter extends DocWriter
{
	Logger loger = Logger.getLogger(MongoHtmlWriter.class);
	private DAOMongo dao = null;
	

	public MongoHtmlWriter(String dbName) {
		
		this.dao = new DAOMongo(dbName);
	
	}
	
	public MongoHtmlWriter(String dbhost, int port, String dbName) {
			
			this.dao = new DAOMongo(dbhost,port,dbName);	
		}
	
	
	@Override
	public void write(CrawlURI curl)
	{
		if(curl.getHttpstatus() != 200 || curl.getStatus() != CrawlURI.STATUS_OK)
			return;
		
		Url url = curl.getUrl();
		Html html = new Html();
		html.setUrl(url.getUrl());
		html.setEncode(curl.getEncode());
		html.setHost(curl.getHost());
		html.setHtml(curl.getContent());
		html.setCrawledDate(curl.getCollectDate());					
		html.setId(CommonUtils.urlEncode(url.getUrl()));
		
		if(url.getType() == Url.URL_SAVE)
		{	
			html.setMagicNum(-1);
			dao.insert(html);
		}
	}
}
