package org.junju.controller.part1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.junjun.bean.part1.Form1;
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

import org.movier.bean.BResource;
import org.movier.service.MovieService;
import org.movier.service.ResourceService;

@Controller
@RequestMapping(value="/res")
public class RestResource 
{
	@Autowired
	private ResourceService resourceService = null;
	
	/**
	 * get resources according a movie id;
	 * @return
	 */
	@RequestMapping(value="/{movieid}",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public List<BResource> getform(@PathVariable("movieid") String id)
	{
		List<BResource> ret = resourceService.getResources(id);
		return ret;
	}
	
	/**
	 * delete resource
	 * @return
	 */
/*	@RequestMapping(value="/{resourceid}",method=RequestMethod.DELETE)
	@ResponseBody
	public Object deleteform(@PathVariable("resourceid") String id)
	{
		 this.resourceService.delResource(id);
		 return null;
	}*/
	
	/**
	 * using json format
	 * {"name":"junjun","age":100}
	 * @return
	 */
	@RequestMapping(value="/{formid}",method=RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Object createform(@RequestBody @Valid Form1 form,@PathVariable("formid") String id,
			BindingResult result) throws BindException
	{
		if(result.hasErrors())
		{
			throw new BindException(result);
		}
		 return result;
	}
	
	/**
	 * using json format
	 * {"name":"junjun","age":100}
	 * @return
	 */
	@RequestMapping(value="/{formid}",method=RequestMethod.PUT,headers = "Content-Type=application/json")
	@ResponseBody
	public Object updateForm(@RequestBody Form1 form,@PathVariable("formid") String id)
	{
		 Map result = new HashMap<String,Object>();	 
		 result.put("result", "update from"+id+ "OK:"+form.toString());
		 return result;
	}
}
