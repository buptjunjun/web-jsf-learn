package org.easyGoingCrawler.DAO;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.easyGoingCrawler.docWriter.Html;
import org.easyGoingCrawler.docWriter.Url;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.util.RandomList;
import org.junjun.mongo.DAOMongo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class EGDAOMongoUpdater implements EGDAO
{
	public static final int HTMLERROR = 2;  
    
    private DAOMongo daomongo = null; 
	private List<String> hosts = null;
    
    
    public EGDAOMongoUpdater(DAOMongo daomongo, List<String> hosts )
	{
    	this.daomongo = daomongo;
    	this.hosts = hosts;

	}
   
	public List<CrawlURI> get()
	{
		return null;
	}
    
	
    public CrawlURI Html2CrawlURI(Html h)
    {
    	CrawlURI uri = new CrawlURI();
    	Date date = new Date();
		uri.setLastCrawlDate(date);
		uri.setCollectDate(date);
		uri.setUrl(h.getUrl());
		uri.setHost(h.getHost());
		uri.setStatus(CrawlURI.STATUS_OK);
		return uri;
    }
	
	
	

	public List<CrawlURI> get(String key)
	{
		// TODO Auto-generated method stub
		List<CrawlURI> list = new ArrayList<CrawlURI>();
		

		for(String h : hosts)
		{
			Map<String,Object> constrains = new HashMap<String,Object>();
			constrains.put("magicNum", HTMLERROR);
			constrains.put("host", h);
			List<Html> tmp  = daomongo.search(null, null, constrains, "crawledDate", DAOMongo.ASCENDING, 10, Html.class);
			if(tmp == null || tmp.size() == 0)
				continue;
			for(Html html:tmp)
				list.add(Html2CrawlURI(html));
		}
		RandomList.random(list);
		return list;

	}

	public boolean updateURL(Url url)
	{
		// TODO Auto-generated method stub
		return false;
	}



	public boolean insert(Object h)
	{
		if(!Html.class.isInstance(h))
			return false;
		Html html = (Html)h;
		Map<String,Object> constrains = new HashMap<String,Object>();
		constrains.put("id", html.getId());
		this.daomongo.update(html, constrains);		
		return false;
	}

	static public void main(String [] args) throws ParseException
	{
		ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
		EGDAOMongoUpdater Mongo= appcontext.getBean("EGDAOMongo", EGDAOMongoUpdater.class);
		/*Query q_chinaunix = new Query(where("host").is("blog.chinaunix.net")).limit(5);
		Query q_csdn = new Query(where("host").is("blog.csdn.net")).limit(5);
		Query q_sochina = new Query(where("host").is("my.oschina.net")).limit(5);
		Query q_51cto = new Query(where("host").is("blog.51cto.com")).limit(5);*/
		
		
//		//Blog blog = Mongo.mongoOps.findOne( q_chinaunix,Blog.class);
//		List<Blog> blog = Mongo.mongoOps.find( q_51cto,Blog.class);
//		for (Blog b:blog)
//		{
//			System.out.println(b);
//			try
//			{
//				Document doc = Jsoup.parse(b.getContent());
//				String content = doc.text();
//				System.out.println(content+"\n\n");
//			} catch (Exception e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//break;
//		}
//		java.text.SimpleDateFormat f = new  java.text.SimpleDateFormat("yyyy-MM-dd hh:mm");
//		Date start = f.parse("2013-2-1 00:00");
//		Date end = f.parse("2013-3-1 00:00");
//		long amount = Mongo.getCollectionCount(Blog.class);
//		long amount1 = Mongo.getCollectionCountByTime("crawledDate",start,end , Blog.class);
		//List<Url> ret = Mongo.getItemByRegex("url","http://home.cnblogs.com/u/[a-zA-z|0-9|_|-]+/",1000,Url.class);
	//	long count = Mongo.getCollectionCount(Blog.class);
		System.out.println();
	}
}
