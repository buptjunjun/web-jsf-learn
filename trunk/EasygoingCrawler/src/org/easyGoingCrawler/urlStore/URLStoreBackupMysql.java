package org.easyGoingCrawler.urlStore;

import java.util.Date;

import org.easyGoingCrawler.framwork.URLStore;


/**
 * URLStoreMysql will store the URL in MYSQL.
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
public class URLStoreBackupMysql extends URLStoreMysql
{
	public URLStoreBackupMysql() 
	{
		super("urldb","urlstoreBackup");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stu
	}


}
