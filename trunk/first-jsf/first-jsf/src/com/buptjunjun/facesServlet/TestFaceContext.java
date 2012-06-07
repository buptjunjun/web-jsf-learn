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
    * ȡ���ڷ������ϵľ���·��
    */  
   public void showFacesContext()
   {
	   //��ȡfaces��������
	   FacesContext context = FacesContext.getCurrentInstance();
	   //ͨ��facesContex��ȡ ��ͨHttpServletRequest����
	   ExternalContext exContext = context.getExternalContext();
	   HttpServletRequest request = (HttpServletRequest)exContext.getRequest();
	   
	   //�����Ӧ�ó����ڷ������ϵ���ʵĿ¼
	   this.info = "this root = "+request.getSession().getServletContext().getRealPath("/");
	   this.show = !this.show;
	   
	   System.out.println(this.info);   
   }
   
   /**
    * ��ӡmessage
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
