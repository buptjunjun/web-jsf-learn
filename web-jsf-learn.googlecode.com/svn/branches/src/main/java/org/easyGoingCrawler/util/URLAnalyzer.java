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
		//String url = "http://my.oschina.net/u/616092/blog/99399";
		//String url = "http://my.oschina.net/kzhou/blog/99360";
		//String url = "http://my.oschina.net/u/126717";
		//String url = "http://my.oschina.net/dourgulf";
		String url = "http://home.cnblogs.com/u/JimmyZhang/followers/2/";

		String pattern4save = "(http://home.cnblogs.com/u/[a-zA-z|0-9|_|-]+[/]?.*)|(http://www.cnblogs.com/[a-zA-z|0-9|_|-]+[/]?)|(http://www.cnblogs.com/p[0-9]+)|(http://www.cnblogs.com/[a-zA-z|0-9|_|-]+/default.html\\?page=[0-9]+)";
		String pattern4hold = "(http://[a-zA-z|0-9|-|_]+\\.iteye.com[/]?)|(http://[a-zA-z|0-9|-|_]+\\.iteye.com/\\?page=\\d+)|(http://www.iteye.com/blogs/.*)";
		System.out.println(Pattern.matches(pattern4save, url));
		System.out.println(Pattern.matches(pattern4hold, url));
			
	}
}
