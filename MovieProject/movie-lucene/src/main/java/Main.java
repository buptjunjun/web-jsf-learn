import java.util.List;

import org.apache.lucene.document.Document;
import org.movie.lucene.service.MovieLuceneService;
import org.movie.lucene.serviceImpl.*;
import org.movier.bean.BResource;
import org.movier.bean.Movie;
import org.movier.bean.Rating;
import org.movier.service.*;
import org.movier.serviceImpl.mongo.*;

public class Main 
{
	
	DAOMongo mongo = null;
	MovieLuceneService movielucene;
	
	RatingService ratingservice =null;
	ResourceService resourcesService = null;
	MovieServiceImpl movieService = null;
	
	public Main() 
	{
		movielucene = new MovieLuceneServiceImpl("/home/junjun/lucene/test");
		DAOMongo mongo = new DAOMongo("42.96.143.59",27017,"moviedb");
		
		ratingservice = new RatingServiceImpl(mongo);
		resourcesService = new ResourceServiceImpl(mongo);		
		movieService = new MovieServiceImpl(mongo);	
	}
	
	
	public void test()
	{
/*		List<BResource> res = this.resourcesService.getResources("57a86254759d985739df32a57d5ed82c");
		
		Rating rating = this.ratingservice.getRating(movies.get(0).getId());
		System.out.println(res);*/
		
/*		List<Movie> movies = this.movieService.getTopMovie();
		
		for(Movie m:movies)
		{
			this.movielucene.index(m);
		}*/
		
		List<Movie> listMovie = this.movielucene.search("Omega");
		
		for(Movie m:listMovie)
		{
			System.out.println(m);
		}
		
	}
	static public void main(String [] args)
	{
		Main m = new Main();
		m.test();
		
		
	}
}
