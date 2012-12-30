package org.easyGoingCrawler.util;

import java.util.Map;
import java.util.regex.Pattern;

public class URLAnalyzer
{
	public static final int SAVE = 0;
	public static final int HOLD = 1;
	public static final int DELETE = 2;
	
	private Map<String,String> saveurls = null;
	private Map<String,String> holdurls = null;
	
	public URLAnalyzer(Map<String ,String> saveurls,Map<String ,String> holdurls )
	{
		this.holdurls = holdurls;
		this.saveurls = saveurls;
	}
	
	public int analyze(String host,String url)
	{
		if (host == null || url == null)
			return DELETE;
		
		if (url.contains("#"))
			return DELETE;
		
		String pattern4save = this.saveurls.get(host);
		String parttern4hold = this.holdurls.get(host);
		
		if(Pattern.matches(pattern4save, url))
			return SAVE;
		
		if(Pattern.matches(parttern4hold, url))
			return HOLD;
		
		return DELETE;
		
	}
	
	
	public static void main(String [] args)
	{
		//String url = "http://blog.csdn.net/ro_wsy999/article/details/8393544";
		String url = "http://blog.chinaunix.net/uid/26896647.html";
		//String url = "http://blog.chinaunix.net/uid-2689664a7-id-3454480.html";
		String pattern4save = "http://blog.chinaunix.net/uid-\\d+-id-\\d+\\.html";
		String pattern4hold = "http://blog.chinaunix.net/uid/\\d+\\.html";
		System.out.println(Pattern.matches(pattern4save, url));
		System.out.println(Pattern.matches(pattern4hold, url));
			
	}
}
