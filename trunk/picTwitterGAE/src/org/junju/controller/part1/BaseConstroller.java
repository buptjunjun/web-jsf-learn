package org.junju.controller.part1;

import org.apache.commons.lang3.StringUtils;
import org.junjun.bean.part1.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({"login","user"})
public class BaseConstroller {
	
	
	public BaseConstroller() {
		// TODO Auto-generated constructor stub
	}
	
	 @ModelAttribute("login")
	   public boolean login() {
	       return false; // populates form for the first time if its null
	   }

	 @ModelAttribute("user")
	 public User user() {
	     return new User(); // populates form for the first time if its null
	 }
	 
	 public Boolean iflogin(@ModelAttribute("login") Boolean login, User user)
	 {
		 if(login == false)
			 return false;
		 else if(user!=null && !StringUtils.isBlank(user.getIdSource()))
			 return true;
		 else return false;
			 
	 }
}
