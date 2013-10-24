package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;
import org.junjun.controller.logic.Buffer;
import org.junjun.controller.logic.PicBuffer;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesJPA;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.junjun.controller.logic.PicUtil;

@Controller
@RequestMapping("/pic")
@SessionAttributes({"login","hello"})
public class PicIndexController {
	
	public static final int LIMIT = 300;
	private PicServices picservice = new PicServicesJPA();
	public static String defaultType = "photo";
  
	@ModelAttribute("login")
    public boolean login() {
       return false; // populates form for the first time if its null
   }
	
	public PicIndexController() 
	{
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model)
	{
		if(Buffer.getNewestItem() == null)
			init();
		
		model.addAttribute("tags", Buffer.getTags());		
		model.addAttribute("kind", null);  // weekly , monthly , newest
		model.addAttribute("currtype", null);	
		model.addAttribute("kinds", Constant.kinds);
		
		model.addAttribute("tags", Buffer.getTags());	
		
		List<Item> items = this.picservice.getItemByTag(null);
		model.addAttribute("items", items);
		
		// show error page
		//int i = 1/0;
		return "index";
	}
	
	@RequestMapping(value = {"/{type}/{kind}"}, method = RequestMethod.GET)
	 public String showInputPage (@PathVariable String type, @PathVariable String kind,Model model )
	 {
			if(Buffer.getNewestItem() == null)
				init();
		
			if(kind == null || !Constant.kinds.contains(kind))
				return this.showInputPage(type, model);
			
			if(type == null)
			{
				type=defaultType;
			}
			
			model.addAttribute("tags", Buffer.getTags());		
			model.addAttribute("kind", kind);  // weekly , monthly , newest
			model.addAttribute("currtype", type);
			model.addAttribute("kinds", Constant.kinds);
			List<Item> items = this.picservice.getItemByTagAndKind(type, kind);
			model.addAttribute("items", items);
			
	        return "index";
     }
	
	@RequestMapping(value = {"/{type}"}, method = RequestMethod.GET)
	 public String showInputPage (@PathVariable String type,Model model )
	 {
		if(Buffer.getNewestItem() == null)
			init();
		if(!Buffer.containTag(type))
			return this.index(model);
		
			
		model.addAttribute("tags", Buffer.getTags());		
		model.addAttribute("kind", Constant.defaultKind);  // weekly , monthly , newest
		model.addAttribute("currtype", type);	
		model.addAttribute("kinds", Constant.kinds);
		
		List<Item> items = this.picservice.getItemByTag(type);
		
		model.addAttribute("items", items);
        return "index";
    }
	
	 public void init()
	{
		if(Buffer.getNewestItem() == null)
		{
			List<Item> items = this.picservice.getItemByTag(null);
			if(items != null && items.size() > 0)
				Buffer.setNewestItem(items.get(0));
		}
		
		
		// init tags
		List<Tag>  types =  this.picservice.getTag();
		if(types!=null)
			Buffer.getTags().addAll(types);
	}
	
	
}

