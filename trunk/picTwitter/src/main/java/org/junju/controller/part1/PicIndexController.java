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
		
		List<Item> items = this.picservice.getItemsWhenLoadIndx();
		model.addAttribute("items", items);
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
			List<Item> items = this.picservice.getItemsWhenLoad(type, kind);
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
			model.addAttribute("kind", null);  // weekly , monthly , newest
			model.addAttribute("currtype", type);	
			model.addAttribute("kinds", Constant.kinds);
			
			List<Item> items = this.picservice.getItemsWhenLoad(type, Constant.daily);
			
			model.addAttribute("items", items);
	        return "index";
    }
	
	 public void init()
	{
		if(Buffer.getNewestItem() == null)
		{
			List<Item> items = this.picservice.getNewestItems(null, new Date(), 1);
			if(items != null && items.size() > 0)
				Buffer.setNewestItem(items.get(0));
		}
		
		
		// init tags
		String [] types = "pictures,animals".split(",");
		
		for(String type:types)
		{
			Tag tag = new Tag();
			tag.setType(type);
			Buffer.getTags().add(tag);
		}
		
		// initial index page 
		if(Buffer.getTags()!=null)
		{
			int count = 0;
			List<Queue<Item>> listItemQueue = new ArrayList<Queue<Item>>();
			for(Tag tag:Buffer.getTags())
			{
				List<Item> items = picservice.getItemsWhenLoad(tag.getType(), Constant.daily);
				
				Queue<Item> queue = new LinkedList<Item>();
				queue.addAll(items);
				if(items!=null)
				{	
					listItemQueue.add(queue);
					count+=items.size();
				}
			}
			
			while(count >= 0)
			{
				Queue queue = listItemQueue.get(count%listItemQueue.size());
				if(queue!=null&&queue.size()>0)
					Buffer.getIndexitem().add((Item) queue.poll());
				count--;
			}
			
		}

	}
	
	
}

