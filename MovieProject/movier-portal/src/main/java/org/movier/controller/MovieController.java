package org.movier.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MovieController {
	 
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	 public String aaa (Map<String,Object> model ){
		         model.put("message", "SpringMVC hello world -222-!");
		         System.out.print("");
	         return "hello";
    }
}

