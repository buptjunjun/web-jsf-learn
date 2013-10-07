package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.UIItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class PicAdminController {
	 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showInputPage (Model model )
	{		
		return "admin";
    }
	
}

