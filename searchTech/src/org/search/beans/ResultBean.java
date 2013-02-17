package org.search.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.cb.data.Blog;
import org.cb.data.DAOMongo;
import org.cb.lucene.BaseSearcher;
import org.cb.lucene.BlogSearcher;
import org.search.beans.ResultItemBean;

public class ResultBean
{
	private List<ResultItemBean> results = new ArrayList<ResultItemBean>(0);
	static private BaseSearcher bsearch = new BaseSearcher("E:/Lucene");
	static private BlogSearcher blogsearch = new BlogSearcher(bsearch);
	
	public ResultBean(String queryStr)
	{
		List<Blog> lblucene = blogsearch.searchBlog(queryStr);
		List<String> ids = new ArrayList<String>();
		if(lblucene != null )
		{
			for(int i = 0; i < lblucene.size() && i <30 ;i++)
				ids.add(lblucene.get(i).getId());
		}
		
		
		List<Blog> lb = DAOMongo.getInstance().searchBlog("_id",ids);
		if(lb != null)
		for(Blog blog:lb)
		{
			String subContent = blog.getContent().length()<=200? blog.getContent() : blog.getContent().substring(0, 200);
			this.results.add(new ResultItemBean(blog.getTitle(),subContent,blog.getUrl(),blog.getTags(),blog.getPostDate()));
			//System.out.println("blog:"+blog+"\ntitle:"+blog.getTitle()+"\n tags:"+blog.getTags()+"\n" +blog.getTags()+"content:\n"+"\n-----------------++++---------------------------------\n");
		}	
	}
	public List<ResultItemBean> getResults()
	{
		return results;
	}

	public void setResults(List<ResultItemBean> results)
	{
		this.results = results;
	}
}
