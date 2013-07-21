package com.movie.dao.service;

import java.util.List;
import java.util.Set;

import org.easyGoingCrawler.bean.BResource;
import org.easyGoingCrawler.docWriter.Movie;

public interface DBResourceService
{
	//update a movie
	public String update(BResource r,Set<String> field2update);
	public String update(BResource r);
	
	public String delete(BResource r);
	public String add(BResource r);
	public List<BResource> get(String movieid);
	public List<BResource> get(String movieid,int magicNum);
}
