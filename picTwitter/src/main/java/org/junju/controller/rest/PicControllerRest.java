package org.junju.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Form1;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.TypeKind;
import org.junjun.controller.logic.Buffer;
import org.junjun.controller.logic.PicBuffer;
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
	private Buffer buffer = new Buffer();
	
	public PicControllerRest() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/loadmore",method=RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public Object loadMore( @RequestBody TypeKind typekind)
	{
		List<Item> ret = null;
		if(typekind == null || StringUtils.isEmpty(typekind.getId()))
			return fail;
		
		ret = this.picservice.getItemByTagRest(typekind.getType(),typekind.getId());	
		if(ret!=null ) return ret;
		return fail;
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
