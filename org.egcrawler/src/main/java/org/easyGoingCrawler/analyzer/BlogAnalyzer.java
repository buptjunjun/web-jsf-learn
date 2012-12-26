package org.easyGoingCrawler.analyzer;

import java.util.Map;

import org.easyGoingCrawler.docWriter.Blog;

public class BlogAnalyzer implements Analyzer<Blog>
{
	Map<String,Analyzer> analyzers = null;
	public BlogAnalyzer(Map<String,Analyzer> analyzers)
	{
		this.analyzers = analyzers;
	}
	
	public Blog analyze(String host,String encode,byte[] content)
	{
		Analyzer analyzer = analyzers.get(host);
		if(analyzer == null)
			return null;
		// TODO Auto-generated method stub
		return (Blog) analyzer.analyze(host, encode, content);
	}
	
}
