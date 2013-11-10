package org.junju.controller.part1;


import java.util.List;
import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.User;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"login","user"})
public class PicIndexController {
	
	public static final int LIMIT = 300;
	private PicServices picservice = new PicServicesMongo();
	public static String defaultType = "photo";
    
	@ModelAttribute("login")
    public boolean login() {
       return false; // populates form for the first time if its null
   }
	
	@ModelAttribute("user")
    public User user() {
       return new User(); // populates form for the first time if its null
   }
	
	public PicIndexController() 
	{
		
	}
	
	@RequestMapping(value={"/",""},method = RequestMethod.GET)
	public String index(@RequestParam(value="sort",required=false) String sort, @ModelAttribute User user,Model model)
	{
		model.addAttribute("tags", this.picservice.getTag());		
		model.addAttribute("sorts", Constant.sortby);	
		if(Constant.sortby.contains(sort))
			model.addAttribute("sort", sort);	
		else	
			model.addAttribute("sort", Constant.default_sort);	
		

		model.addAttribute("currtag", "");	
		boolean loadmore = true;
		model.addAttribute("loadmore", loadmore);//enable water fall 
		if(!Constant.sortby.contains(sort))
			sort = Constant.default_sort;
		
		List<Item> items = this.picservice.getItemByTag(null, 0,sort, Constant.LIMIT);
		
		model.addAttribute("items", items);
        return "index";
	}
	
	
	

	@RequestMapping(value = {"/{tag}"}, method = RequestMethod.GET)
	 public String showInputPage (@PathVariable String tag,@RequestParam(value="sort",required=false) String sort, Model model )
	 {
		model.addAttribute("tags", this.picservice.getTag());		
		//model.addAttribute("kind", Constant.defaultKind);  // weekly , monthly , newest
		model.addAttribute("currtag", tag);	
		
		model.addAttribute("sorts", Constant.sortby);
		if(Constant.sortby.contains(sort))
			model.addAttribute("sort", sort);	
		else	
			model.addAttribute("sort", Constant.default_sort);	
		
		boolean loadmore = true;
		model.addAttribute("loadmore", loadmore);//enable water fall 
		
		if(!Constant.sortby.contains(sort))
			sort = Constant.default_sort;
		
		List<Item> items = this.picservice.getItemByTag(tag, 0, sort, Constant.LIMIT);
		
		model.addAttribute("items", items);
        return "index";
    }

	
}

