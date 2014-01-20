package byr.web.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class searchController {
	 
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	 public String showHomePage (Map<String,Object> model ){
		         model.put("result", "test");
	         return "search";
    }
}

