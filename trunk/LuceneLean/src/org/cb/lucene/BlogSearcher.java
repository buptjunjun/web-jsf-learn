package org.cb.lucene;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.cb.common.Task;
import org.cb.data.Blog;
import org.cb.util.converter;

public class BlogSearcher extends Task
{

	static private Logger logger = Logger.getLogger(BlogSearcher.class.getName());
	private Searcher searcher = null;
	private String searchableField = "content";
	private String queryStr = null;
	

	public BlogSearcher( BaseSearcher searcher)
	{
		this.searcher = searcher;
		Similarity s = new DefaultSimilarity();
	}
	
	/**
	 * search blog form lucene.
	 * @param queryStr
	 * @return
	 */
	private List<Blog> searchBlog(String queryStr)
	{
		List<Document> ldoc = this.searcher.SearchField(queryStr, searchableField);
		List ret = new ArrayList<Blog>();
	
		for(Document doc: ldoc)
		{
//			System.out.println(doc.get(searchableField));
//			System.out.println(doc.toString());
			Blog blog = converter.doc2blog(doc);
			if(blog == null)
			{
				logger.info("BlogSearcher:converter.doc2blog(doc) error "+ doc);
				System.out.println("BlogSearcher:converter.doc2blog(doc) error "+ doc);
			}
			else
			{
				ret.add(blog);
			}
		}
		
		return ret;

	}
	
	@Override
	public void doTask()
	{
		// TODO Auto-generated method stub
		List<Blog> ret = this.searchBlog(queryStr);
	}
	
	public String getQueryStr()
	{
		return queryStr;
	}

	public void setQueryStr(String queryStr)
	{
		this.queryStr = queryStr;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BaseSearcher bsearch = new BaseSearcher("E:/Lucene");
		BlogSearcher blogsearch = new BlogSearcher(bsearch);
		List<Blog> lb = blogsearch.searchBlog("java jsf¿ª·¢");
		for(Blog blog:lb)
		{
			System.out.println(blog.getContent()+"\n-----------------++++---------------------------------\n");
		}
		
	}
	


}
