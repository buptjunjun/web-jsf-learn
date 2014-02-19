package byr.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import byr.web.bean.Result;
import byr.web.bean.ResultItem;
import byr.web.bean.SearchCriteria;
import byr.web.logic.ResultSearcher;
import byr.web.util.UIlabelStore;

@Controller
@RequestMapping(value="/searchapi")
public class RestController 
{
	Logger logger = Logger.getLogger(RestController.class);
	
	private ResultSearcher rs = new ResultSearcher();
	
	/**
	 * get result 
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public Result getform(@RequestBody SearchCriteria sc)
	{
		if(sc.getDate1() == null)
		{
			Date date1 = new Date();
			date1.setYear(0);
			sc.setDate1(date1);
		}
		
		if(sc.getDate2() == null)
		{
			sc.setDate2(new Date());
		}
			
		Result result = new Result();
		result.setSuccess(false);
		result.setErrorMessge("");
		List<ResultItem> ret = null;
		
		try 
		{
			result = rs.search(sc);			
			logger.info("success: keywords="+sc.getKeywords()+"  result.size="+(result==null?0:result.getCount()));
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("failed: keywords="+sc.getKeywords()+e.getMessage());
			result.setData(null);
			result.setSuccess(false);
			result.setErrorMessge(UIlabelStore.getMessage("search_error"));
		}		
		
		return result;
	}
	
	public static void main(String [] args)
	{
		SearchCriteria sc = new SearchCriteria();
		sc.setKeywords("10");
		sc.setLimit(2);
		new RestController().getform(sc);
	}
}
