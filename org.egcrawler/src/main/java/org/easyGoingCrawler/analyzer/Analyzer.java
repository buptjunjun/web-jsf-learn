package org.easyGoingCrawler.analyzer;

import org.easyGoingCrawler.framwork.CrawlURI;

public interface  Analyzer <T>
{
	 public T analyze(String host,String encode,String content); 
	 public T analyze(CrawlURI curl); 
}
