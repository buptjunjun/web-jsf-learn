package com.malong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class HomeController extends AbstractController
{
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		// TODO Auto-generated method stub
		ModelAndView mav= new ModelAndView("home");
		Test test = new Test();
		mav.addObject("test", test);
		return mav;
	}
}
