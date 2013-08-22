package org.junju.controller.part1;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.junjun.bean.part1.Form1;
import org.junjun.bean.part1.MovieUI;
import org.movier.bean.Movie;
import org.movier.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	 public void addmovie (@ModelAttribute("movie") Movie movie )
	 {
			String ret = this.movieService.addMovie(movie);	         
	 }
	
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	 public void delmovie (String id )
	 {
			String ret = this.movieService.delMovie(id);	         
	 }
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	 public void updatemovie (Movie movie )
	 {
			String ret = this.movieService.updateMovie(movie);	         
	 }
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	 public String getmovie (Date date,Integer limit,Model model)
	 {
			List<Movie> movies = this.movieService.getMovie(date, limit);
			model.addAttribute("movies", movies);
			
			return "movie";
	 }
	 
	public MovieService getMovieService() {
		return movieService;
	}
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}
}

