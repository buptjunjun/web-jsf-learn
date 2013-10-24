package org.junju.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.UIComment;
import org.junjun.bean.part1.User;
import org.junjun.controller.logic.PicBuffer;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesJPA;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.apache.commons.lang3.*;
@Controller
@SessionAttributes({"login","user"})
@RequestMapping(value="/api")
public class PicLoginControllerRest {
	
	private PicServices picservice = new PicServicesJPA();
	
	private static String defaultType = "pictures";
	
	 @ModelAttribute("login")
	   public boolean login() {
	       return false; // populates form for the first time if its null
	   }
 
	
 
 	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login ( Model model)
	{	
			return "login";
	}
	@RequestMapping(value="/login",method = RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public Object login (  @RequestBody User user,@ModelAttribute("login") Boolean login,Model model)
	{	
		if(user == null || StringUtils.isBlank( user.getSource()) ||StringUtils.isBlank( user.getIdSource()))
		{
			model.addAttribute("login", false);
			return "fail";
		}
		User u = picservice.getUser(user.getSource()+user.getIdSource());
		String id = user.getSource()+user.getIdSource();
		user.setId(id);
		if(u == null)
		{		
			picservice.insert(user);
			// the user login successfully
			login =  true;
			model.addAttribute("user", user);
		}
		else
		{
			String idExist = u.getId();
			if(idExist.equals(user.getId()))
			{
				login =  true;
				model.addAttribute("user", user);
			}
			else
				login = false;
		}
		
		System.out.println(login);
		model.addAttribute("login", login);
		
		return "ok";
    }
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	@ResponseBody
	public Object logout ( @ModelAttribute(value="login") Boolean login, Model model,SessionStatus session)
	{	
		session.setComplete();
		System.out.println();
		System.out.println(login);
		return "ok";
    }


}

