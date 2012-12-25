package org.easyGoingCrawler.analyzer;

public interface  Analyzer <T>
{
	 public T analyze(String host,String encode,byte[] content); 
}
