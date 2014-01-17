package byr.crawler.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


/**
 * 
 * configruation of all the stuff.
 * @author andy
 *
 */
public class Config 
{
	private static Logger logger = Logger.getLogger(Config.class);
	
	public static int interval = 30; // by seconds
	public static int threads  = 1;  // amount of thread to fetch
	public static String host = null; // host name
	public static String first_url = null;          // the first url to fetch
	public static String db_name = null;
	
	public static String url_save_parttern = null;
	public static String url_repeat_pattern=null;
	public static String url_hold_pattern=null;
	
	
	static String conf = "conf/conf.properties";	
	static {
		init();
		
	}	
	static void init()
	{
		InputStream in = Config.class.getClassLoader().getResourceAsStream(conf);
		Properties pro = new Properties ();
		try {
			pro.load(in);
			interval = Integer.parseInt(pro.getProperty("interval"));
			threads = Integer.parseInt(pro.getProperty("threads"));
			host = pro.getProperty("host");
			first_url = pro.getProperty("first_url");
			db_name = pro.getProperty("db_name");
			url_save_parttern = pro.getProperty("url_save_parttern");
			url_repeat_pattern = pro.getProperty("url_repeat_pattern");
			url_hold_pattern = pro.getProperty("url_hold_pattern");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("init error:"+e.getMessage());
		}
		finally
		{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	static public int classifyUrl(String url)
	{
		if (host == null || url == null)
			return Url.URL_DELETE;
			
		if(Pattern.matches(url_save_parttern, url))
			return Url.URL_SAVE;
		
		if(Pattern.matches(url_repeat_pattern, url))
			return Url.URL_REPEAT;
		
		if(Pattern.matches(url_hold_pattern, url))
			return Url.URL_HOLD;
		
		return Url.URL_DELETE;
		
	}

	public static void main(String [] args)
	{
		
	}
}
