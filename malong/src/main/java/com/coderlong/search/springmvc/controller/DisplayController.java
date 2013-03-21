package com.coderlong.search.springmvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cb.data.Blog;
import org.cb.data.DAOMongo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.WebContentGenerator;

import com.coderlong.search.springmvc.beans.HotBlogs;
import com.coderlong.search.springmvc.util.BlogUtil;

@Controller
public class DisplayController
{
	private HotBlogs hotblogs = null;
	//Ã¿Ò³500¸ö×Ö
	static private int contentLengthPerPage = 1000;
	
	public DisplayController()
	{
	
		
	}
	/*@RequestMapping(value = "/display", method = RequestMethod.GET)*/
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception
	{
		arg0.setCharacterEncoding("GBK");		
		String url = arg0.getParameter("url");
		url = new String(url.getBytes("iso-8859-1"),"GBK");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", url);
		mav.setViewName(this.getViewPath());		
		return mav;
		
	}
	
	@RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
	protected ModelAndView showpage(HttpServletRequest arg0,
			HttpServletResponse arg1 ,@PathVariable String id) throws Exception
	{
		arg0.setCharacterEncoding("GBK");		
		id = new String(id.getBytes("iso-8859-1"),"GBK");
		String page = arg0.getParameter("page");
		
		int totalPage = 0;
		int p = 0;
		try
		{
			if(page!=null)
				p=Integer.parseInt(page);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			p = 0;
		}
		
		Blog blog = DAOMongo.getInstance().searchBlog(id);
		if(blog==null)
		{
			throw new ServletException();
		}
		BlogUtil.prework(blog);
		
		String content = "";
		
		// add hot blogs
		if(BlogUtil.isHotBlog(blog))
			hotblogs.addItem(blog);
		
		if(blog.getPictures()  == 0)
		{
			content = blog.getContent();
			int length = content.length();
			totalPage = length / contentLengthPerPage;
			
			int begin = p;
			if(begin > totalPage) begin = totalPage;
			if(begin < 0) begin = 0;
			begin = begin*contentLengthPerPage;
			int end = begin+contentLengthPerPage >= length ?length:begin+contentLengthPerPage;
		
				content = content.substring(begin,end);
		}
		else
		{
			content = blog.getHtml();
			totalPage = -1;
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("blog", blog);
		mav.addObject("content", content);
		mav.addObject("url", blog.getUrl());
		mav.addObject("hotblogs", hotblogs.gethotblogs());
		mav.addObject("totalPage", totalPage);
		mav.addObject("page", p);
		mav.setViewName(this.getViewPath());		
		return mav;
		
	}
	
	
	@RequestMapping(value = "/page1/{id}", method = RequestMethod.GET)
	protected ModelAndView showpage1(HttpServletRequest arg0,
			HttpServletResponse arg1 ,@PathVariable String id) throws Exception
	{
		arg0.setCharacterEncoding("GBK");		
		id = new String(id.getBytes("iso-8859-1"),"GBK");
		String page = arg0.getParameter("page");
		
		int totalPage = 0;
		int p = 0;
		try
		{
			if(page!=null)
				p=Integer.parseInt(page);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			p = 0;
		}
		
		Blog blog = DAOMongo.getInstance().searchBlog(id);
		if(blog==null)
		{
			throw new ServletException();
		}
		BlogUtil.prework(blog);
		
		String content = "";
		//add hotblogs
		if(BlogUtil.isHotBlog(blog))
			hotblogs.addItem(blog);
			
		content = blog.getHtml();
		if(content == null )
			content = blog.getContent();
		totalPage = -1;
			
		ModelAndView mav = new ModelAndView();
		mav.addObject("blog", blog);
		mav.addObject("content", content);
		mav.addObject("url", blog.getUrl());
		mav.addObject("hotblogs", hotblogs.gethotblogs());
		mav.addObject("totalPage", totalPage);
		mav.addObject("page", p);
		mav.setViewName(this.getViewPath());		
		return mav;
		
	}
	
	private String viewPath = null;
	public String getViewPath()
	{
		return viewPath;
	}

	public void setViewPath(String viewPath)
	{
		this.viewPath = viewPath;
	}


	public HotBlogs getHotblogs()
	{
		return hotblogs;
	}
	public void setHotblogs(HotBlogs hotblogs)
	{
		this.hotblogs = hotblogs;
	}
	
	
	
	
}
