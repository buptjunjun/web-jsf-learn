package org.easyGoingCrawler.analyzer;

import java.util.Map;

import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.framwork.CrawlURI;

public class BlogerAnalyzer implements Analyzer<Bloger>
{
	Map<String,Analyzer> analyzers = null;
	
	public BlogerAnalyzer(Map<String,Analyzer> analyzers)
	{
		this.analyzers = analyzers;
	}
	
	public Bloger analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		Analyzer analyzer = analyzers.get(host);
		if(analyzer == null)
			return null;
		return (Bloger) analyzer.analyze(host, encode, content);
	}
	public Bloger analyze(CrawlURI curl)
	{
		if(curl == null || curl.getHost() == null)
			return null;
		// TODO Auto-generated method stub
		Analyzer analyzer = analyzers.get(curl.getHost());
		if(analyzer == null)
			return null;
		;
		// TODO Auto-generated method stub
		return (Bloger) analyzer.analyze(curl);
	}
	
}
