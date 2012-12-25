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
		String pattern4save = this.saveurls.get(host);
		String parttern4hold = this.holdurls.get(host);
		
		if(Pattern.matches(pattern4save, url))
			return SAVE;
		
		if(Pattern.matches(parttern4hold, url))
			return HOLD;
		
		return DELETE;
		
	}
}
