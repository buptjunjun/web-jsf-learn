package org.movier.service;

import org.movier.bean.Movie;
import org.movier.bean.MovieUI;

public interface  MovieService 
{
	public Movie getMovie(String id);
	public String updateMovie(Movie movie);
	public String addMovie(Movie movie);
	public String delMovie(String id);
}
