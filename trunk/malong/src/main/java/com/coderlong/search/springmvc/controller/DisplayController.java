package com.coderlong.search.springmvc.controller;

import java.util.ArrayList;
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

@Controller
public class DisplayController
{

	
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
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("blog", blog);
		mav.addObject("url", blog.getUrl());
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


	
	
}
