package com.coderlong.search.springmvc.controller;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.coderlong.search.springmvc.beans.SearchCriteria;
import com.coderlong.search.springmvc.beans.SearchResult;
import com.coderlong.search.springmvc.service.SearchService;

public class SearchController extends SimpleFormController
{
	private SearchService searchService=null;

	public SearchController()
	{
		this.setFormView(this.getViewPath());
		this.setSuccessView(this.getViewPath());
		this.setCommandName("searchCriteria");
		this.setCommandClass(SearchCriteria.class);
	}
	
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception
	{
		SearchCriteria criteria =( SearchCriteria)command;
		List<SearchResult> results = searchService.search(criteria);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(this.getViewPath());
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
