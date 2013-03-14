package com.coderlong.search.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.WebContentGenerator;

import com.coderlong.search.springmvc.beans.HotItems;

public class HomeController extends AbstractController
{

	private HotItems hots = null;
	
	public HomeController()
	{
		// set the support method
		this.setSupportedMethods(new String [] {WebContentGenerator.METHOD_GET});
		
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName(this.getViewPath());		
		mav.addObject("hosts", hots.getHotItems());
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

	
	
}