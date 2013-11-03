package org.junju.controller.part1;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {
	 
	public HelloController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	 public String showHomePage (Map<String,Object> model ){
		         model.put("message", "SpringMVC hello world -222-!");
	         return "hello";
    }
}
