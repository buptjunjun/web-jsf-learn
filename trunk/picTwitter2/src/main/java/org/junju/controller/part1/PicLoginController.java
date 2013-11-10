package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.List;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.UIComment;
import org.junjun.bean.part1.User;
import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.apache.commons.lang3.*;
@Controller
@SessionAttributes({"login","user"})
public class PicLoginController {
	
	private PicServices picservice = new PicServicesMongo();
	
	private static String defaultType = "pictures";
	
	 @ModelAttribute("login")
	   public boolean login() {
	       return false; // populates form for the first time if its null
	   }
 
	 @ModelAttribute("user")
	 public User user() {
	     return new User(); // populates form for the first time if its null
	 }
 
 	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login ( Model model)
	{	
			return "login";
	}
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login ( @ModelAttribute("login") Boolean login, @ModelAttribute User user,Model model)
	{	
		if(user == null || StringUtils.isBlank( user.getSource()) ||StringUtils.isBlank( user.getIdSource()))
		{
			model.addAttribute("login", false);
			return "login";
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
		return "redirect:/pic";
    }
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout ( Model model,SessionStatus session)
	{	
		session.setComplete();
		return "redirect:/";
    }


}

