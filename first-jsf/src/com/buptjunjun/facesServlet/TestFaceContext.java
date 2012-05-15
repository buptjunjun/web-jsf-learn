package com.buptjunjun.facesServlet;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.*;


public class TestFaceContext 
{
   private String info = "test";
   private boolean show = false;

	private String messages ="msg\n";
   
   /**
    * 取得在服务器上的绝对路径
    */  
   public void showFacesContext()
   {
	   //获取faces的上下文
	   FacesContext context = FacesContext.getCurrentInstance();
	   //通过facesContex获取 普通HttpServletRequest对象
	   ExternalContext exContext = context.getExternalContext();
	   HttpServletRequest request = (HttpServletRequest)exContext.getRequest();
	   
	   //获得在应用程序在服务器上的真实目录
	   this.info = "this root = "+request.getSession().getServletContext().getRealPath("/");
	   this.show = !this.show;
	   
	   System.out.println(this.info);   
   }
   
   /**
    * 打印message
    */
   public void showMessageText()
   {
	   FacesContext fcontext = FacesContext.getCurrentInstance();
	   List<FacesMessage> mesList =  fcontext.getMessageList();
	   
	   //print messages
	   for(FacesMessage msg: mesList)
	   {
		   System.out.println(" message:" + msg.getDetail());
		   this.messages+=msg.getDetail()+"\n";
	   }
	   
   }
   
   
   
   
   
   public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
   public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

}
