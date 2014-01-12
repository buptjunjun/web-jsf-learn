package org.weibo.main;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.weibo.common.Constants;
import org.weibo.common.FetchBean;
import org.weibo.common.KeywordInfo;
import org.weibo.common.WeiboUtil;

import com.google.gson.Gson;


public class MainThread {

	private static final Logger logger = Logger.getLogger(MainThread.class);
	
	// keywords maps to the corresponding Thread that is fetching the WEIBOS concerning the keywords
	private Map<String,Thread> crawlerMap = new HashMap<String,Thread>();
	
	//keywords
	private List<KeywordInfo> keywords = new ArrayList<KeywordInfo>();
	
	private  static final String keywords_file  = "src/main/resources/keywords.json"; 
	
	public static void main(String[] args) 
	{
		new MainThread().startAll();
		
	}
	
	public MainThread() 
	{
		refresh();
	}
	
	/**
	 * start the thread according to the keyword
	 * @param keyword
	 */
	public void stopThread4Keyword(String keyword)
	{
		keyword = keyword.trim();
		if(StringUtils.isEmpty(keyword) || !this.crawlerMap.containsKey(keyword))
		{
			logger.warn("keyword: '"+keyword+"' is empty or does not exists");
			return;
		}
		
		// start the thread
		WeiboCrawlerThread t = (WeiboCrawlerThread) this.crawlerMap.get(keyword);
		if(t!=null && t.isAlive())
		{
			t.stopMe();
			logger.info("stop thread:"+keyword);
		}
		else
		{
			logger.info("thread: " +keyword+" is already down");
		}
	}
	
	/**
	 * start the thread according to the keyword
	 * @param keyword
	 */
	public void startThread4Keyword(String keyword)
	{
		keyword = keyword.trim();
		if(StringUtils.isEmpty(keyword) || !this.crawlerMap.containsKey(keyword))
		{
			logger.warn("keyword: '"+keyword+"' is empty or does not exists");
			return;
		}
		
		// start the thread
		WeiboCrawlerThread t = (WeiboCrawlerThread) this.crawlerMap.get(keyword.trim());
		if(t != null && !t.isAlive())
		{
			t.startMe();
			logger.info("start thread:"+keyword);
		}
		else
		{
			logger.info("thread: " +keyword+" is already running");
		}
	}
	
	/**
	 * start all thread in the map
	 */
	public void startAll()
	{
		for(Entry entry: this.crawlerMap.entrySet())
		{
			String keyword = (String)entry.getKey();
			this.startThread4Keyword(keyword);
		}
	}
	
	/**
	 * start all thread in the map
	 */
	public void stopAll()
	{
		for(Entry entry: this.crawlerMap.entrySet())
		{
			String keyword = (String)entry.getKey();
			this.stopThread4Keyword(keyword);
		}
	}
	
	
	
	
	/**
	 *  load keywords from file.
	 */
	public void loadKeyWords()
	{
		 this.keywords = WeiboUtil.loadKeyWords();		
	}
	
	/**
	 * reload the keywords and create new crawling thread .
	 */
	public void refresh()
	{
		this.loadKeyWords();
		int keywordsSize = this.keywords.size();
		
		// for each keyword, create a thread to fetch concerning weibos;
		for(KeywordInfo keywordinfo :keywords)
		{
			String keyword =  keywordinfo.getKeyword();
			int interval = keywordinfo.getInterval();
			
			if(!StringUtils.isEmpty(keyword))
			{
				String keyword_trim = keyword.trim();
				
				// create the thread but not start
				FetchBean fb = new FetchBean(keyword_trim,Constants.TX,interval);
				if(!crawlerMap.containsKey(keyword_trim))
				{
					WeiboCrawlerThread t = new WeiboCrawlerThread(fb);
					t.setName(keyword_trim);
					this.crawlerMap.put(keyword_trim, t);
					logger.info("create thread:"+keyword_trim);
				}
			}
			
		}
	}

}
