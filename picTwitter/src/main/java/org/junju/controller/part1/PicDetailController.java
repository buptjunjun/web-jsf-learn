package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.UIItem;
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
		return "detail";
    }
	
}

