package org.cb.util;

import java.util.List;

import org.cb.data.Blog;
import org.cb.lucene.BaseSearcher;
import org.cb.lucene.BlogSearcher;

public class searchTool
{
	static private BaseSearcher bsearch = null;
	static private BlogSearcher blogsearch =null;
	
	public static List<Blog>  search(String queryStr )
	{
		if(bsearch == null)
		{
			String dir = Localizer.getMessage("luceneDir");
			bsearch = new BaseSearcher(dir);
		}
		
		if(blogsearch == null && bsearch != null)
		{	
			blogsearch = new BlogSearcher(bsearch);
		}
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
