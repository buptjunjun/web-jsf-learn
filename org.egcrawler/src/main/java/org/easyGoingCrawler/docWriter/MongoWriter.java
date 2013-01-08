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
import org.easyGoingCrawler.util.URLAnalyzer;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
public class MongoWriter extends DocWriter
{
	Logger loger = Logger.getLogger(MongoWriter.class);
	private EGDAO egdao = null;
	URLAnalyzer urAnalyzer = null;
	BlogerAnalyzer blogerAnalyzer = null;
	BlogAnalyzer blogAnalyzer = null;
	public MongoWriter(EGDAO daomongo,URLAnalyzer urlAnalyzer,BlogerAnalyzer blogerAnalyzer ,BlogAnalyzer blogAnalyzer )
	{
		this.egdao = daomongo;
		this.urAnalyzer = urlAnalyzer;
		this.blogerAnalyzer = blogerAnalyzer;
		this.blogAnalyzer= blogAnalyzer;
	}
	
	@Override
	public void write(CrawlURI curl)
	{
		if(curl.getHttpstatus() != 200 || curl.getStatus() != CrawlURI.STATUS_OK)
			return;
		
		if(urAnalyzer.analyze(curl.getHost(), curl.getUrl()) == urAnalyzer.SAVE)
		{
			// save a bolg or bloger.
			String host = curl.getHost();	
			Bloger bloger = this.blogerAnalyzer.analyze(curl);
			Blog blog = this.blogAnalyzer.analyze(curl);
			
			if(blog != null  && bloger != null)	
			{							
				if(curl.getUrl() != null)
				{	
					System.out.println(Thread.currentThread().getName()+"-"+ "MongoWriter:insert blog :"+blog);
					System.out.println(Thread.currentThread().getName()+"-"+"MongoWriter: inster bloger :"+bloger);
					loger.info(Thread.currentThread().getName()+"-"+"++Mongowriter: blog = "+blog);
					loger.info(Thread.currentThread().getName()+"-"+"++Mongowriter: bloger = "+bloger);
					
					this.egdao.insert(blog);
			    	this.egdao.insert(bloger);
				}
			}	
			else
			{
				curl.setStatus(CrawlURI.STATUS_WRITE_ERROR);
			}
			
			// clean HtmlPagePage;
			try
			{
				((HtmlPage)curl.getReserve()).cleanUp();
				curl.setReserve(null);
			}
			catch(Exception e)
			{
				
			}
		}
		
	}
	
	public EGDAO getEgdao()
	{
		return egdao;
	}

	public void setEgdao(EGDAO egdao)
	{
		this.egdao = egdao;
	}

	public URLAnalyzer getUrAnalyzer()
	{
		return urAnalyzer;
	}

	public void setUrAnalyzer(URLAnalyzer urAnalyzer)
	{
		this.urAnalyzer = urAnalyzer;
	}

	public BlogerAnalyzer getBlogerAnalyzer()
	{
		return blogerAnalyzer;
	}

	public void setBlogerAnalyzer(BlogerAnalyzer blogerAnalyzer)
	{
		this.blogerAnalyzer = blogerAnalyzer;
	}

	public BlogAnalyzer getBlogAnalyzer()
	{
		return blogAnalyzer;
	}

	public void setBlogAnalyzer(BlogAnalyzer blogAnalyzer)
	{
		this.blogAnalyzer = blogAnalyzer;
	}
	
}
