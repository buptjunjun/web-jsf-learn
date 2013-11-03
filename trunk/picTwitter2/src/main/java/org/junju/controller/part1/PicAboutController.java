package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PicAboutController {
	
	private PicServices picservice = new PicServicesMongo();
	@RequestMapping(value="/about",method = RequestMethod.GET)
	public String showInputPage (Model model )
	{		
		model.addAttribute("tags", this.picservice.getTag());		
		return "about";
    }
	
	
}

