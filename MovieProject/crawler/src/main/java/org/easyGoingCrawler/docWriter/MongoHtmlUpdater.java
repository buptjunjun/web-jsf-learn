package org.easyGoingCrawler.docWriter;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.DAO.EGDAO;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.util.Converter;
import org.easyGoingCrawler.util.URLAnalyzer;

public class MongoHtmlUpdater extends DocWriter
{
	Logger logger = Logger.getLogger(MongoHtmlUpdater.class);
	private EGDAO egdao = null;
	private URLAnalyzer urAnalyzer = null;
	

	public MongoHtmlUpdater(EGDAO daomongo )
	{
		this.egdao = daomongo;
	}
	
	@Override
	public void write(CrawlURI curl)
	{
		if(curl.getHttpstatus()!=-1 && urAnalyzer.analyze(curl.getHost(), curl.getUrl()) == urAnalyzer.SAVE)
		{	
			
			Html html = new Html();
			html.setUrl(curl.getUrl());
			html.setEncode(curl.getEncode());
			html.setHost(curl.getHost());
			html.setHtml(curl.getContent());
			html.setCrawledDate(curl.getCollectDate());
			html.setMagicNum(-1);
			
			if(html.getUrl() == null)
				return;
			
			//html.setId(Converter.urlEncode(curl.getUrl()));
			html.setId((String)curl.getReserve());
			egdao.insert(html);
			System.out.println(Thread.currentThread().getName()+"-"+ "##MongoHtmlUpdater write a html:curl="+curl);
			logger.info(Thread.currentThread().getName()+"-"+ "##MongoHtmlUpdater write a html:curl="+curl);
		}
		
	}
	public URLAnalyzer getUrAnalyzer()
	{
		return urAnalyzer;
	}

	public void setUrAnalyzer(URLAnalyzer urAnalyzer)
	{
		this.urAnalyzer = urAnalyzer;
	}
}
