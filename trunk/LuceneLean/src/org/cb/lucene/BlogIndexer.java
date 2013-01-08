package org.cb.lucene;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.document.Document;
import org.cb.data.Blog;
import org.cb.data.DAOMongo;
import org.cb.util.converter;

public class BlogIndexer extends BaseIndexer
{
	private    int commitLimit = 0;
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
		
		Document doc = converter.Object2Doc(blog);	
		this.Index(doc);
		synchronized(this)
		{
			commitLimit+=1;
			// commit every 1000 docs
			if(this.commitLimit % 1000 == 0)
				this.commit();	
		}
	}

	static public void main(String [] args)
	{
		DAOMongo mongo = new DAOMongo("blogdb");
		BlogIndexer bi = new BlogIndexer("E:/Lucene");
		List<Blog> lblog = mongo.searchBlog("blog.csdn.net", -1, 100);
		while(lblog!=null && lblog.size()!=0)
		{
			for(Blog b : lblog)
			{
				bi.indexBlogs(b);
				b.setMagicNum(DAOMongo.INDEXED);
				mongo.updateBlog(b);
			}
			System.out.println("indexed " + lblog.get(0)+"  and .....");
			lblog = mongo.searchBlog("blog.csdn.net", -1, 2);
		}
			
	}
}
