package org.search.beans;

import java.util.List;

import org.cb.data.Blog;
import org.cb.lucene.*;

public class test
{
	public static void main(String [] args)
	{
		BaseSearcher bsearch = new BaseSearcher("E:/Lucene");
		BlogSearcher blogsearch = new BlogSearcher(bsearch);
		List<Blog> lb = blogsearch.searchBlog("aaa");
		
		for(Blog blog:lb)
		{
			//this.results.add(new ResultItemBean(blog.getTitle(),blog.getContent().substring(0,100),blog.getUrl(),blog.getTags(),blog.getPostDate()));
			System.out.println("blog:"+blog+"\ntitle:"+blog.getTitle()+"\n tags:"+blog.getTags()+"\n" +blog.getTags()+"content:\n"+"\n-----------------++++---------------------------------\n");
		}	
	}
}
