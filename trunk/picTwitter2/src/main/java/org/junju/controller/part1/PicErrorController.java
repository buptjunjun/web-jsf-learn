package org.junju.controller.part1;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error")
public class PicErrorController 
{
	
	@RequestMapping(method = RequestMethod.GET)
	public String showInputPage (Model model )
	{	
		return "error";
    }
	
	
}

