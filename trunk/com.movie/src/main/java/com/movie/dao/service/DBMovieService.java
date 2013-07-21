package com.movie.dao.service;

import java.util.List;
import java.util.Set;

import org.easyGoingCrawler.docWriter.Movie;

public interface DBMovieService
{
	//update a movie
	public String update(Movie m,Set<String> field2update);
	public String update(Movie m);
	public String delete(Movie m);
	public String add(Movie m);
	public Movie get(String id);
	public List<Movie> query(String query);
	public List<Movie> get(int maigcNum,int limit);
	
}
