package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.UIComment;
import org.junjun.controller.logic.PicBuffer;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/detail")
public class PicDetailController {
	
	private PicServices picservice = new PicServicesMongo();
	
	private static String defaultType = "pictures";
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showInputPage ( @RequestParam String kind, @PathVariable String id, Model model )
	{	
		model.addAttribute("kind", kind);
		model.addAttribute("tags", PicBuffer.tags);
		
		Item item = PicBuffer.itemBuffer.get(id);
		
		// if item is not in the buffer , set it to a buffered item;
		if(item == null)
			item = picservice.getItem(id);
		
		// if the id is not correct ,give it a random one;
		List<Item> items = null;
		if(item == null)
		{
			items = PicBuffer.itemsNewest.get(defaultType);
			item = items.get(0);
		}
			
		model.addAttribute("item", item);
		
		List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);
		return "detail";
    }
	
	
	@RequestMapping(value = "/{arrow}/{id}", method = RequestMethod.GET)
	public String preNext (@RequestParam String kind, @PathVariable String arrow, @PathVariable String id, Model model )
	{	
		model.addAttribute("tags", PicBuffer.tags);
		
			
		Item item = PicBuffer.itemBuffer.get(id);
		
		model.addAttribute("kind", kind);  // weekly , monthly , newest			
		List<Item> items = null;
		String type = item.getType();
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
		
		// if item is not in the buffer , set it to a buffered item;

		if (item == null)
			item = items.get(0);
		
		int hold = 0;
		int size = items.size();
		for(int i = 0; i<size ; i++)
		{
			if(id.equals(items.get(i).getId()))
			{
				hold = i;
				break;
			}
		}
		
		Item newItem = null;
		if(hold<=0)
			hold = size;
		if("pre".equals(arrow))
		{
			newItem = items.get((hold-1)%size);
		}
		else
		{
			newItem = items.get((hold+1)%size);
		}
		model.addAttribute("item", newItem);
		
		List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);
		return "redirect:/detail/"+newItem.getId();
    }
}

