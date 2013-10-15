package org.junju.controller.part1;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.junjun.bean.part1.Form1;
import org.movier.bean.Rating;
import org.movier.service.CommentService;
import org.movier.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="/rating")
public class RestRating 
{
	@Autowired
	private RatingService ratingService = null;
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/{formid}",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public Rating getform(@PathVariable("formid") String id)
	{
		 Rating rating = ratingService.getRating(id);
		 return rating;
	}
	
	
	/**
	 * using json format
	 * {"name":"junjun","age":100}
	 * @return
	 */
	@RequestMapping(value="/{formid}",method=RequestMethod.PUT,headers = "Content-Type=application/json")
	@ResponseBody
	public Object updateForm(@RequestBody Rating rating,@PathVariable("formid") String id)
	{
		 this.ratingService.updateRating(rating);
		 return null;
	}
}