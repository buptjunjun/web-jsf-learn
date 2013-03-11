package com.coderlong.search.springmvc.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


import com.coderlong.search.springmvc.beans.SearchCriteria;
import com.coderlong.search.springmvc.beans.SearchResult;
import com.coderlong.search.springmvc.service.SearchService;

import com.mysql.jdbc.StringUtils;

public class SearchController extends AbstractController
{
	private SearchService searchService=null;
	public static final int sessionMaxInactiveInterval = 5*16;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception
	{
		arg1.setCharacterEncoding("GBK");
		arg0.setCharacterEncoding("GBK");
		String query = arg0.getParameter("query");
		
		//½âÂë
		if(query != null)
		{
			query = new String(query.getBytes("iso-8859-1"),"GBK");
			query = URLDecoder.decode(query, "GBK");
		}
		
		String page = arg0.getParameter("page");
		
		int pageNUM= 0;
		if(page==null)
			pageNUM = 0;
		else
		{
			try{
				pageNUM = Integer.parseInt(page);
			}
			catch(Exception e) {e.printStackTrace(); pageNUM = 0;}
			
		}
		
		List<SearchResult> results = null;
		SearchCriteria criteria = new SearchCriteria(query);
		HttpSession session = arg0.getSession(true);
		Object criteriaSession = session.getAttribute("criteria");
		if(criteriaSession == null || !criteria.equals(criteriaSession) )
		{
			 results = searchService.search(criteria);
			 session.putValue("results", results);
		}
		else
		{
			 Object resultSession=  session.getAttribute("results");
			 if(resultSession == null)
				 results = searchService.search(criteria);
			 else 
				 results =  (List<SearchResult>)resultSession;
		}

		int begin = 10*pageNUM;
		if(begin > results.size() -10)
			begin = results.size()-10;
		else if(begin < 0) begin = 0;
		
		List<SearchResult> tmp = results.subList(begin, begin+10);
		
		session.putValue("criteria",criteria);
				
		ModelAndView mav = new ModelAndView();
		mav.setViewName(this.viewPath);
		mav.addObject("criteria",criteria);
		mav.addObject("page",pageNUM);
		mav.addObject("results",tmp);
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
