package org.easyGoingCrawler.analyzer;

import java.util.Map;

import org.easyGoingCrawler.docWriter.Movie;
import org.easyGoingCrawler.framwork.CrawlURI;

public class ResourceAnalyzer implements Analyzer<Movie>
{
	Map<String,Analyzer> analyzers = null;
	
	public ResourceAnalyzer(Map<String,Analyzer> analyzers)
	{
		this.analyzers = analyzers;
	}
	
	public Movie analyze(String host,String encode,String content)
	{
		// TODO Auto-generated method stub
		Analyzer analyzer = analyzers.get(host);
		if(analyzer == null)
			return null;
		return (Movie) analyzer.analyze(host, encode, content);
	}
	public Movie analyze(CrawlURI curl)
	{
		if(curl == null || curl.getHost() == null)
			return null;
		// TODO Auto-generated method stub
		Analyzer analyzer = analyzers.get(curl.getHost());
		if(analyzer == null)
			return null;
		;
		// TODO Auto-generated method stub
		return (Movie) analyzer.analyze(curl);
	}
	
}
