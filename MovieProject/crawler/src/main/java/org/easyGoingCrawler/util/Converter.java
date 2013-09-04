package org.easyGoingCrawler.util;

import java.security.MessageDigest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
public class Converter
{
	public static Url CrawlURI2Url (CrawlURI uri)
	{
		if( uri == null ) return null;
		
		Url url = new Url();
		url.setHost(uri.getHost());
		url.setUrl(uri.getUrl());
		url.setLastCrawled(uri.getLastCrawlDate());
		url.setId(Converter.urlEncode(uri.getUrl()));
		url.setLastCrawled(new Date());
		return url;
	}
	
	public static String urlEncode(String url)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte [] b = md5.digest(url.getBytes());
			StringBuffer buf = new StringBuffer(""); 
			int i;
			for (int offset = 0; offset < b.length; offset++) 
			{ 
				i = (int)b[offset]; 
				if(i<0) i+= 256; 
				if(i<16) 
				buf.append("0"); 
				buf.append(Integer.toHexString(i)); 
			}
			return  buf.toString();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
	
	public static int praseIntFromStr(String str)
	{
		try
		{
			String num = null;
			Pattern p = Pattern.compile("[0-9]+");
			Matcher m = p.matcher(str);
			
			if(m.find())
			{
				num = m.group();
			}
			
			if ( num == null)
				return -1;
			
			return Integer.parseInt(num);
		} 
		catch (Exception  e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} 
	}
	
	public static void main(String[] args)
	{
//		String url1 = "http://www.cnblogs.com/mycodelife/archive/2009/04/22/1441624.html";
//		String url2 = "http://www.cnblogs.com/mycodelife/archive/2009/04/22/1441 624.html";
//		System.out.println(urlEncode(url1));
//		System.out.println(urlEncode(url2));
		
		System.out.println(praseIntFromStr("123asd"));
		
	}
}
