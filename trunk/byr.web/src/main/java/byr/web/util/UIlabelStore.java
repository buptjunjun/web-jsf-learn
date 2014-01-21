package byr.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class UIlabelStore 
{
	static private Logger logger = Logger.getLogger(UIlabelStore.class);
	private static Properties p = new Properties();
	private static String UILabelfile = "UILabel.properties";
	
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
			String confFile = Thread.currentThread().getContextClassLoader().getResource(UILabelfile).getFile();
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
			logger.error(UILabelfile + " not found :" + e1.getMessage());
		} 
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(UILabelfile + " io exception :" + e1.getMessage());
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
    	String interval = UIlabelStore.getMessage("interval");
    	String password = UIlabelStore.getMessage("password");
    	System.out.println(interval);
    }


}
