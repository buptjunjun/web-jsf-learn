package com.coderlong.search.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.WebContentGenerator;

@Controller
public class AboutController 
{

	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception
	{
		ModelAndView mav = new ModelAndView();
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
