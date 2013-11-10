package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.User;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"user"})
public class PicAboutController {
	
	private PicServices picservice = new PicServicesMongo();
	@ModelAttribute("user")
    public User user() {
       return new User(); // populates form for the first time if its null
   }
	
	@RequestMapping(value="/about",method = RequestMethod.GET)
	public String showInputPage (@ModelAttribute User user,Model model )
	{		
		model.addAttribute("tags", this.picservice.getTag());		
		return "about";
    }
	
	
}

