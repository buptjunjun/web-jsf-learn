package org.easyGoingCrawler.urlStore;

import org.easyGoingCrawler.framwork.URLStore;


/**
 * URLStoreMysql will store the URL in MYSQL.
 * 
 * @author Andy  weibobee@gmail.com 2012-9-13
 *
 */
public class URLStoreMysql implements URLStore
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean put(String url)
	{
		// TODO Auto-generated method stub
		System.out.println("put " + url + " to mysql ");
		return false;
	}

	@Override
	public String get(String condition)
	{
		// TODO Auto-generated method stub
		System.out.println("get " + "www.baidu.com" + " from ");
		return null;
	}

}
