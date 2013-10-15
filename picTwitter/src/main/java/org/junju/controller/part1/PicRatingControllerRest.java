package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.junjun.bean.part1.Collect;
import org.junjun.bean.part1.Comment;
import org.junjun.bean.part1.Form1;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.User;
import org.junjun.controller.logic.PicBuffer;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value="/api")
@SessionAttributes({"login","user","good","bad","collect"})
public class PicRatingControllerRest
{
	
	public class ID
	{
		public String id = "";
	}
	
	private PicServices picservice = new PicServicesMongo();
		
	 @ModelAttribute("user")
	 public User user() {
	     return new User(); // populates form for the first time if its null
	 }
	 @ModelAttribute("good")
	 public  List<String> good() {
	     return new ArrayList<String>(); // populates form for the first time if its null
	 }
	 
	 @ModelAttribute("bad")
	 public  List<String> bad() {
	     return new ArrayList<String>(); // populates form for the first time if its null
	 }
	 
	 @ModelAttribute("collect")
	 public  List<Collect> collect() {
	     return new ArrayList<Collect>(); // populates form for the first time if its null
	 }
	 
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/good",method=RequestMethod.POST)
	@ResponseBody
	public Object good(@RequestBody String id, @ModelAttribute("good") List<String> good)
	{
		// if it is not login
		if(StringUtils.isEmpty(id) || good.contains(id))
				return "false";
		
		good.add(id);	
		
		Item item = picservice.getItem(id);
		if(item!=null)
		{
			item.setGood(item.getGood()+1);
			//picservice.updateItem(item);
		}
		return "true";
	}
	
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/bad",method=RequestMethod.POST)
	@ResponseBody
	public Object bad(@RequestBody String id, @ModelAttribute("bad") List<String> bad)
	{
		// if it is not login
		if(StringUtils.isEmpty(id) || bad.contains(id))
				return "false";
		
		bad.add(id);	
		
		Item item = picservice.getItem(id);
		if(item!=null)
		{
			item.setBad(item.getBad()+1);
			picservice.updateItem(item);
		}
		return "true";
		
	}
	
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/collect",method=RequestMethod.POST)
	@ResponseBody
	public Object collect(@RequestBody String id, @ModelAttribute("collect") List<Collect> collect, @ModelAttribute("user") User user)
	{
		// if it is not login
		if(StringUtils.isEmpty(id) || user == null ||StringUtils.isEmpty(user.getId()))
				return "false";
		
		Collect c = new Collect();
		c.setCollectFrom(user.getId());
		c.setCollectTo(id);
		c.setId(c.getCollectFrom()+c.getCollectTo());
		
		// have contains the comments
		if(collect.contains(c))
			return false;
		
		Item item = picservice.getItem(id);
		if(item!=null)
		{
			item.setCollect(item.getCollect()+1);
			//picservice.updateItem(item);
		}
		return "true";
	}
}
