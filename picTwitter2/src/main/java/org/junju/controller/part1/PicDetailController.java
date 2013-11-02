package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.UIComment;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PicDetailController {
	
	private PicServices picservice = new PicServicesMongo();
	private static String defaultType = "pictures";

	
	
	@RequestMapping(value = "/{tag}/{id}", method = RequestMethod.GET)
	public String showInputPage ( @RequestParam(required=false) String sort,@RequestParam(required=false) Integer skip, @PathVariable String tag, @PathVariable String id, Model model )
	{	
		// if item is not in the buffer , set it to a buffered item;
		Item item = picservice.getItem(id);
		model.addAttribute("tags", this.picservice.getTag());		
		model.addAttribute("sorts", Constant.sortby);	
		if(Constant.sortby.contains(sort))
			model.addAttribute("sort", sort);	
		else	
			model.addAttribute("sort", Constant.default_sort);	
		
		if(skip == null || skip<0 ||skip>Constant.MAX_SKIP*Constant.REST_LIMIT)
			skip = 0;
			
		model.addAttribute("skip", skip);	
		model.addAttribute("currtag", tag);	
		model.addAttribute("item", item);
		
		List<Item> recommends = this.picservice.getItemByTag(tag, 0, Constant.weekly, 3);
		model.addAttribute("recommends", recommends);
/*		List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);*/
		return "detail";
    }
	
	
	@RequestMapping(value = "/{arrow}/{tag}/{id}", method = RequestMethod.GET)
	public String preNext (@RequestParam String sort,@RequestParam Integer skip, @PathVariable String arrow, @PathVariable String tag, @PathVariable String id, Model model )
	{	
		if(skip == null || skip<0 ||skip>Constant.MAX_SKIP*Constant.REST_LIMIT)
			skip = 0;
		
		if(!Constant.sortby.contains(sort))
			sort = Constant.default_sort;
		
		Item newItem = null;
		if("next".equals(arrow))
		{
			newItem = this.picservice.getNextItem(tag, id, sort, skip);
			skip++;
		}
		else
		{
			newItem = this.picservice.getPreItem(tag, id, sort, skip);
			skip--;
		}
		
		return "redirect:/"+tag+"/"+newItem.getId()+"?sort="+sort+"&skip="+skip;
		
	/*	List<UIComment> comments = picservice.getUIComments(id);
		model.addAttribute("comments", comments);*/
    }
}

