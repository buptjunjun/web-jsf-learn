package org.cb.lucene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
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
	private List<String> searchableFields = new ArrayList<String>();
	private String queryStr = null;
	
	public BlogSearcher()
	{
		this("E:/Lucene");		
	}
	
	public BlogSearcher( BaseSearcher searcher)
	{
		this.searcher = searcher;
		Similarity s = new DefaultSimilarity();
		String [] fields = {"title","content"};
		searchableFields.addAll(Arrays.asList(fields));
	}
	
	public BlogSearcher( String indexPath)
	{
		this.searcher = new BaseSearcher(indexPath);
		Similarity s = new DefaultSimilarity();
		String [] fields = {"title","content"};
		searchableFields.addAll(Arrays.asList(fields));
	}
	
	
	
	/**
	 * search blog form lucene.
	 * @param queryStr
	 * @return
	 */
	public List<Blog> searchBlog(String queryStr)
	{
		List<Document> ldoc = this.searcher.SearchField(queryStr, this.searchableFields);
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
		if(ret != null)
		for(Blog blog:ret)
		{
			System.out.println(Thread.currentThread().getName()+"   blog:"+blog+"\ntitle:"+blog.getTitle()+"\n tags:"+blog.getTags()+"\n" +blog.getTags()+"content:\n"+"\n-----------------++++---------------------------------\n");
		}
		else
			System.out.println(Thread.currentThread().getName()+"  ret = null");
	}
	
	public String getQueryStr()
	{
		return queryStr;
	}

	public void setQueryStr(String queryStr)
	{
		this.queryStr = queryStr;
	}
	public List<String> getSearchableFields()
	{
		return searchableFields;
	}

	public void setSearchableFields(List<String> searchableFields)
	{
		this.searchableFields = searchableFields;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BaseSearcher bsearch = new BaseSearcher("E:/Lucene");
		BlogSearcher blogsearch = new BlogSearcher(bsearch);
		List<Blog> lb = blogsearch.searchBlog("java java");
		for(Blog blog:lb)
		{
			System.out.println("blog:"+blog+"\ntitle:"+blog.getTitle()+"\n tags:"+blog.getTags()+"\n" +blog.getTags()+"content:\n"+"\n-----------------++++---------------------------------\n");
		}	
	}
}
