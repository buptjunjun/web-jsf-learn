package org.easyGoingCrawler.docWriter;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.DAO.EGDAO;
import org.easyGoingCrawler.analyzer.Analyzer;
import org.easyGoingCrawler.analyzer.DoubanMovieAnalyzer;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.util.URLAnalyzer;

public class MovieWriter extends DocWriter
{
	private Analyzer<Movie> analyzer = null;
	Logger loger = Logger.getLogger(MovieWriter.class);
	private EGDAO egdao = null;
	private URLAnalyzer urAnalyzer = null;
	
	public MovieWriter(EGDAO egdao)
	{
		this.egdao = egdao;
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
			Movie movie = this.analyzer.analyze(curl);

			
			if(movie != null)	
			{							
				if(curl.getUrl() != null)
				{	
					System.out.println(Thread.currentThread().getName()+"-"+ "MovieWriter:insert movie :"+movie);
					
					loger.info(Thread.currentThread().getName()+"-"+"++Mongowriter: blog = "+movie);				
					this.egdao.insert(movie);
				}
			}	
			else
			{
				curl.setStatus(CrawlURI.STATUS_WRITE_ERROR);
			}
			
			
		}
	}
	
	public Analyzer<Movie> getAnalyzer()
	{
		return analyzer;
	}

	public void setAnalyzer(Analyzer<Movie> analyzer)
	{
		this.analyzer = analyzer;
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
}
