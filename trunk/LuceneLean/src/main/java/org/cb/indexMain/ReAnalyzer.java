package org.cb.indexMain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.cb.analyzer.*;
import org.cb.data.Blog;
import org.cb.data.DAOMongo;
import org.cb.data.Html;
import org.cb.lucene.BlogIndexer;
import org.cb.util.Localizer;

public class ReAnalyzer extends Thread
{
	String host;
	String mongohost;
	String mongodb;
	Analyzer a;
	
	public ReAnalyzer(String host,Analyzer a)
	{
		this.host=host;
		this.a=a;
	}
	
/*			host_csdn=blog.csdn.net
			host_cnblogs=www.cnblogs.com
			host_chinaunix=blog.chinaunix.net
			host_oschina=my.oschina.net
			host_51cto=blog.51cto.com
			host_ibmcn=ibm.cn
			host_iteye=www.iteye.com/blogs
*/
	static public void main(String [] args)
	{

	    Map<String,Analyzer> hosts = new HashMap<String,Analyzer>();
	    //hosts.put("blog.csdn.net", new CSDNAnalyzer());
	    hosts.put("blog.chinaunix.net", new ChinaunixAnalyzer());
	    //hosts.put("blog.51cto.com",new A51ctoAnalyzer());
	    //hosts.put("my.oschina.net",new OschinaAnalyzer());
	    //hosts.put("www.iteye.com/blogs",new IteyeAnalyzer());
	    hosts.put("ibm.cn",new IBMAnalyzer());
	    //hosts.put("www.cnblogs.com",new CnblogsAnalyzer());
	    
	    
		//String host = Localizer.getMessage("host");
	    for(Entry entry:hosts.entrySet())
	    {
	    	String host = (String) entry.getKey();
	    	Analyzer a = (Analyzer) entry.getValue();
	    	new ReAnalyzer(host,a).start();
	    }
		
	}
	
	@Override
	public void run()
	{
		dotask(this.host,this.a);
	}
	
	public void dotask(String host,Analyzer a)
	{
		String mongohost = Localizer.getMessage("mongohost");
		String mongodb = Localizer.getMessage("mongodb");
		String unindexedflag = Localizer.getMessage("unindexedflag");
		String inserunindexedflag = Localizer.getMessage("inserunindexedflag");

		int unindexedflag_int = Integer.parseInt(unindexedflag); 
		int inserunindexedflag_int = Integer.parseInt(inserunindexedflag); 
		
		DAOMongo rmongo = new DAOMongo(mongohost,27017,mongodb);
		DAOMongo mongo = new DAOMongo(mongodb);
		List<Html> lblog = mongo.searchHtml(host, inserunindexedflag_int, 100);
		int size = 1;
		long begin = System.currentTimeMillis();
		while(lblog!=null && lblog.size()!=0 )
		{			
			for(Html h : lblog)
			{				
				String content = a.analyse(h);
				h.setMagicNum(unindexedflag_int);
				try
				{
					rmongo.updateBlogHtml(h.getId(),content);
				
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mongo.updateHtml(h);
				System.out.println("update:"+h.getUrl());
			}
			System.out.println("indexed " + lblog.get(0)+"  and .....");
			size+=lblog.size();
			lblog = mongo.searchHtml(host, inserunindexedflag_int, 100);
			try
			{
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();	
		System.out.println("size:" + size+"   "+(end-begin)/1000);
	}
}
