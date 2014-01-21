package byr.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import byr.web.bean.ResultItem;
import byr.web.bean.SearchCriteria;

@Controller
@RequestMapping(value="/search")
public class RestController 
{
	private int limit = 100;
	/**
	 * get result 
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public List<ResultItem> getform(@RequestBody SearchCriteria sc)
	{
		List<ResultItem> ret = new ArrayList<ResultItem>();	
		return ret;
	}
}
