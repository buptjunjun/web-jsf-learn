package org.cb.indexMain;

import java.util.List;

import org.cb.data.Blog;
import org.cb.data.DAOMongo;
import org.cb.lucene.BlogIndexer;
import org.cb.util.Localizer;

public class Main
{
	static public void main(String [] args)
	{

		String mongohost = Localizer.getMessage("mongohost");
		String luceneDir = Localizer.getMessage("luceneDir");
		String mongodb = Localizer.getMessage("mongodb");
		String host = Localizer.getMessage("host");
		String unindexedflag = Localizer.getMessage("unindexedflag");
		String indexedflag = Localizer.getMessage("indexedflag");

		int unindexedflag_int = Integer.parseInt(unindexedflag); 
		int indexedflag_int = Integer.parseInt(indexedflag); 
		
		DAOMongo mongo = new DAOMongo(mongohost,27017,mongodb);
		BlogIndexer bi = new BlogIndexer(luceneDir);
		bi.setMongo(mongo);
		List<Blog> lblog = mongo.searchBlog(host, unindexedflag_int, 100);
		int size = 1;
		long begin = System.currentTimeMillis();
		while(lblog!=null && lblog.size()!=0 )
		{
			for(Blog b : lblog)
			{				
				b.setMagicNum(indexedflag_int);		
				try
				{
					bi.indexBlogs(b);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mongo.updateBlog(b);
				System.out.println("insert:"+b.getUrl());
			}
			System.out.println("indexed " + lblog.get(0)+"  and .....");
			size+=lblog.size();
			lblog = mongo.searchBlog(host, unindexedflag_int, 100);		
		}
		long end = System.currentTimeMillis();	
		System.out.println("size:" + size+"   "+(end-begin)/1000);
			
	}
}
