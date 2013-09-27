package org.easyGoingCrawler.docWriter;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.DAO.EGDAO;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.util.Converter;
import org.easyGoingCrawler.util.URLAnalyzer;
import org.junjun.analyse.analyzer.Analyzer;

/**
 * get one html and send one signal to other class  to process the html
 * @author andyWebsense
 *
 */
public class MongoHtmlWriterWithSignal extends DocWriter
{
	Logger logger = Logger.getLogger(MongoHtmlWriterWithSignal.class);
	private EGDAO egdao = null;
	private URLAnalyzer urAnalyzer = null;
	
	// analyzer to analyse the html
	private Analyzer analyzer = null;

	public MongoHtmlWriterWithSignal(EGDAO daomongo ,Analyzer analyzer)
	{
		this.egdao = daomongo;
		this.analyzer = analyzer;
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
			
			html.setId((String)curl.getReserve());
			egdao.insert(html);
			System.out.println(Thread.currentThread().getName()+"-"+ "##MongoHtmlWriter write a html:html="+html);
			logger.info(Thread.currentThread().getName()+"-"+ "##MongoHtmlWriter write a html:html="+html);
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
