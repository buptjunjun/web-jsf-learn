package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junjun.bean.part1.Constant;
import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.UIComment;
import org.junjun.controller.logic.Buffer;
import org.junjun.controller.logic.PicBuffer;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesJPA;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

