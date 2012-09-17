package org.easyGoingCrawler.setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.easyGoingCrawler.framwork.Setting;

/**
 * save all the setting information one EGCrawler needed form a .properties file
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 */

public class EGCrawlerSetting
{
	// configure file name 
	private String configureFile = null;
	
	// all the component names that one EGCrawler needed
	private String fetcher = null;
	private String docwriter =null;
	private String extractor =null;
	private String fetchPolicy = null;
	private String extractPolicy = null;
	private String urlstore = null;
	private String urlstoreBackup = null;
	
	// task interval in millisecond
	private int interval = 500;
	

	public EGCrawlerSetting(String configureFile)
	{
		this.configureFile = configureFile;
		this.readSetting();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private void readSetting() 
	{
		Properties p = new Properties();
		FileInputStream fi = null;		
		try
		{
			File f = new File(this.configureFile);
			if (!f.exists() || !f.canRead() || f.isHidden())
				return ;
			fi = new FileInputStream(f);
			
			p.load(fi);
			
			 // all the component names that one EGCrawler needed 
			 fetcher = p.getProperty("fetcher");
			 docwriter = p.getProperty("docwriter");
			 extractor = p.getProperty("extractor");
			 fetchPolicy = p.getProperty("fetchPolicy");
			 extractPolicy = p.getProperty("extractPolicy");
			 urlstore = p.getProperty("urlstore");
			 urlstoreBackup =  p.getProperty("urlstoreBackup");
		}
		catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			try
			{
				if (fi != null)
					fi.close();
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int getInterval()
	{
		return interval;
	}


	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	
	public String getConfigureFile()
	{
		return configureFile;
	}


	public String getFetcher()
	{
		return fetcher;
	}


	public String getDocwriter()
	{
		return docwriter;
	}


	public String getExtractor()
	{
		return extractor;
	}


	public String getFetchPolicy()
	{
		return fetchPolicy;
	}


	public String getExtractPolicy()
	{
		return extractPolicy;
	}


	public String getUrlstore()
	{
		return urlstore;
	}


	public String getUrlstoreBackup()
	{
		return urlstoreBackup;
	}
}
