package org.junju.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.RequestParams;
import org.junjun.bean.part1.ResponseData;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value="/api")
public class PicControllerRest 
{
	
	private String fail = "fail";
	private PicServices picservice = new PicServicesMongo();
	
	public PicControllerRest() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/loadmore",method=RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public Object loadMore( @RequestBody RequestParams loadmore)
	{
		ResponseData resdata = new ResponseData();
		
		String tag = loadmore.getParam1();
		String sort = loadmore.getParam2();
		String pageStr = loadmore.getParam3();
		
		int page = 1;
		try{ page = Integer.parseInt(pageStr); }catch(Exception e){ e.printStackTrace();}
		
		List<Item> ret = null;
		
		ret = this.picservice.getItemByTag(tag, page, sort, Constant.REST_LIMIT);
		if(ret ==null || ret.size() <= 0)
		{
			resdata.setStatus(false);
			resdata.setData("");
		}
		else
		{
			resdata.setStatus(true);
			resdata.setData(ret);
		}
		return resdata;
	}
	
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/comment",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public Object comment(@PathVariable("itemID") String id)
	{
		
		return "ok";
	}
	
}
