package byr.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfStore 
{
	static private Logger logger = Logger.getLogger(ConfStore.class);
	private static Properties p = new Properties();
	private static String WebConfFile = "webconf.properties";
	
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
		
		InputStream in=null;		
		try
		{
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(WebConfFile);
			p.load(in);
		}
		catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(WebConfFile + " not found :" + e1.getMessage());
		} 
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(WebConfFile + " io exception :" + e1.getMessage());
		}
		finally
		{
			try
			{
				if (in != null)
					in.close();
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
    	String interval = ConfStore.getMessage("interval");
    	String password = ConfStore.getMessage("password");
    	System.out.println(interval);
    }


}
