package com.coderlong.search.springmvc.util;

import java.util.Date;

import org.cb.data.Blog;

public class BlogUtil
{

	static public void prework(Blog blog)
	{
		if(blog == null ) return;
		
		String content = blog.getContent();
		if(content != null)
			blog.setContent(content.replace("\n", "<br>").replace("\r", "<br>"));
		
		if("www.cnblogs.com".equals(blog.getHost()))
		{
			String title = blog.getTitle();
			if(title != null)	
				blog.setTitle(title.replace("²©¿ÍÔ°", "").replaceFirst("- .* -", ""));
		}
	}
	
	static public boolean isHotBlog(Blog blog)
	{
		if(blog == null)
			return false;
		
		String content = blog.getContent();
		if( (blog.getPictures() > 4 || (content!=null && content.length() > 600 ) || blog.getComment() > 3 ))
			return true;
		return false;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
