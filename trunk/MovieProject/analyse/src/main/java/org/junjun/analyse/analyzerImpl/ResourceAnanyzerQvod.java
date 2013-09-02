package org.junjun.analyse.analyzerImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junjun.analyse.analyzer.Analyzer;
import org.junjun.analyse.analyzer.bean.BResource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ResourceAnanyzerQvod implements Analyzer<List<BResource>>
{
    private int limit = 3;
    private String type="qvod";
    private String unknow = "unknow";
    static private String regex = ".*([qQ][vV][oO][dD]|¿ì²¥|&#x5FEB;&#x64AD;|\u5FEB\u64AD).*";
    
	public List<BResource> analyze(String host, String encode, String content)
	{
		// TODO Auto-generated method stub
		List<BResource> resources = new ArrayList<BResource>();
		try
		{
			JSONObject  root = JSONObject.fromObject(content);
			JSONArray results = root.getJSONArray("results");
			if(results == null || results.isEmpty())
				return resources;
			
			Iterator itr = results.iterator();
			while(itr.hasNext())
			{
				JSONObject result = (JSONObject) itr.next();
				String description = result.getString("contentNoFormatting");
				String url =result.getString("unescapedUrl"); 
				String title = result.getString("titleNoFormatting");
				description=title+" || "+description;
				
				if(isQvod(description))
				{
					BResource resource = new BResource();
					resource.setResourceURL(url.trim());
					resource.setMovieDescription(title+description);
					resources.add(resource);
				}
			}
						
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resources;
		}
		
		return resources;
	}

	public List<BResource> analyze(CrawlURI curl)
	{
		List<BResource> ret= analyze(curl.getHost(),curl.getEncode(),curl.getContent());	
		if(ret!=null)
		{
			for(BResource r:ret)
			{
				r.setId(Converter.urlEncode(r.getResourceURL()));
				r.setResourceType(type);
				r.setMovieId(((String)curl.getReserve()));
				r.setCrawledDate(new Date());
				
				URL url;
				try
				{
					url = new URL(r.getResourceURL());
					r.setHost(url.getHost());
				} catch (MalformedURLException e)
				{
					// TODO Auto-generated catch block
					r.setHost(unknow);
					e.printStackTrace();
				}		
			}
		}
		
		return ret;
	}
	
	private boolean isQvod(String content)
	{
		if(StringUtils.isBlank(content))
			return false;
		
		if(Pattern.matches(regex, content))
			return true;
		return false;
	}
	static public void main(String [] args)
	{

		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("https://www.googleapis.com/customsearch/v1element?key=AIzaSyCVAXiUzRYsML1Pv6RwSG1gunmMikTzQqY&rsz=filtered_cse&num=10&hl=zh_CN&prettyPrint=false&source=gcsc&gss=.com&sig=351077565dad05b6847b1f7d41e36949&cx=014545285319128157587:opxtjupf3yk&q=%E6%B3%B0%E5%9D%A6%E5%B0%BC%E5%85%8B&sort=&googlehost=www.google.com&oq=%E6%B3%B0%E5%9D%A6%E5%B0%BC%E5%85%8B");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("qvod");
		fetcher.fetch(curl);
		String content = curl.getContent();
	
		System.out.println(content);
		List<BResource> list = new ResourceAnanyzerQvod().analyze(curl);
		System.out.println(list);
		
		String test="asdfasd¿ì²¥asdfasd";
		System.out.println(Pattern.matches(regex, test));
		
	}

	public List<BResource> analyze(Html html)
	{
		// TODO Auto-generated method stub
		return null;
	}

	

}
