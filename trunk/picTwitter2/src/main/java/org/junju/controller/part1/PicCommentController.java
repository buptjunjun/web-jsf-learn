package org.junju.controller.part1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junjun.bean.part1.Comment;
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
public class PicCommentController {
	
	private PicServices picservice = new PicServicesMongo();
	
	private static String defaultType = "pictures";
	
	 @ModelAttribute("login")
	   public boolean login1() {
	       return false; // populates form for the first time if its null
	   }

	 @ModelAttribute("user")
	 public User user() {
	     return new User(); // populates form for the first time if its null
	 }
	@RequestMapping(value="/comment12313",method = RequestMethod.POST)
	public String login (Comment comment, Model model,@ModelAttribute("login") Boolean login,@ModelAttribute("user") User user)
	{	
		if(login == false || user == null || StringUtils.isEmpty(user.getIdSource())||StringUtils.isEmpty(user.getId()))
			return  "login";
		
		comment.setCommentFrom(user.getId());
		comment.setId(user.getId()+comment.getCommentTo());
		comment.setDate(new Date());		
		//this.picservice.insertComment(comment);
		return  "login";
    }
	
}

