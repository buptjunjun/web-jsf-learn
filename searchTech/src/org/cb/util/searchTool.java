package org.cb.util;

import java.util.List;

import org.cb.data.Blog;
import org.cb.lucene.BaseSearcher;
import org.cb.lucene.BlogSearcher;

public class searchTool
{
	static private BaseSearcher bsearch = new BaseSearcher("D:/data/lucenedata/Lucene");
	static private BlogSearcher blogsearch = new BlogSearcher(bsearch);
	
	public static List<Blog>  search(String queryStr )
	{
		List<Blog> lblucene = blogsearch.searchBlog(queryStr);
		return lblucene;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
