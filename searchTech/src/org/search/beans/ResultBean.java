package org.search.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.cb.data.Blog;
import org.cb.data.DAOMongo;
import org.cb.lucene.BaseSearcher;
import org.cb.lucene.BlogSearcher;
import org.cb.util.searchTool;
import org.search.beans.ResultItemBean;

public class ResultBean
{
	private List<ResultItemBean> results = new ArrayList<ResultItemBean>(0);
	
	
	public ResultBean(String queryStr)
	{
		List<Blog> lblucene = searchTool.search(queryStr);
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
			subContent  = toHtml(subContent);
			String title = toHtml(blog.getTitle());
			this.results.add(new ResultItemBean(title,subContent,blog.getUrl(),blog.getTags(),blog.getPostDate()));
			//System.out.println("blog:"+blog+"\ntitle:"+blog.getTitle()+"\n tags:"+blog.getTags()+"\n" +blog.getTags()+"content:\n"+"\n-----------------++++---------------------------------\n");
		}	
	}
	public List<ResultItemBean> getResults()
	{
		return results;
	}
	private String toHtml(String str)
	{
		if (str == null)
			return null;
		StringBuffer sb = new StringBuffer();
		int len = str.length();
		for (int i = 0; i < len; i++)
		{
			char c = str.charAt(i);
			switch (c)
			{
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
		
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public void setResults(List<ResultItemBean> results)
	{
		this.results = results;
	}
}
