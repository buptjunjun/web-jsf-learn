package com.coderlong.search.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.WebContentGenerator;

import com.coderlong.search.springmvc.beans.HotBlogs;
import com.coderlong.search.springmvc.beans.HotItems;

@Controller
public class HomeController
{

	private HotItems hots = null;
	
	public HomeController()
	{
		
		
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName(this.getViewPath());		
		mav.addObject("hosts", hots.getHotItems());
		mav.addObject("hotblogs", hotblogs.gethotblogs());
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

	public HotItems getHots()
	{
		return hots;
	}

	public void setHots(HotItems hots)
	{
		this.hots = hots;
	}

	private HotBlogs hotblogs = null;
	public HotBlogs getHotblogs()
	{
		return hotblogs;
	}
	public void setHotblogs(HotBlogs hotblogs)
	{
		this.hotblogs = hotblogs;
	}
	
}
