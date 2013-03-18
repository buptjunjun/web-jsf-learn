package com.coderlong.search.springmvc.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


import com.coderlong.search.springmvc.beans.SearchCriteria;
import com.coderlong.search.springmvc.beans.SearchResult;
import com.coderlong.search.springmvc.service.SearchService;

import com.mysql.jdbc.StringUtils;

@Controller
public class SearchController 
{
	private SearchService searchService=null;
	public static final int sessionMaxInactiveInterval = 5*16;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception
	{
		arg1.setCharacterEncoding("GBK");
		
		String query =  arg0.getParameter("query");
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
		int end = begin;
		if(begin > results.size() -10)
			begin = results.size()-10;
		 if(begin < 0) begin = 0;
		
		if(begin +10 > results.size()-1)
			end = results.size();
		else end=begin+10;
		List<SearchResult> tmp = results.subList(begin, end);
		
		// how many page to show
		int totalPage = results.size()/10+1;
		session.putValue("criteria",criteria);
				
		ModelAndView mav = new ModelAndView();
		mav.setViewName(this.viewPath);
		mav.addObject("criteria",criteria);
		mav.addObject("page",pageNUM);
		mav.addObject("itemamount",tmp.size());
		mav.addObject("totalpage",new Integer(totalPage));
		mav.addObject("results",tmp);
		return mav;
	}
	
	@RequestMapping(value = "/s/{query}", method = RequestMethod.GET)
	protected ModelAndView searchRest(HttpServletRequest arg0,
			HttpServletResponse arg1, @PathVariable String query) throws Exception
	{
		arg1.setCharacterEncoding("GBK");
		arg0.setCharacterEncoding("GBK");
		
		//½âÂë
		if(query != null)
		{
			query = new String(query.getBytes("iso-8859-1"),"utf-8");
			query = URLDecoder.decode(query, "utf-8");
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
		int end = begin;
		if(begin > results.size() -10)
			begin = results.size()-10;
		 if(begin < 0) begin = 0;
		
		if(begin +10 > results.size()-1)
			end = results.size();
		else end=begin+10;
		List<SearchResult> tmp = results.subList(begin, end);
		
		session.putValue("criteria",criteria);
				
		ModelAndView mav = new ModelAndView();
		mav.setViewName(this.viewPath);
		mav.addObject("criteria",criteria);
		mav.addObject("page",pageNUM);
		mav.addObject("itemamount",tmp.size());
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
