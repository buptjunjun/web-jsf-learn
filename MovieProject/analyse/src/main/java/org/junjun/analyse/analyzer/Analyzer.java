package org.junjun.analyse.analyzer;

import org.easyGoingCrawler.docWriter.Html;

public interface Analyzer<T>
{
	 public T analyze(String host,String encode,String content); 
	 public T analyze(Html html); 
	 public T analyze(Object obj); 
}
