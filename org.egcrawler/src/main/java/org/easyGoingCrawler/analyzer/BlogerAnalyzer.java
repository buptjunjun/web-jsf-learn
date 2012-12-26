package org.easyGoingCrawler.analyzer;

import java.util.Map;

import org.easyGoingCrawler.docWriter.Blog;
import org.easyGoingCrawler.docWriter.Bloger;

public class BlogerAnalyzer implements Analyzer<Bloger>
{
	Map<String,Analyzer> analyzers = null;
	
	public BlogerAnalyzer(Map<String,Analyzer> analyzers)
	{
		this.analyzers = analyzers;
	}
	
	public Bloger analyze(String host,String encode,byte[] content)
	{
		// TODO Auto-generated method stub
		Analyzer analyzer = analyzers.get(host);
		if(analyzer == null)
			return null;
		return (Bloger) analyzer.analyze(host, encode, content);
	}
	
}
