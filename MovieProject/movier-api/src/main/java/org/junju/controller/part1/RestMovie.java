package org.junju.controller.part1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.junjun.bean.part1.Form1;
import org.movier.bean.Movie;
import org.movier.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value="/movie")
public class RestMovie {

	@Autowired
	private MovieService movieService = null;
	
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/{movieid}",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public Object getform(@PathVariable("movieid") String id)
	{
		 Movie movie = movieService.getMovie(id);
		 return movie;
	}
	
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/search",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public Object searchform(@Param("keyword") String keyword)
	{
		 List<Movie> movie = movieService.searchMovie(keyword);
		 return movie;
	}
}
