package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Rating;
import org.junjun.bean.part1.UIItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/detail")
public class PicDetailController {
	 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	 public String showInputPage (Model model ){		
		
			List<Item> items = this.getItem();		
			List<Rating> ratings = this.getRating();
			List<UIItem> uis = new ArrayList<UIItem>();
			
			for(int i = 0; i < items.size(); i++)
			{
				UIItem u = new UIItem();
				u.setItem(items.get(i));
				u.setRating(ratings.get(i));
				uis.add(u);
			}
			model.addAttribute("uis", uis);
	        return "detail";
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
	
	
	private List<Rating> getRating()
	{
		 List<Rating> ret = new ArrayList<Rating>();
		 
		for(int i = 0;i < 5;i++)
		{
			Rating r = new Rating();
			ret.add(r);
		}
		 return ret;
	}
}

