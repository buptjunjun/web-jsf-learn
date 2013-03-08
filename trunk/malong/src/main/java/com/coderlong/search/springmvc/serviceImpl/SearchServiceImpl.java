package com.coderlong.search.springmvc.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coderlong.search.springmvc.beans.SearchResult;

import com.coderlong.search.springmvc.beans.SearchCriteria;
import com.coderlong.search.springmvc.service.SearchService;
import com.mysql.jdbc.StringUtils;

public class SearchServiceImpl implements SearchService
{
	
	public SearchServiceImpl()
	{
		// TODO Auto-generated constructor stub
	}

	public List<SearchResult> search(SearchCriteria criteria)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
/*	@Override
	public List<SearchResult> search(SearchCriteria criteria)
	{
		// TODO Auto-generated method stub
		 List<SearchResult>  result = new ArrayList<SearchResult>();
		 if(StringUtils.isEmptyOrWhitespaceOnly(criteria.getQuery()))
				 return result;
		 ResultBean rb = new ResultBean(criteria.getQuery());
		 
		 //result.add(new SearchResult(criteria.getQuery(),criteria.getQuery(),criteria.getQuery(),new ArrayList(),new Date()));
		return ResultBean2SearchResults(rb);
	}
	
	public List<SearchResult> ResultBean2SearchResults(ResultBean rb)
	{
		List<SearchResult>  ret = new ArrayList<SearchResult>();
		List<ResultItemBean> results = rb.getResults();
		if(results == null) return ret;
		
		for(ResultItemBean r:results)
		{
			SearchResult sr = new SearchResult();
			sr.setTitle(r.getTitle());
			sr.setUrl(r.getUrl());
			sr.setContent(r.getContent());
			sr.setDate(r.getDate());
			sr.setTags(r.getTags());
			sr.setReserve(r.getId());
			ret.add(sr);
			
		}
		return ret;
	}
	*/
}
