package org.cb.lucene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexableField;
import org.cb.boost.BoostGeter;
import org.cb.boost.SimpleBoostGeter;
import org.cb.data.Blog;
import org.cb.data.Bloger;
import org.cb.data.DAOMongo;
import org.cb.util.converter;

public class BlogIndexer extends BaseIndexer
{
	private    int commitLimit = 0;	
	private  DAOMongo mongo = null;
	private BoostGeter boostGeter = new SimpleBoostGeter(); 
	public BlogIndexer(String indexPath)
	{
		super(indexPath);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * index a list of blog 
	 * @param lblog
	 */
	public void indexBlogs(List<Blog> lblog)
	{
		if(lblog == null) return ;
		
		List<Document> ldoc = new ArrayList<Document>();
		for(Blog blog:lblog)
		{
			if(blog == null) continue;
			Document doc = converter.Object2Doc(blog);
			if(doc != null)
				ldoc.add(doc);
		}
		
		this.Index(ldoc);
		synchronized(this)
		{
			commitLimit+=ldoc.size();	
			// commit every 1000 docs
			if(this.commitLimit % 1000 == 0)
				this.commit();	
		}
	}
	
	/**
	 * index a list of blog 
	 * @param lblog
	 */
	public void indexBlogs(Blog blog)
	{
		if(blog == null) return ;
		
		Document doc = Blog2Doc(blog);	
		this.Index(doc);
		synchronized(this)
		{
			commitLimit+=1;
			// commit every 1000 docs
			if(this.commitLimit % 1000 == 0)
				this.commit();	
		}
	}

	/**
	 *  blog to doc with boost value 
	 * @param blog
	 * @return
	 */
	public Document Blog2Doc(Blog blog)
	{
		Float blogBoost = this.boostGeter.getBoostFromBlog(blog);		
		System.out.println( "==bost :" + blog.getUrl()+" boost is " + blogBoost );
		;
		Float blogerBoost = 0.0f;	
		Bloger bloger = this.getBloger(blog.getBlogerURL());
		if(bloger != null)
		{
			blogerBoost = this.boostGeter.getBoostFromBloger(bloger);
			System.out.println( "==bost :" +bloger.getUrl()+" boost is " + blogerBoost );		
		}
		
		float boost =  blogBoost+blogerBoost;
		Map<String , Float> mboost = new HashMap<String,Float>();
		
//		mboost.put("title", 1.2f+boost);
//		mboost.put("tags", 0.9f+boost);
//		mboost.put("content", 1.0f+boost);
//		
		Document doc = converter.Object2Doc(blog, mboost);
		return doc;
	}
	
	/**
	 * get a bloger from db using bloger URl.
	 * @param blogerUrl
	 * @return
	 */
	public Bloger getBloger(String blogerUrl)
	{
		Bloger bloger = mongo.getBloger(converter.urlEncode(blogerUrl));
		return bloger;
	}
	

	
	static public void main(String [] args)
	{
		DAOMongo mongo = new DAOMongo("blogdb");
		DAOMongo rmongo = new DAOMongo("42.96.143.59",27017,"blogdb");
		BlogIndexer bi = new BlogIndexer("D:/work/lucene");
		bi.setMongo(mongo);
		List<Blog> lblog = mongo.searchBlog("www.cnblogs.com", -1, 100);
		int size = 1;
		long begin = System.currentTimeMillis();
		while(lblog!=null && lblog.size()!=0 )
		{
			for(Blog b : lblog)
			{
				
				b.setMagicNum(DAOMongo.INDEXED);
				
				
				try
				{
					rmongo.insert(b);
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
			lblog = mongo.searchBlog("www.cnblogs.com", -1, 100);
			gosleep(2);
		}
		long end = System.currentTimeMillis();
	
		System.out.println("size:" + size+"   "+(end-begin)/1000);
			
	}
	
	public int getCommitLimit()
	{
		return commitLimit;
	}

	public void setCommitLimit(int commitLimit)
	{
		this.commitLimit = commitLimit;
	}

	public DAOMongo getMongo()
	{
		return mongo;
	}

	public void setMongo(DAOMongo mongo)
	{
		this.mongo = mongo;
	}

	public static void gosleep(int i)
	{
		//if(new Random().nextInt(10) > 8)
		{
			try
			{
				System.out.println("sleep "+i+"second");
				TimeUnit.SECONDS.sleep(i);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
