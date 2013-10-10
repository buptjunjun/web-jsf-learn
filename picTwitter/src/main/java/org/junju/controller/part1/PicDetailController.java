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

@Controller
@RequestMapping("/detail")
public class PicDetailController {
	
	private PicServices picservice = new PicServicesMongo();
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String showInputPage (@PathVariable String id, Model model )
	{	
		model.addAttribute("tags", PicBuffer.tags);
		
		Item item = PicBuffer.itemBuffer.get(id);
		if(item == null)
			item = picservice.getItem(id);
		
		model.addAttribute("item", item);
		
		List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);
		return "detail";
    }
	
	
	@RequestMapping(value = "/{arrow}/{id}", method = RequestMethod.GET)
	public String preNext (@PathVariable String arrow, @PathVariable String id, Model model )
	{	
		model.addAttribute("tags", PicBuffer.tags);
		
		Item item = PicBuffer.itemBuffer.get(id);
		
		List<Item> listItem = PicBuffer.itemsNewest.get(item.getType());
		int hold = 0;
		int size = listItem.size();
		for(int i = 0; i<size ; i++)
		{
			if(id.equals(listItem.get(i).getId()))
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
			newItem = listItem.get((hold-1)%size);
		}
		else
		{
			newItem = listItem.get((hold+1)%size);
		}
		model.addAttribute("item", newItem);
		
		List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);
		return "redirect:/detail/"+newItem.getId();
    }
}

