package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.junjun.bean.part1.BHotSet;
import org.junjun.bean.part1.BMovieSet;
import org.junjun.bean.part1.BTagSet;
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
@RequestMapping("/index")
public class MovieIndexController 
{	
	@Autowired
	private MovieService movieService = null;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	 public String home (Map<String,Object> model )
	 {
		List<String> tags = Arrays.asList("2013,2014".split(","));
		 model.put("tags", tags);	
		 model.put("test", "test");
	     return "movieHome";         
    }
	
	/**
	 *  tags
	 * @return
	 */
	private List<BTagSet> getTagSets()
	{
		List<BTagSet> tagSets = new ArrayList<BTagSet>();
		return tagSets;
	}

	/**
	 * recommented Movies
	 * @return
	 */
	private List<BMovieSet> getMovieSets()
	{
		List<BMovieSet> movieSets = new ArrayList<BMovieSet>();
		return movieSets;
	}
	
	/**
	 * Hot movies
	 * @return
	 */
	private List<BHotSet> getHotSets()
	{
		List<BHotSet> hotSets = new ArrayList<BHotSet>();
		return hotSets;
	}
}

