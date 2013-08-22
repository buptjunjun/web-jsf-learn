package org.junju.controller.part1;

import java.util.Map;

import org.movier.bean.Movie;
import org.movier.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class MovieController 
{	
	@Autowired
	private MovieService movieService = null;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	 public String home (Map<String,Object> model )
	 {
			Movie movie = movieService.getMovie("");
		 	movie.setName("Titannic");
		    model.put("movie", movie);		
		
		    
	       return "home";
	         
    }
	
	public MovieService getMovieService() {
		return movieService;
	}
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}
}

