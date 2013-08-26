package org.movie.lucene.service;

import java.util.List;

import org.movier.bean.Movie;

public interface MovieLuceneService {

	public String index(Movie movie);
	public List<Movie> search(String query);

}
