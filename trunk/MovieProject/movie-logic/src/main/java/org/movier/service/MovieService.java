package org.movier.service;

import java.util.Date;
import java.util.List;

import org.movier.bean.*;

public interface  MovieService 
{
	public Movie getMovie(String id);
	public List<Movie> getMovie(Date date,int limit);
	public List<Movie> searchMovie(String keywords);
	public String updateMovie(Movie movie);
	public String addMovie(Movie movie);
	public String delMovie(String id);
}
