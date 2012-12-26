package org.easyGoingCrawler.framwork;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.crawler.EGCrawlerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *  One EGCrawler is a thread , it  contains 7 components:
 *  		<li>fetcher  </li>
 *			<li>docwriter  </li>
 *			<li>extractor  </li> 
 *			<li>fetchPolicy  </li>  
 *			<li>extractPolicy  </li>  
 *			<li>urlstore  </li>  
 *			<li>urlstoreBackup  </li>  
 *			<br>
 *
 *  EGCrawler will continuously do one task.
 *  One task is all the things one instance of web crawler must do as following :<br/>
 *   <li>use urlStoreto get a url from the url database</li>
 *   <li>use fetchPolicy to check if it is accepted</li>
 *   <li>ues fetcher to fecth the docuemnt of  the url</li>
 *   <li>ues extractor to extract  urls in this document</li>
 *   <li>use urlStore to store the urls to the database </li>
 *   <li>use DocWriter to  save the document </li>
 *   
 * @author Andy  weibobee@gmail.com 2012-9-13
 */

public class EGCrawler  extends Thread
{
	  private Logger logger = Logger.getLogger(EGCrawlerFactory.class);
	  
	  // flag to control this thread
	  private int flag = EGCrawler.PAUSE;
	  
	  // command to contrl a crawlTask
	  public static final int RUN = 0;	  
	  public static final int PAUSE = 1;
	  public static final int STOP = 2;
	  
	  
	  // fecher to fetcher a uri
	  private Fetcher  fetcher = null;
	  
	  // extractor to extract url from current document
	  private Extractor extractor = null;
	  
	  // privide the url to fetcher and save the urls extracted from document.
	  private URLScheduler scheduler = null;
	 
	  // docWriter to write the document to somewhere ,may be a hard disk or Mysql
	  private DocWriter docWriter = null;
	  
	  // interval of two fetching task in millsecond
	  private int interval = 1000;
	  
	 


	  public EGCrawler ()
	  {
		  
	  }
	  
	  
	  /**
		 *	EGCrawler will create a EGCrawler instance  according to the setting object
		 *  It will equip one EGCrawler with following components:
		 *		<li>fetcher  </li>
		 *		<li>docwriter  </li>
		 *		<li>extractor  </li> 
		 *		<li>fetchPolicy  </li>  
		 *		<li>extractPolicy  </li>  
		 *		<li>urlstore  </li>  
		 *		<li>urlstoreBackup  </li>  
		 *		<br/>
		 *	according to the configure file (setting.properties)
		 *	
		 * @author Andy  weibobee@gmail.com 2012-9-13
		 *
		 */
	  public EGCrawler(  Fetcher  fetcher , Extractor extractor , URLScheduler scheduler, DocWriter docWriter)
	  {		
			this.fetcher = fetcher;
			this.extractor = extractor;
			this.scheduler = scheduler;
			this.docWriter = docWriter;
	  }
	  
	  
	@Override
	public void run() 
	{
		// if the EGCrawler is not stop ,do the job of a task
		while(flag != EGCrawler.STOP )
		{
			try
			{			
				// if the state is PAUSE , do nothing and continue;
				if(flag == EGCrawler.PAUSE)
				{
					System.out.println("pause");
					TimeUnit.SECONDS.sleep(1);
					continue;
				}
				System.out.println(Thread.currentThread().getName() + "do one task");
				
				// do a crawl job
				this.doOneTask();			
												
				// sleep a time
				TimeUnit.MILLISECONDS.sleep(this.interval);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		System.out.println("stop");
	}
	
	/**
	 *  doOneTask is the heart of the crawler , it will do all the job of a crawler.
	 *  
	 *  doOneTask is contains follows steps:
	 *  <li>use urlStoreto get a url from the url database</li>
	 *  <li>use fetchPolicy to check if it is accepted</li>
	 *  <li>ues fetcher to fecth the docuemnt of  the url</li>
	 *  <li>ues extractor to extract  urls in this document</li>
	 *  <li>use urlStore to store the urls to the database </li>
	 *  <li>use DocWriter to  save the document </li>
	 *  
	 */
	private void  doOneTask()
	{ 	
		CrawlURI curl = this.scheduler.get();
		if(curl == null)
		{
			logger.info("doOneTask: get one null CrawlURL");
			return;
		}
		this.fetcher.fetch(curl);
		this.extractor.extract(curl);
		this.docWriter.write(curl);
		this.scheduler.put(curl);		
	}
	
	
	/**
	 * make the crawler thread pasue
	 */
	public void pauseCrawl()
	{
		this.flag = PAUSE;
	}
	
	/**
	 *  get the current status of the this crawler
	 *   
	 * @return
	 */
	public int getFlag() {
		return flag;
	}


	/**
	 * stop this crawler thread
	 */
	public void stopCrawl()
	{
		this.flag = STOP;
	}

	/**
	 * make the crawler thread pasue
	 */	
	public void startCrawl()
	{
		this.flag = RUN;
	}
	
	
	public Fetcher getFetcher()
	{
		return fetcher;
	}

	public void setFetcher(Fetcher fetcher)
	{
		this.fetcher = fetcher;
	}



	public Extractor getExtractor()
	{
		return extractor;
	}

	public void setExtractor(Extractor extractor)
	{
		this.extractor = extractor;
	}

	
	public DocWriter getDocWriter()
	{
		return docWriter;
	}

	public void setDocWriter(DocWriter docWriter)
	{
		this.docWriter = docWriter;
	}
	
	  public URLScheduler getScheduler() 
	  {
			return scheduler;
	  }


	public void setScheduler(URLScheduler scheduler) {
		this.scheduler = scheduler;
	}
	 public int getInterval() {
			return interval;
		}


		public void setInterval(int interval) {
			this.interval = interval;
		}
		
		public static void main (String [] args)
		{
			ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
			EGCrawler egcrawler = appcontext.getBean("egcrawler", EGCrawler.class);
			egcrawler.startCrawl();
			egcrawler.start();
			
			egcrawler.setDaemon(true);
			while(true)
			{
				try
				{
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
}
