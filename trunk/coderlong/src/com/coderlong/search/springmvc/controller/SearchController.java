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
	private SearchCriteria criteria = null;
	public SearchCriteria getCriteria()
	{
		return criteria;
	}

	public void setCriteria(SearchCriteria criteria)
	{
		this.criteria = criteria;
	}

	public SearchController()
	{
		this.setFormView(this.getViewPath());
		this.setSuccessView(this.getViewPathSuccess());
		this.setCommandName("criteria");
		this.setCommandClass(SearchCriteria.class);
	}
	
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception
	{
		criteria =( SearchCriteria)command;
		List<SearchResult> results = searchService.search(criteria);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(this.getViewPathSuccess());
		mav.addObject("criteria",criteria);
		mav.addObject("results",results);
		return mav;
	}
	
	public void setSearchService(SearchService searchService)
	{
		this.searchService = searchService;
	}
	
	private String viewPath = null;
	private String viewPathSuccess = null;
	public String getViewPathSuccess()
	{
		return viewPathSuccess;
	}

	public void setViewPathSuccess(String viewPathSuccess)
	{
		this.viewPathSuccess = viewPathSuccess;
	}

	public String getViewPath()
	{
		return viewPath;
	}

	public void setViewPath(String viewPath)
	{
		this.viewPath = viewPath;
	}

}
