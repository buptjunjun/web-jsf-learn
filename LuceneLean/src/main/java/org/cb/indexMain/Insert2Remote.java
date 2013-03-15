package org.cb.indexMain;

import java.util.List;

import org.cb.data.Blog;
import org.cb.data.DAOMongo;
import org.cb.lucene.BlogIndexer;
import org.cb.util.Localizer;

public class Insert2Remote
{

	static public void main(String [] args)
	{

		String mongohost = Localizer.getMessage("mongohost");
		String mongodb = Localizer.getMessage("mongodb");
		String host = Localizer.getMessage("host");
		String unindexedflag = Localizer.getMessage("unindexedflag");
		String inserunindexedflag = Localizer.getMessage("inserunindexedflag");

		int unindexedflag_int = Integer.parseInt(unindexedflag); 
		int inserunindexedflag_int = Integer.parseInt(inserunindexedflag); 
		
		DAOMongo rmongo = new DAOMongo(mongohost,27017,mongodb);
		DAOMongo mongo = new DAOMongo(mongodb);
		List<Blog> lblog = mongo.searchBlog(host, inserunindexedflag_int, 100);
		int size = 1;
		long begin = System.currentTimeMillis();
		while(lblog!=null && lblog.size()!=0 )
		{
			for(Blog b : lblog)
			{				
				b.setMagicNum(unindexedflag_int);		
				try
				{
					rmongo.insert(b);
				
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
			lblog = mongo.searchBlog(host, inserunindexedflag_int, 100);
		}
		long end = System.currentTimeMillis();	
		System.out.println("size:" + size+"   "+(end-begin)/1000);
			
	}
}
