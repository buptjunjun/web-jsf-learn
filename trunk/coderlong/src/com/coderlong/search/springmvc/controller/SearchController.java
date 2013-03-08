package com.coderlong.search.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import coderlong.search.springmvc.beans.SearchCriteria;
import coderlong.search.springmvc.service.SearchService;

import com.mysql.jdbc.StringUtils;

public class SearchController extends AbstractController
{
	private SearchService searchService=null;
	
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception
	{
		String query = arg0.getParameter("query");
		if(StringUtils.isEmptyOrWhitespaceOnly(query))
		{
			query = null;
		}
		
		SearchCriteria criteria = new SearchCriteria(query);
		List<SearchResult> results = searchService.search(criteria);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(this.viewPath);
		mav.addObject("criteria",criteria);
		mav.addObject("results",results);
		return mav;
	}
	
	public void setSearchService(SearchService searchService)
	{
		this.searchService = searchService;
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
