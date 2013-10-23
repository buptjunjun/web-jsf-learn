package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.Date;
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
		// if item is not in the buffer , set it to a buffered item;
		Item item = picservice.getItem(id);
		model.addAttribute("tags", Buffer.getTags());		
		model.addAttribute("kind", kind);  // weekly , monthly , newest
		model.addAttribute("currtype", item.getType());	
		model.addAttribute("kinds", Constant.kinds);
			
		model.addAttribute("item", item);
		
/*		List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);*/
		return "detail";
    }
	
	
	@RequestMapping(value = "/{arrow}/{id}", method = RequestMethod.GET)
	public String preNext (@RequestParam String kind, @PathVariable String arrow, @PathVariable String id, Model model )
	{	

		model.addAttribute("tags", Buffer.getTags());	
			
		Item item = this.picservice.getItem(id);
		Item newItem = null;
		
		// if item is not in the buffer , set it to a buffered item;			
		if(!Constant.kinds.contains(kind))
			kind = Constant.defaultKind;
		
		
		
		if("next".equals(arrow))
		{
			newItem = this.picservice.getNextItem(id);
		}
		else
		{
			newItem = this.picservice.getPreItem(id);
		}
		return "redirect:/detail/"+newItem.getId()+"?kind="+kind;
		
	/*	List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);*/
    }
}

