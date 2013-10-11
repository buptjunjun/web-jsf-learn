package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.UIComment;
import org.junjun.controller.logic.PicBuffer;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/welcome")
public class PicLoginController {
	
	private PicServices picservice = new PicServicesMongo();
	
	private static String defaultType = "pictures";
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String login ( @RequestParam String kind, @PathVariable String id, Model model )
	{	
		return "redirect:/pic";
    }
	
	
	
}

