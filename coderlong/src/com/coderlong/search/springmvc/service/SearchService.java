package com.coderlong.search.springmvc.service;

import java.util.List;

import coderlong.search.springmvc.beans.SearchCriteria;

public interface SearchService
{
	public List<SearchResult> search(SearchCriteria criteria);
}
