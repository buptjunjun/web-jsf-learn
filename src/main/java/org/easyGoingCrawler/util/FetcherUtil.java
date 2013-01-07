package org.easyGoingCrawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetcherUtil
{
	static public String getEncode(String xml)
	{
		if(xml == null) return null;
		
		String charSet = null;
		
		try
		{		
			String regEx="(?=<meta).*?(?<=charset=[//'|//\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
			Pattern p=Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher m=p.matcher(xml);
		    boolean result=m.find();
		    if (result == true && m.groupCount() == 1) 
		       charSet = m.group(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	    
	    return charSet;
	}
}