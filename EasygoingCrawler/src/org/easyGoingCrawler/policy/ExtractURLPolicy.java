package org.easyGoingCrawler.policy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.easyGoingCrawler.framwork.Policy;

import com.mysql.jdbc.StringUtils;

/*
* ExtractURLPolicy is an implemetation of Policy. It will make decision if one url will be accepted 
* when we extract urls from html and want to put it to urlstore.
* for example , when we choose one url from "url store"(maybe in a database , it all depends), 
* and want to fetch the html of it.  URLPolicy will decide whether to abandon it 
* or accept it . Maybe the url is "http://www.abc.com/music.swf", we have no interest in it ,
* so we can abandon it  
* 
*  @author Andy  weibobee@gmail.com 2012-9-12
*/
public class ExtractURLPolicy implements Policy 
{
	private String regx = null;
	private String configureFile = "conf/policy.properties";
	
	public ExtractURLPolicy() 
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
			regx = p.getProperty("extractURLreg");
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
	/**
	 * decide if the url will be accepted
	 * @param url to prcess
	 * @return true if the url is accepted or false
	 */
	@Override
	public boolean process(String url) {
		if(StringUtils.isNullOrEmpty(this.regx))
			return true;
		
		if(StringUtils.isNullOrEmpty(url))
			return false;
		
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(url);
		if(m.find())
			return true;
		
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String [] args)
	{
		ExtractURLPolicy p = new ExtractURLPolicy();
		boolean ret = p.process("http://webdev.csdn.net/a");		
		System.out.println(ret);
	}
}
