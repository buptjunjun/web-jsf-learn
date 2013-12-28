package org.weibo.main;

import java.util.concurrent.TimeUnit;

import org.weibo.analyzer.Analyzer;
import org.weibo.analyzer.SinaHtmlAnalyzer;
import org.weibo.common.AnalyzeBean;
import org.weibo.common.Constants;
import org.weibo.common.FetchBean;
import org.weibo.common.ParamStore;
import org.weibo.common.SearchResultID;
import org.weibo.common.Weiboh2;
import org.weibo.dao.WeiboDao;
import org.weibo.dao.WeiboDaoH2;
import org.weibo.fetcher.Fetcher;
import org.weibo.fetcher.SinaHtmlFetcher;

public class WeiboCrawlerThread extends Thread{

	private Fetcher fetcher = null;
	private Analyzer analyzer = null;
	private WeiboDao weibodao = null;
	
	private boolean flag = true;
	private FetchBean fb = null;
	public WeiboCrawlerThread(FetchBean fb)
	{
		this.fb = fb;
		if(fb.getType() == Constants.SINA)
		{
			fetcher = new SinaHtmlFetcher(true);
			analyzer = new SinaHtmlAnalyzer();
		}
		
		weibodao = new WeiboDaoH2();
		
	}
	
	@Override
	public void run() 
	{
		while(flag)
		{
	    	AnalyzeBean ab = fetcher.fetch(fb);
	    	SearchResultID srid = analyzer.analyze(ab);
	    	weibodao.save(srid);
	    	
	    	try 
	    	{
				TimeUnit.SECONDS.sleep(Integer.parseInt(ParamStore.getMessage("interval")));
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
}
