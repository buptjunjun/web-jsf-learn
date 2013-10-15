package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;
import org.junjun.controller.logic.PicBuffer;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
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
	private PicServices picservice = new PicServicesMongo();
	public static String defaultType = "pictures";
  
	@ModelAttribute("login")
    public boolean login() {
       return false; // populates form for the first time if its null
   }
	public PicIndexController() 
	{
		if(PicBuffer.itemsNewest==null)
			init();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@ModelAttribute("login") Boolean login,Model model)
	{
		System.out.println(login);
		return showInputPage(null,null,model);
	}
	
	@RequestMapping(value = {"/{type}","/{type}/{kind}"}, method = RequestMethod.GET)
	 public String showInputPage (@PathVariable String type, @PathVariable String kind,Model model )
	 {
			if(type == null)
			{
				type=defaultType;
			}
			model.addAttribute("tags", PicBuffer.tags);
			model.addAttribute("kind", kind);  // weekly , monthly , newest
			List<Item> items = null;
			if("newest".equals(kind))
				 items = PicBuffer.itemsNewest.get(type);
			else if("weekly".equals(kind))
				 items = PicBuffer.itemsHottestWeekly.get(type);
			else if("monthly".equals(kind))
			{
				items = PicBuffer.itemsHottestMonthly.get(type);				
			}
			else
			{
				items = PicBuffer.itemsNewest.get(type);
				model.addAttribute("kind", "newest");
			}
			
			int size = items.size() < 5?items.size():5;
			
			model.addAttribute("items", items.subList(0, size-1));
			if(type == null)
			{
				type=defaultType;
			}
			
			model.addAttribute("currtype", type);
	        return "index";
     }
	
	synchronized public void init()
	{
		if(PicBuffer.itemsNewest != null)
			return;
		
		PicBuffer.itemsNewest = new  HashMap<String,List<Item>>();
		if(PicBuffer.tags == null)
			 PicBuffer.tags = picservice.getTag();
		
		Date now = new Date();
		for(Tag tag:PicBuffer.tags)
		{
			List<Item> items = picservice.getNewestItems(tag.getType(), new Date() , LIMIT);
			if(items!=null)
			{
				for(Item item:items)
					PicBuffer.itemBuffer.put(item.getId(), item);
			}
			PicBuffer.itemsNewest.put(tag.getType(), items);
		}
		
		
		// weekly 
		Calendar c = Calendar.getInstance();     
        c.setTime(now);  
        int day = c.get(Calendar.DATE);  
        c.set(Calendar.DATE, day - 7);  
        
        Date weekBefor = c.getTime();
        PicBuffer.itemsHottestWeekly = new  HashMap<String,List<Item>>();
		for(Tag tag:PicBuffer.tags)
		{
			List<Item> items = picservice.getTopItemByTime(tag.getType(), weekBefor, Integer.MAX_VALUE, LIMIT);
			if(items!=null)
			{
				for(Item item:items)
					PicBuffer.itemBuffer.put(item.getId(), item);
			}
			PicBuffer.itemsHottestWeekly.put(tag.getType(), items);
		}
		
		// monthly 
	    c = Calendar.getInstance();     
        c.setTime(now);  
        day = c.get(Calendar.DATE);  
        c.set(Calendar.DATE, day - 30);  
        
        Date monthlyBefore = c.getTime();
        PicBuffer.itemsHottestMonthly = new  HashMap<String,List<Item>>();
		for(Tag tag:PicBuffer.tags)
		{
			List<Item> items = picservice.getTopItemByTime(tag.getType(), monthlyBefore, Integer.MAX_VALUE, LIMIT);
			if(items!=null)
			{
				for(Item item:items)
					PicBuffer.itemBuffer.put(item.getId(), item);
			}
			PicBuffer.itemsHottestMonthly.put(tag.getType(), items);
		}

	}
	
	
}
