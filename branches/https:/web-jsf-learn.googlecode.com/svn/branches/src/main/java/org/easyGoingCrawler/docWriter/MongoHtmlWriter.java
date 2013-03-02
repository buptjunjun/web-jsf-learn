package org.easyGoingCrawler.docWriter;

import java.net.InetAddress;
import java.net.URI;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.DAO.EGDAO;
import org.easyGoingCrawler.DAO.EGDAOMongo;
import org.easyGoingCrawler.analyzer.Analyzer;
import org.easyGoingCrawler.analyzer.BlogAnalyzer;
import org.easyGoingCrawler.analyzer.BlogerAnalyzer;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.util.Converter;
import org.easyGoingCrawler.util.URLAnalyzer;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
public class MongoHtmlWriter extends DocWriter
{
	Logger loger = Logger.getLogger(MongoHtmlWriter.class);
	private EGDAO egdao = null;
	private URLAnalyzer urAnalyzer = null;
	

	public MongoHtmlWriter(EGDAO daomongo )
	{
		this.egdao = daomongo;
	}
	
	@Override
	public void write(CrawlURI curl)
	{
		if(curl.getHttpstatus() != 200 || curl.getStatus() != CrawlURI.STATUS_OK)
			return;
		if(urAnalyzer.analyze(curl.getHost(), curl.getUrl()) == urAnalyzer.SAVE)
		{	
			Html html = new Html();
			html.setUrl(curl.getUrl());
			html.setEncode(curl.getEncode());
			html.setHost(curl.getHost());
			html.setHtml(curl.getContent());
			html.setCrawledDate(curl.getCollectDate());
					
			if(html.getUrl() == null)
				return;
			
			html.setId(Converter.urlEncode(curl.getUrl()));
			egdao.insert(html);
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
