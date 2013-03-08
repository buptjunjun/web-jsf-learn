package com.coderlong.search.springmvc.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coderlong.search.springmvc.beans.SearchResult;

import com.coderlong.search.springmvc.beans.SearchCriteria;
import com.coderlong.search.springmvc.service.SearchService;

public class SearchServiceImpl implements SearchService
{
	@Override
	public List<SearchResult> search(SearchCriteria criteria)
	{
		// TODO Auto-generated method stub
		 List<SearchResult>  result = new ArrayList<SearchResult>();
		 result.add(new SearchResult(criteria.getQuery(),criteria.getQuery(),criteria.getQuery(),new ArrayList(),new Date()));
		return result;
	}

}
