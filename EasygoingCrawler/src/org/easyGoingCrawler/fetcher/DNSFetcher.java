package org.easyGoingCrawler.fetcher;

import org.easyGoingCrawler.framwork.Fetcher;

/**
 *  DNSFetcher wil fetch the IP address of a website. If we will crawl the same website , we'll  use the IP address.
 *  This will save us the time to access DNS server. 
 *  For example we query "ditu.baidu.com" form DNS server. will return "http://123.125.114.86/". If we want to browse
 *  "http://ditu.baidu.com/abc",we will use "http://123.125.114.86/abc" instead. 
 *  
 * @author Andy  weibobee@gmail.com 2012-9-12
 *
 */
public class DNSFetcher implements Fetcher
{

	/**
	 * fetch the ip address of a website.
	 * For example we query "ditu.baidu.com" form DNS server. will return "http://123.125.114.86/" 
	 * 
	 * @param url the url of one html file
	 * @return the IP address of a Website 
	 */
	@Override
	public String fetch(String website) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	public static void main(String [] args)
	{
		new HttpFetcher().fetch("");
	}
}
