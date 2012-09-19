package org.easyGoingCrawler.urlStore;

import java.util.Date;

import org.easyGoingCrawler.framwork.URLStore;


/**
 * URLStoreBackupMysql will store the URL in MYSQL. it will use the urls that we are not interesting but do not want to discard 
 * 
 * @author Andy  weibobee@gmail.com 2012-9-17
 *
 */
public class URLStoreBackupMysql extends URLStoreMysql
{
	public URLStoreBackupMysql() 
	{
		super("urldb","urlstorebackup");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		URLStore uslstore = new URLStoreBackupMysql();
		//String url = uslstore.get();
		uslstore.put("http://www.myexception.cn/abcdefg");
	}


}
