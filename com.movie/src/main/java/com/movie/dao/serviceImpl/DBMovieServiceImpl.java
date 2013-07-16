package com.movie.dao.serviceImpl;

import java.util.List;
import java.util.Set;

import org.easyGoingCrawler.DAO.DAOMongo;
import org.easyGoingCrawler.DAO.EGDAOMongoMovie;
import org.easyGoingCrawler.bean.BResource;
import org.easyGoingCrawler.docWriter.Movie;

import com.movie.dao.service.DBMovieService;

public class DBMovieServiceImpl implements DBMovieService
{
	private DAOMongo egMongoMovie = null;
	public DBMovieServiceImpl(DAOMongo egMongoMovie)
	{
		this.egMongoMovie = egMongoMovie;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}


	public String update(BResource r,Set<String> field2update)
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String delete(Movie m)
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String add(Movie m)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<Movie> query(String query)
	{
		return null;
	}
	
	public Movie get(String id)
	{
		Movie m = (Movie) egMongoMovie.search(id, Movie.class);
		return m;
	}


	public String update(Movie m, Set<String> field2update)
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String update(Movie m)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
