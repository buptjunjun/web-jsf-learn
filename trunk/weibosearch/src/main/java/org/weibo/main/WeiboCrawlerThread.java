package org.weibo.main;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.weibo.analyzer.Analyzer;
import org.weibo.analyzer.SinaHtmlAnalyzer;
import org.weibo.common.AnalyzeBean;
import org.weibo.common.Constants;
import org.weibo.common.FetchBean;
import org.weibo.common.ParamStore;
import org.weibo.common.SearchResult;
import org.weibo.dao.WeiboDao;
import org.weibo.dao.WeiboDaoH2;
import org.weibo.dao.WeiboDaoTxt;
import org.weibo.fetcher.Fetcher;
import org.weibo.fetcher.SinaHtmlFetcher;

public class WeiboCrawlerThread extends Thread{

	private static final Logger logger = Logger.getLogger( WeiboCrawlerThread.class);
	
	private Fetcher fetcher = null;
	private Analyzer analyzer = null;
	private WeiboDao weibodao = null;
	private FetchBean fb = null;
	
	private int interval = 60;             // interval between two fetching rounds .
	private boolean flag = true;           // flag of stop 
	           
	public WeiboCrawlerThread(FetchBean fb)
	{
		this.interval = 60<fb.getInterval()?fb.getInterval():60;
		
		this.fb = fb;
		if(fb.getType() == Constants.SINA)
		{
			fetcher = new SinaHtmlFetcher(true);
			analyzer = new SinaHtmlAnalyzer();
		}
		
		
		weibodao = new WeiboDaoTxt();
		
	}
	
	@Override
	public void run() 
	{
		while(flag)
		{
	    	AnalyzeBean ab = fetcher.fetch(fb);
	    	SearchResult srid = analyzer.analyze(ab);
	    	weibodao.save(srid);
	    	
	    	try 
	    	{
	    		
				logger.info("thread:'"+ fb.getKeyword()+ "' will sleep " + this.interval + " seconds ");
	    		TimeUnit.SECONDS.sleep(this.interval);
				
			}
	    	catch (NumberFormatException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	catch (InterruptedException e) 
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	
		}
		
	}
	
	public void stopMe()
	{
		this.flag = false;
	}
	
	public void startMe()
	{
		this.flag = true;
		this.start();
	}
}
