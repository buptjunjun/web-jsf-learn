package com.coderlong.search.springmvc.service;

import java.util.List;

import com.coderlong.search.springmvc.beans.SearchCriteria;
import com.coderlong.search.springmvc.beans.SearchResult;


public interface SearchService
{
	public List<SearchResult> search(SearchCriteria criteria);
}
