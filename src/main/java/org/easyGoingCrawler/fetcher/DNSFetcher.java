package org.easyGoingCrawler.fetcher;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
public class DNSFetcher extends Fetcher
{

	
	public static void main(String [] args)
	{

	}
}
