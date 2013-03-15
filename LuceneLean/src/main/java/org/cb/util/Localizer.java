package org.cb.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * read setting from configure file
 * @author Andy  weibobee@gmail.com 2012-9-22
 *
 */
public class Localizer
{
	private static Properties p = new Properties();
	
	static String confFile = "setting.properties";
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
		InputStream stream = null;
		try
		{
			stream = Localizer.class.getClassLoader().getResourceAsStream(confFile);		
			p.load(stream);
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
				if (stream != null)
					stream.close();
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
    	String interval = Localizer.getMessage("luceneDir");
    	System.out.println("");
    }
}

