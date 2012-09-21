package org.easyGoingCrawler.urlStore;

import org.easyGoingCrawler.util.ZipUtil;
import org.jsoup.helper.StringUtil;

import com.mysql.jdbc.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 * URLStoreMysql will store the URL in MYSQL.
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
public class URLStoreMysql 
{
	protected MysqlDB mysqldb = null;
	
	// the cached to store the currently being  crawled urls
	private static  final Hashtable urls = new Hashtable();
	
	// max size of the urls
	private static  int   MaxputCachedURLS = 250;
	
	// the table name  storing the urls 
	private  String tableName = "urlstore";
	
	// configuring file name
	private String configureFile = "conf/urlstoreMysql.properties";
	
	// this string defined how to get a url from url store
	private String condition4get = null;
	
	private String seedsFile = null;
		
	private static  HashSet<String> putCachedURLS =  new HashSet<String>();
	private static  Stack<URLInfo>  getCachedURLS = new Stack<URLInfo>();
	
 	private static  Boolean lockPutputCachedURLS = true;
	private static  Boolean lockGetputCachedURLS = true;
	
	private static List<String>  seedList = null;
	
	public static final int MAX_URL_LENGTH= 50; 
	
	public URLStoreMysql() 
	{
		mysqldb = new MysqlDB("urldb");	
		readSetting();
		if(seedList == null)
			seedList = this.readSeeds(seedsFile);
	}
	
	public URLStoreMysql(String dbName,String tableName) 
	{
		mysqldb = new MysqlDB(dbName);
		this.tableName = tableName;
		readSetting();
		
		if(seedList == null)
			seedList = this.readSeeds(seedsFile);
	}

	public  boolean  put(String url,int encode)
	{
		
		if(url == null || url.length() > MAX_URL_LENGTH)
			return false;
		 
		//url.replaceAll("%[//d]", replacement)
		synchronized (lockPutputCachedURLS)
		{
			if (putCachedURLS.contains(url) || StringUtils.isNullOrEmpty(url))
				return false;
			
			putCachedURLS.add(url);
			
			if (putCachedURLS.size() > MaxputCachedURLS || new Random().nextInt(3) == 0)
			{
				Iterator iter = putCachedURLS.iterator();
				while( iter.hasNext())
				{
					String str = (String) iter.next();
					URLInfo urlinfo = new URLInfo(str,0,new Date().toLocaleString(),new Date().toLocaleString(), str);
					boolean ret = this.mysqldb.insertURL(urlinfo, tableName);
				}
				
			}
			return true;
		}
		
	}

	public String get()
	{
		if(seedList == null || seedList.size() <=0)
			return null;
		int randomInt = Math.abs(new Random().nextInt());
		int index =  randomInt % seedList.size();
		String hostName = seedList.get(index);
		
		String condition = condition4get.replace("host", hostName);
		
		synchronized(lockGetputCachedURLS)
		{
			if(getCachedURLS.size() <= 0)
			{// TODO Auto-generated method stub		
				List<URLInfo> urlinfos = this.mysqldb.getURL(condition, tableName);
				if (urlinfos == null || urlinfos.size() == 0)
					return null;
				
				for(URLInfo urlinfo: urlinfos)
				{
					this.updateSucceed(urlinfo.getUrl());	
					getCachedURLS.add(urlinfo);
				}
			}
			URLInfo u = getCachedURLS.pop();
			this.urls.put(u.getUrl(), u);
			this.updateSucceed(u.getUrl());
			return u.getUrl();	
		}
	}
	
	public void updateSucceed(String url)
	{
		// TODO Auto-generated method stub
		if(url != null && urls.containsKey(url))
		{
			System.out.println("update succeed :" + url);	
			URLInfo urlinfo = (URLInfo) urls.get(url);	
			urlinfo.setLastCrawlTime(new Date().toLocaleString());
			urlinfo.setStatus(1);
			this.urls.remove(urlinfo.getUrl());
			this.mysqldb.updateURLInfo(urlinfo, tableName);
		}

	}

	public void updateFailed(String url)
	{
		if(url != null && urls.containsKey(url))
		{
			System.out.println("update Failed :" + url);
			URLInfo urlinfo = (URLInfo) urls.get(url);			
			urlinfo.setLastCrawlTime(new Date().toLocaleString());
			urlinfo.setStatus(-1);
			this.urls.remove(urlinfo.getUrl());
			this.mysqldb.updateURLInfo(urlinfo, tableName);
		}
	
	}

	/**
	 * read setting of urlstoreMysql
	 */
	public  void readSetting()
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
			 this.condition4get = p.getProperty("condition4get");
			 this.seedsFile = p.getProperty("seeds");
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
	
	List<String>  readSeeds(String seedfile)
	{
		BufferedReader fi = null;		
		try
		{
			File f = new File(seedfile);
			if (!f.exists() || !f.canRead() || f.isHidden())
				return null;
			fi = new BufferedReader(new FileReader(f));
			
			List<String> seeds = new ArrayList<String>();
			String line = null;
			
			while((line = fi.readLine()) != null)
			{
				if (!StringUtils.isNullOrEmpty(line))
				{	
					line = line.trim();
					seeds.add(line);	
				}
			}
			return seeds;
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
		return null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
//		
//		URLStoreMysql uslstore = new URLStoreMysql();
////	
//	/*	for(int i = 0; i < seedList.size(); i++)
//			uslstore.put(seedList.get(i));*/
////		String url = uslstore.get();
////		System.out.println(url);
//		//uslstore.updateFailed("http://www.myexception.cn");
//		//uslstore.updateSucceed("http://www.myexception.cn");
//		
//		
//		String url = "http://www.iteye.com/blogs/tag/C++%20%20%20%20%E6%8E%92%E5%BA%8F%20%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%20%E7%AE%97%E6%B3%95%20%20%E7%90%86%E8%AE%BA";
//		uslstore.put(url);
//		/*where url like '%compressed%'
//		try {
//			System.out.println(URLDecoder.decode(url,"utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
//		
//		List<URLInfo> l = uslstore.mysqldb.getURL("where url like '%compressed'", "urlstore");
//		String url1 = l.get(0).getUrl();
//		System.out.println(url1);
//		url1 = url1.replaceAll("compressed", "");
//		String deurl = ZipUtil.decompress(url1.getBytes());
//		System.out.println(deurl.equals(url));
	}
}




