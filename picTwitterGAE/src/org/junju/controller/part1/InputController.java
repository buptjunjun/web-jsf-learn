package org.junju.controller.part1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/input")
public class InputController {
	 
	
	public InputController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	 public String showInputPage (Model model ){		
		
	         return "inputtest";
    }
	@RequestMapping(value = "/inputtest", method = RequestMethod.GET)
	 public String dealwithInput (@RequestParam("input") String param,String autoparam ,Model model ){
		 		 String msg = "param:"+param+",autoparam="+autoparam; 
				  System.out.println();
		         model.addAttribute("msg", msg);
	         return "inputtest";
   }
}

