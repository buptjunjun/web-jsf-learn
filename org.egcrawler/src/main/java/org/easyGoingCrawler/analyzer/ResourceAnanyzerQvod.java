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

public class ResourceAnanyzerQvod implements Analyzer<List<BResource>>
{
    private int limit = 3;
    private String type="baidu";
    private String unknow = "unknow";
    
	public List<BResource> analyze(String host, String encode, String content)
	{
		// TODO Auto-generated method stub
		List<BResource> resources = new ArrayList<BResource>();
		try
		{
			Document doc = Jsoup.parse(content);
			
			Elements results = doc.getElementsByClass("results");
			Element bigDiv = null;
			if(results!=null && !results.isEmpty())
			   bigDiv= results.first();
			if(bigDiv == null)
				return null;
			
			Elements divs = bigDiv.getElementsByClass("rb");
			if(divs == null || divs.isEmpty()) 
				return null;
			
			Element e = divs.first();
			int count = 0;
			while(e !=  null)
			{			
				String description = null;
				//¼ò½é
				Elements ebrief  = e.getElementsByClass("pt");
				if(ebrief!=null && !ebrief.isEmpty())
				{
					Element brief = ebrief.first();
					description  = brief.text();
				}
				
				
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
								resource.setResourceDescription(des);
								resource.setMovieDescription(description);
								
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
					Elements titles= e.getElementsByAttributeValueContaining("class", "singleReportTitle");
					if(titles!=null && !titles.isEmpty())
					{
						Element title = titles.first();
						if(title!=null)
						{
							String des = title.text();
							String url = title.attr("ahref");
							
							BResource resource = new BResource();
							resource.setResourceURL(url);
							resource.setMovieDescription(description);
						
							resources.add(resource);
						}
					}
					
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
		Fetcher fetcher = appcontext.getBean("fetcher",Fetcher.class);
		
		CrawlURI curl = new CrawlURI();
		curl.setUrl("http://www.sogou.com/web?p=40040100&sut=7467&sourceid=kbplayer_all&interation=196626&interV=kKIOkrELjbkPmLkElbkTkKIMkbELjbgQmLkElbcTkKILmrELjbcQmLkEmrELjbgRmLkEkLYTkKIM%0Alo%3D%3D_-158122751&ie=utf8&sst0=1373537192102");
		curl.setStatus(CrawlURI.STATUS_OK);
		curl.setHost("qvod");
		fetcher.fetch(curl);
		String content = curl.getContent();
	
		System.out.println(content);
		//List<BResource> list = new ResourceAnanyzerQvodGoogle().analyze(curl);
		//System.out.println(list);
		
	}

	

}
