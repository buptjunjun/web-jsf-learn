package org.easyGoingCrawler.crawler;

import java.util.ArrayList;
import java.util.List;

import org.easyGoingCrawler.framwork.EGCrawler;

/**
 * 	EGCrawlerPool contains a set of EGCrawler and will control them  
 *   @author Andy  weibobee@gmail.com 2012-9-21
 *
 */
public class EGCrawlerPool 
{
	
	private EGCrawlerFactory crawlerFactory = null;
	
	// the limit crawler in this EGCrawlerPool
	private int crawlerLimit = 50;
	
	private List<EGCrawler> crawlers = new  ArrayList<EGCrawler>();
	
	private int countPaused = 0;
	private int countStopped = 0;
	private int countRunning = 0;
	
	/**
	 * 
	 * @param configure configure file for this crawler pool
	 */
	public EGCrawlerPool()
	{
		crawlerFactory = new EGCrawlerFactory();
	}
	
	/**
	 * add N crawler to the pool
	 * if n < 0 do nothing ,
	 * @param n
	 */
	public void addNCrawler(int n)
	{
		while(n >= 0)
		{
			if(this.crawlers.size() < this.crawlerLimit)
				this.addOneCrawler();
			else break;
			n--;
		}
	}
	
	/**
	 * add one crawler to this pool
	 */
	public void addOneCrawler()
	{
		EGCrawler crawler = this.crawlerFactory.createCrawler();
		if(crawler != null && this.crawlers.size() < this.crawlerLimit)
		{
			this.crawlers.add(crawler);
			crawler.startCrawl();
			crawler.start();
		}
	}
	

	/**
	 * 
	 * @return total crawlers in this pool
	 */
	public int size()
	{
		if(this.crawlers != null)
			return this.crawlers.size();
		
		return 0;
	}
	
	
	/**
	 * 
	 * get the  number of paused ,stopped  and running EGCrawler 
	 */
	public synchronized void getStatitics()
	{
		 countPaused = 0;
		 countStopped = 0;
		 countRunning= 0;
		if(this.crawlers != null)
		{
			for(int i = 0; i < this.crawlers.size(); i++)
			{
				EGCrawler c = this.crawlers.get(i);
				switch(c.getFlag())
				{
					case EGCrawler.PAUSE:
						countPaused++;
						break;
					case EGCrawler.RUN:
						countRunning++;
						break;
					case EGCrawler.STOP:
						countStopped++;
						break;
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @return total crawlers in this pool
	 */
	public synchronized int sizePause()
	{
		int count = 0; 
		if(this.crawlers != null)
		{
			for(int i = 0; i < this.crawlers.size(); i++)
			{
				EGCrawler c = this.crawlers.get(i);
				if(c.getFlag() == EGCrawler.PAUSE)
				{
					count++;
				}
			}
			return count;
		}
		
		return 0;
	}
	

	public int getCrawlerLimit() {
		return crawlerLimit;
	}

	public void setCrawlerLimit(int crawlerLimit) {
		this.crawlerLimit = crawlerLimit;
	}

	public int getCountPaused() {
		return countPaused;
	}

	public void setCountPaused(int countPaused) {
		this.countPaused = countPaused;
	}

	public int getCountStopped() {
		return countStopped;
	}

	public void setCountStopped(int countStopped) {
		this.countStopped = countStopped;
	}

	public int getCountRunning() {
		return countRunning;
	}

	public void setCountRunning(int countRunning) {
		this.countRunning = countRunning;
	}

}
