package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pic")
public class PicIndexController {
	 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	 public String showInputPage (Model model ){		
		
			List<Item> items = this.getItem();
			model.addAttribute("items", items);
	        return "index";
    }
	
	private List<Item> getItem()
	{
		 List<Item> ret = new ArrayList<Item>();
		 
		 String [] urls = 
				 {
				 "http://p0.pstatp.com/medium/319/1168961149",
				 "http://p.pstatp.com/medium/318/5580660950" ,
				 "http://p0.pstatp.com/medium/319/1168961149",
				 "http://p.pstatp.com/medium/318/5580660950" ,
				 "http://p0.pstatp.com/medium/319/1168961149",
				 };
		for(String url:urls)
		{
			Item i = new Item();
			i.setUrl(url);
			i.setType("test");
			ret.add(i);
		}
		 return ret;
	}
}

