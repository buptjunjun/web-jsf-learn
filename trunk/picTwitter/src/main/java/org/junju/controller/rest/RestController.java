package org.junju.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.junjun.bean.part1.Form1;
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
@RequestMapping(value="/rest")
public class RestController 
{
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/{formid}",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public Form1 getform(@PathVariable("formid") String id)
	{
		 Form1 form = new Form1();
		 form.setAge(123);
		 form.setName(id);
		 return form;
	}
	
	/**
	 * delete resource
	 * @return
	 */
	@RequestMapping(value="/{formid}",method=RequestMethod.DELETE)
	@ResponseBody
	public Object deleteform(@PathVariable("formid") String id)
	{
		 Map result = new HashMap<String,Object>();
		 result.put("result", "delete from:"+id+ "OK");
		 return result;
	}
	
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
