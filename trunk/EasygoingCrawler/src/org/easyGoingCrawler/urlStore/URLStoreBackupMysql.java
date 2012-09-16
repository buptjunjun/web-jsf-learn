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
		super();
	}
	
	@Override
	public boolean put(String url) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		URLInfo urlinfo = new URLInfo(url,2,new Date().toGMTString(),new Date().toGMTString());
		this.mysqldb.insertURL(urlinfo, "urlstore");
		return false;
	}
	
	@Override
	public String get() {
		String condition = " where status=2 order by lastCrawlTime   asc limit 1";	
		URLInfo urlinfo = this.mysqldb.getURL(condition, "urlstore");
		if (urlinfo != null)
		{
			urlinfo.setLastCrawlTime(new Date().toLocaleString());
			urlinfo.setStatus(3);
			this.mysqldb.updateURLInfo(urlinfo, "urlstore");
			return urlinfo.getUrl();
		}
		
		return null;
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stu
	}


}
