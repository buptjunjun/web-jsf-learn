package com.coderlong.search.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.WebContentGenerator;

public class DisplayController extends AbstractController
{

	
	public DisplayController()
	{
		// set the support method
		this.setSupportedMethods(new String [] {WebContentGenerator.METHOD_GET});
		
	}
	
	@Override
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
