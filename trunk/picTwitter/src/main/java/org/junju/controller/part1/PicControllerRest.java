package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.junjun.bean.part1.Form1;
import org.junjun.bean.part1.Item;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value="/api")
public class PicControllerRest 
{
	
	private PicServices picservice = new PicServicesMongo();
	
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/{itemID}",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public Object getform(@PathVariable("itemID") String id)
	{
		Item item = PicBuffer.itemBuffer.get(id.trim());
		List<Item> ret = new ArrayList<Item>();
		//ret.add(item);
		for(Entry entry:PicBuffer.itemBuffer.entrySet())
		{
			ret.add((Item)entry.getValue());
			
		}
		
		return ret;
	}
	
	
}
