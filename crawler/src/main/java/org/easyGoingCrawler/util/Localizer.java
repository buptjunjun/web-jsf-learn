package org.easyGoingCrawler.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.fetcher.HttpFetcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * read setting from configure file
 * @author Andy  weibobee@gmail.com 2012-9-22
 *
 */
public class Localizer
{
	static private Logger logger = Logger.getLogger(HttpFetcher.class);
	private static Properties p = new Properties();
	
	private static List<String> confFiles  = null;
	
	
	static public String getMessage(String key)
	{
		if(key == null)
			return null;
		return p.getProperty(key, null);
	}
	
	public Localizer(List<String> confFiles)
	{
		this.confFiles = confFiles;
		load();
	}
	private void load()
	{
		Properties props = new Properties();
		for(String path :this.confFiles)
		{
			Resource resource = new ClassPathResource(path);
			try
			{
				props.putAll(PropertiesLoaderUtils.loadProperties(resource));
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.p = props;
	}
	
    public static void main(String [] args)
    {
    	List<String>  list = Arrays.asList("egcrawler.properties","loginCnblog.txt");
    	Localizer l = new Localizer(list);
		
    	System.out.println(Localizer.getMessage("test"));
		System.out.println(Localizer.getMessage("formTag"));
    }
}

