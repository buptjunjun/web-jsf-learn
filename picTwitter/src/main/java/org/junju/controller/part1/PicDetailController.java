package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.UIComment;
import org.junjun.controller.logic.Buffer;
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
		model.addAttribute("tags", Buffer.getTags());		
		model.addAttribute("kind", null);  // weekly , monthly , newest

		
		Item item = Buffer.getItemBuffer().get(id);
		
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
		
/*		List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);*/
		return "detail";
    }
	
	
	@RequestMapping(value = "/{arrow}/{id}", method = RequestMethod.GET)
	public String preNext (@RequestParam String kind, @PathVariable String arrow, @PathVariable String id, Model model )
	{	

		model.addAttribute("tags", Buffer.getTags());	
			
		Item item = Buffer.getItemBuffer().get(id);
		
		
		// if item is not in the buffer , set it to a buffered item;	
		if (item == null)
		{
			item = this.picservice.getItem(id);
		}
		
		if(item == null)
		{
			item = Buffer.getIndexitem().get(0);
		}
		
		if(!Constant.kinds.contains(kind))
			kind = Constant.daily;
		
		int hold = 0;
		List<Item> items = Buffer.getTypeKind(item.getType(), kind);		
		Item newItem = null;
		
		int position = items.indexOf(item);
		int size = items.size();
		
		if(position < 0)
			hold = 0;
		
		if("pre".equals(arrow))
		{
			newItem = items.get((hold-1 + size)%size);
		}
		else
		{
			newItem = items.get((hold+1+size)%size);
		}
		model.addAttribute("item", newItem);
		
	/*	List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);*/
		return "redirect:/detail/"+newItem.getId()+"?kind="+kind;
    }
}

