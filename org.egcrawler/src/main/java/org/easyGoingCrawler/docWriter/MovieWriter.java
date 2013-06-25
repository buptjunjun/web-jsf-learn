package org.easyGoingCrawler.docWriter;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.DAO.EGDAO;
import org.easyGoingCrawler.analyzer.Analyzer;
import org.easyGoingCrawler.analyzer.DoubanMovieAnalyzer;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;

public class MovieWriter extends DocWriter
{
	private Analyzer<Movie> analyzer = null;
	Logger loger = Logger.getLogger(MovieWriter.class);
	private EGDAO egdao = null;
	
	@Override
	public void write(CrawlURI curl)
	{
		if(curl == null || curl.getHttpstatus() != 200)
			return;
		Movie movie = this.analyzer.analyze(curl);
		egdao.insert(movie);	
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
}
