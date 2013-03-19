package com.coderlong.search.springmvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

@Controller
public class DisplayController
{
	private HotBlogs hotblogs = null;
	
	
	public DisplayController()
	{
	
		
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
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

		Blog blog = DAOMongo.getInstance().searchBlog(id);
		//if(isHotBlog(blog))
			hotblogs.addItem(blog);
			
		ModelAndView mav = new ModelAndView();
		mav.addObject("blog", blog);
		mav.addObject("url", blog.getUrl());
		mav.addObject("hotblogs", hotblogs.gethotblogs());
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
	
	public boolean isHotBlog(Blog blog)
	{
		if(blog == null)
			return false;
		
		String content = blog.getContent();
		if( (blog.getPictures() > 4 || (content!=null && content.length() > 200 && content.length() < 800) || blog.getComment() > 3 )&& new Date().getSeconds()%30 == 15)
			return true;
		return false;
	}
}
