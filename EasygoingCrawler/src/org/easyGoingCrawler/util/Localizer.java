package org.easyGoingCrawler.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.fetcher.HttpFetcher;

/**
 * read setting from configure file
 * @author Andy  weibobee@gmail.com 2012-9-22
 *
 */
public class Localizer
{
	static private Logger logger = Logger.getLogger(HttpFetcher.class);
	private static Properties p = new Properties();
	
	static String confFile = "conf/setting.properties";
	static 
	{
		load();
	}
	
	static public String getMessage(String key)
	{
		if(key == null)
			return null;
		return p.getProperty(key, null);
	}
	
	static private void load()
	{
		
		FileInputStream fi = null;		
		try
		{
			File f = new File(confFile);
			if (!f.exists() || !f.canRead() || f.isHidden())
				return ;
			fi = new FileInputStream(f);		
			p.load(fi);
		}
		catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(confFile + " not found :" + e1.getMessage());
		} 
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(confFile + " io exception :" + e1.getMessage());
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
	
    public static void main(String [] args)
    {
    	String interval = Localizer.getMessage("interval");
    	String urlstore = Localizer.getMessage("urlstoreBackup");
    	String aa = Localizer.getMessage("aa");
    	System.out.println(interval+", "+aa+ " ,"+urlstore);
    }
}

