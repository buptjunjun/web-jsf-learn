package org.easyGoingCrawler.urlStore;

import java.util.Date;
import java.util.List;

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

		URLStoreBackupMysql uslstore = new URLStoreBackupMysql();
		List<URLInfo> l = uslstore.mysqldb.getURL("", "urlstorebackup");
		URLStoreMysql uslstore1 = new URLStoreMysql();
		
		for(int i = 0;i < l.size(); i++)
		{
			String url = l.get(i).getUrl();
			uslstore1.put(url);
		}
		
	}


}
