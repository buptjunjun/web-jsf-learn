package org.junju.controller.rest;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junjun.bean.part1.Comment;
import org.junjun.bean.part1.Result;
import org.junjun.bean.part1.UIComment;
import org.junjun.bean.part1.User;

import org.junjun.controller.logic.PicServices;
import org.junjun.controller.logic.PicServicesMongo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value="/api")
@SessionAttributes({"login","user"})
public class PicCommentControllerRest
{

	private PicServices picservice = new PicServicesMongo();
		
	public PicCommentControllerRest() {
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
	 
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/comment",method=RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public Object comment(@RequestBody Comment comment, @ModelAttribute("login") Boolean login,@ModelAttribute("user") User user)
	{
		// if it is not login
		if(user == null || StringUtils.isEmpty(user.getId()) || StringUtils.isEmpty(comment.getComment())|| StringUtils.isEmpty(comment.getCommentTo()))
				return new Result("fail");
		
		comment.setCommentFrom(user.getId());
		comment.setDate(new Date());
		comment.setId(comment.getCommentFrom()+comment.getCommentTo());
		this.picservice.insertComment(comment);
		
		return new Result("ok");
	}
	/**
	 * get resource
	 * @return
	 */
	@RequestMapping(value="/comment/{itemid}",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public Object comment(@PathVariable String itemid)
	{

		List<UIComment> ret = this.picservice.getUIComments(itemid);
		return ret;
	}
	
}
