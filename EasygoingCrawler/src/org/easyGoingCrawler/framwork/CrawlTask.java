package org.easyGoingCrawler.framwork;

import java.util.List;
import java.util.concurrent.TimeUnit;



/**
 *  One CrawlTask contains a fetchpolicy 
 * 
 * @author Andy  weibobee@gmail.com 2012-9-12
 */

public class CrawlTask  implements Runnable
{
	  // flag to control this thread
	  int flag = CrawlTask.STOP;
	  
	  // command to contrl a crawlTask
	  public static final int RUN = 0;	  
	  public static final int PAUSE = 1;
	  public static final int STOP = 2;
	  
	  
	  // fecher to fetcher a uri
	  private Fetcher  fetcher = null;
	  
	  // the policy to decide whether a url is accepted before we fetch phase
	  private Policy fetchPolicy = null;
	  
	  // the policy to decide whether a url is accepted when we extract from current document and want to store it in database
	  private Policy extractPolicy = null;
	  
	  // extractor to extract url from current document
	  private Extractor extractor = null;
	  
	  // setting for this task
	  private Setting setting = null;
	  
	  // get or store a url to url Database 
	  private URLStore urlStore = null;
	  
	  // docWriter to write the document to somewhere ,may be a hard disk or Mysql
	  private DocWriter docWriter = null;
	  
	  
	  public CrawlTask(Setting setting)
		{
			this.setting = setting;
		}
	  
	@Override
	public void run() 
	{
		// if the CrawlTask is not stop ,do the job of a task
		while(flag != CrawlTask.STOP )
		{
			try
			{			
				// if the state is PAUSE , do nothing and continue;
				if(flag == CrawlTask.PAUSE)
				{
					TimeUnit.SECONDS.sleep(1);
					continue;
				}
				
				// do a crawl job
				this.doOneTask();				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
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
		String url = this.urlStore.get("");
		
		// use fetchPolicy to check if it is accepted
		if (!this.fetchPolicy.process(url))
			return;
		
		//ues fetcher to fecth the docuemnt of  the url
		String content = this.fetcher.fetch(url);
		
		if(content == null) return;
		
		// ues extractor to extract  urls in this document
		List<String> urls = this.extractor.extract(content);
		
		//use urlStore to store the urls to the database
		if(urls != null)
		{
			for(String u : urls)
			{
				// if the url meets the this extractPolicy save it to url database.
				if(this.extractPolicy.process(u))
				{
					this.urlStore.put(u);
				}
			}
		}
		
		
		// write this document somewhere
		this.docWriter.write(content);
		
	}
	
	
	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag = flag;
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

	public Setting getSetting()
	{
		return setting;
	}

	public void setSetting(Setting setting)
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
	  
}
