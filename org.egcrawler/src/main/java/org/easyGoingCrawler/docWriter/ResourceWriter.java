package org.easyGoingCrawler.docWriter;

import java.util.List;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.DAO.EGDAO;
import org.easyGoingCrawler.analyzer.Analyzer;
import org.easyGoingCrawler.analyzer.DoubanMovieAnalyzer;
import org.easyGoingCrawler.bean.BResource;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;
import org.easyGoingCrawler.util.URLAnalyzer;

public class ResourceWriter extends DocWriter
{
	private Analyzer<List<BResource>> analyzer = null;
	Logger loger = Logger.getLogger(ResourceWriter.class);
	private EGDAO egdao = null;
	
	public ResourceWriter(EGDAO egdao)
	{
		this.egdao = egdao;
	}
	@Override
	public void write(CrawlURI curl)
	{
		if(curl.getHttpstatus() != 200 || curl.getStatus() != CrawlURI.STATUS_OK)	
			return;
	
		List<BResource> resources = this.analyzer.analyze(curl);
		
		if(resources != null)	
		{							
			for(BResource r : resources)
			{	
				System.out.println(Thread.currentThread().getName()+"-"+ "ResourceWriter insert a resource: :"+r);
				this.egdao.insert(r);
			}
		}	
	}
	

	public void setAnalyzer(Analyzer<List<BResource>> analyzer)
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
