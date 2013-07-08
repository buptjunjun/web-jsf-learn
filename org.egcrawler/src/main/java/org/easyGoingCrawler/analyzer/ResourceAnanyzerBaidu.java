package org.easyGoingCrawler.analyzer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easyGoingCrawler.bean.BResource;
import org.easyGoingCrawler.docWriter.Bloger;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.Fetcher;
import org.easyGoingCrawler.util.Converter;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ResourceAnanyzerBaidu implements Analyzer<List<BResource>>
{
    private int limit = 2;
    private String type="baidu";
    private String unknow = "unknow";
    
	public List<BResource> analyze(String host, String encode, String content)
	{
		// TODO Auto-generated method stub
		List<BResource> resources = new ArrayList<BResource>();
		try
		{
			Document doc = Jsoup.parse(content);
			
			Element bigDiv = doc.getElementById("search_msg");
			if(bigDiv == null)
				return null;
			
			Elements divs = bigDiv.getElementsByClass("singleMessage");
			if(divs == null || divs.isEmpty()) 
				return null;
			
			Element e = divs.first();
			int count = 0;
			while(e !=  null)
			{			
				Elements playAddresses = e.getElementsByClass("singPlayBtn");
				if(playAddresses != null && !playAddresses.isEmpty())
				{
					Element rs = playAddresses.first();
					while(rs!=null)
					{
						Elements as = rs.getElementsByTag("a");
						
						if(as!=null)
						{
							Element a = as.first();
							while(a!=null)
							{
								BResource resource = new BResource();
								String des = a.text();
								String url = a.attr("ahref");
								resource.setResourceURL(url);
								resource.setDescription(des);
								
								if(StringUtil.isBlank(url))
									break;
								resources.add(resource);
								
								a =a.nextElementSibling();
								
							}
						}
						
						rs = rs.nextElementSibling();
					}
				}
				else
				{
					Elements titles= e.getElementsByClass("singleReportTitle");
					Element title = titles.first();
					String des = title.text();
					String url = title.attr("ahref");
					
					BResource resource = new BResource();
					resource.setResourceURL(url);
					resource.setDescription(des);	
					
					resources.add(resource);
					
				}
				e = e.nextElementSibling();
				if(count++ > limit)
					break;
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
	
	static public void main(String [] args)
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		Fetcher fetcher = appcontext.getBean("fetcherJsoup",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://baoku.baidu.com/new/search.php?word=%E6%B3%B0%E5%9D%A6%E5%B0%BC%E5%85%8B+1997&c=");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("www.baidu.com");
		fetcher.fetch(curl);
		String content = null;
	
		
		List<BResource> list = new ResourceAnanyzerBaidu().analyze(curl);
		System.out.println(list);
		
	}

	

}
