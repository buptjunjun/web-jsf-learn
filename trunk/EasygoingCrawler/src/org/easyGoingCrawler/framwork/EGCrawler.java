package org.easyGoingCrawler.framwork;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.easyGoingCrawler.setting.EGCrawlerSetting;



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

public class EGCrawler  implements Runnable
{
	  // flag to control this thread
	  private int flag = EGCrawler.PAUSE;
	  
	  // command to contrl a crawlTask
	  private static final int RUN = 0;	  
	  private static final int PAUSE = 1;
	  private static final int STOP = 2;
	  
	  
	  // fecher to fetcher a uri
	  private Fetcher  fetcher = null;
	  
	  // the policy to decide whether a url is accepted before we fetch phase
	  private Policy fetchPolicy = null;
	  
	  // the policy to decide whether a url is accepted when we extract from current document and want to store it in database
	  private Policy extractPolicy = null;
	  
	  // extractor to extract url from current document
	  private Extractor extractor = null;
	  
	  // setting for this task
	  private EGCrawlerSetting setting = null;
	  
	  // get or store a url that we are interested in  to url Database 
	  private URLStore urlStore = null;
	  
	  // get or store a url that we are not interested in  to backup url Database
	  private URLStore urlStoreBackup = null;
	  
	

	// docWriter to write the document to somewhere ,may be a hard disk or Mysql
	  private DocWriter docWriter = null;
	  
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
	  public EGCrawler(EGCrawlerSetting setting)
	  {
			this.setting = setting;		
			try
			{
				// get the name of components
				URLStore urlstore = (URLStore)Class.forName(this.setting.getUrlstore()).newInstance();
				Fetcher fether = (Fetcher)Class.forName(this.setting.getFetcher()).newInstance();
				Extractor ectractor = (Extractor)Class.forName(this.setting.getExtractor()).newInstance();
				Policy extractPolicy = (Policy)Class.forName(this.setting.getExtractPolicy()).newInstance();
				Policy fetchPolicy = (Policy)Class.forName(this.setting.getFetchPolicy()).newInstance();
				DocWriter docWriter = (DocWriter)Class.forName(this.setting.getDocwriter()).newInstance();
				URLStore urlstoreBackup = (URLStore)Class.forName(this.setting.getUrlstoreBackup()).newInstance();
				
				// add components to a EGCrawler
				this.setUrlStore(urlstore);
				this.setFetcher(fether);			
				this.setExtractor(ectractor);
				this.setExtractPolicy(extractPolicy);			
				this.setFetchPolicy(fetchPolicy);
				this.setDocWriter(docWriter);
				
				// set back url store , this is optional
				if(this.setting.getUrlstoreBackup() != null)
				{
					try
					{
						this.setUrlStoreBackup(urlstoreBackup);
					}
					catch(Exception e)
					{
						System.out.println("add UrlStoreBackup to crawler error");
					}
				}
			
			}
			catch (Exception e1)
			{
				e1.printStackTrace();				
				// if there is some exception happened , set flag to STOP, and the thread will not do any task  
				this.flag = STOP;
			}	
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
				System.out.println("do one task");
				
				// do a crawl job
				this.doOneTask();			
												
				// sleep a time
				TimeUnit.MILLISECONDS.sleep(this.setting.getInterval());
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
		// use urlStoreto get a url from the url database
		String url = this.urlStore.get();
		
		// use fetchPolicy to check if it is accepted
		if (!this.fetchPolicy.process(url))
			return;
		
		//ues fetcher to fecth the docuemnt of  the url
		String content = this.fetcher.fetch(url);
		
		if(content == null) return;
		
		// ues extractor to extract  urls in this document
		List<String> urls = this.extractor.extract(content);
		
		System.out.println("urls:" + urls);
		
		//use urlStore to store the urls to the database
		if(urls != null)
		{
			for(String u : urls)
			{
				// if we are interested in the url  , save it to url database.
				if(this.extractPolicy.process(u))
				{
					this.urlStore.put(u);
				}
				// if we are not interested in the url  , save it to backup url database.
				else
				{
					if(this.urlStoreBackup != null)
						this.urlStoreBackup.put(url);
				}
			}
		}
		
		
		// write this document somewhere
		this.docWriter.write(content,url);
		
	}
	
	
	/**
	 * make the crawler thread pasue
	 */
	public void pause()
	{
		this.flag = PAUSE;
	}
	
	/**
	 * stop this crawler thread
	 */
	public void stop()
	{
		this.flag = STOP;
	}

	/**
	 * make the crawler thread pasue
	 */	
	public void start()
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

	public Policy getFetchPolicy()
	{
		return fetchPolicy;
	}

	public void setFetchPolicy(Policy fetchPolicy)
	{
		this.fetchPolicy = fetchPolicy;
	}

	public Policy getExtractPolicy()
	{
		return extractPolicy;
	}

	public void setExtractPolicy(Policy extractPolicy)
	{
		this.extractPolicy = extractPolicy;
	}

	public Extractor getExtractor()
	{
		return extractor;
	}

	public void setExtractor(Extractor extractor)
	{
		this.extractor = extractor;
	}

	public EGCrawlerSetting getSetting()
	{
		return setting;
	}

	public void setSetting(EGCrawlerSetting setting)
	{
		this.setting = setting;
	}

	public URLStore getUrlStore()
	{
		return urlStore;
	}

	public void setUrlStore(URLStore urlStore)
	{
		this.urlStore = urlStore;
	}

	public DocWriter getDocWriter()
	{
		return docWriter;
	}

	public void setDocWriter(DocWriter docWriter)
	{
		this.docWriter = docWriter;
	}
	
	public URLStore getUrlStoreBackup()
	{
		return urlStoreBackup;
	}


	public void setUrlStoreBackup(URLStore urlStoreBackup)
	{
		this.urlStoreBackup = urlStoreBackup;
	}
  
}
