package org.cb.lucene;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.document.Document;
import org.cb.common.Task;
import org.cb.data.Blog;
import org.cb.util.converter;

public class BlogSearcher extends Task
{

	static private Logger logger = Logger.getLogger(BlogSearcher.class.getName());
	private Searcher searcher = null;
	private String searchableField = "contentField";
	private String queryStr = null;
	

	public BlogSearcher( BaseSearcher searcher)
	{
		this.searcher = searcher;
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public String getQueryStr()
	{
		return queryStr;
	}

	public void setQueryStr(String queryStr)
	{
		this.queryStr = queryStr;
	}

}
