package org.movier.serviceImpl.mongo;

import java.util.Date;
import java.util.List;

import org.movier.bean.Movie;
import org.movier.service.MovieService;

public class MovieServiceImpl implements MovieService{

	public Movie getMovie(String id) {
		// TODO Auto-generated method stub
		Movie m = new Movie();
		m.setName("Titanic");
		m.setDate(new Date());
		m.setDescription("discrption discrption discrptiondiscrptiondiscrption discrption discrption discrption discrption discrption ");
		return m;
	}

	public String updateMovie(Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addMovie(Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

	public String delMovie(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Movie> getMovie(Date date, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

}
