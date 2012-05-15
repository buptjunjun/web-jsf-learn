package com.buptjunjun.login.model.bean;

import javax.el.ValueExpression;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

public class HelloBB
{
    private String userName = "user name";
     
    //������ʾ �ı�������齨
    private HtmlOutputText outputText;
    
    public String sayHelloAction()
    {
    	if(this.userName.equalsIgnoreCase("stranger"))
    	{
    		FacesContext context = FacesContext.getCurrentInstance();
    		//����һ������ı����
    		this.outputText = new HtmlOutputText();
    		//���һ��ֵ���ʽ
    		ValueExpression value = context.getApplication().getExpressionFactory()
    								.createValueExpression(context.getELContext(),"#hello.sayHelloAction", String.class);
    		this.outputText.setValueExpression("value",value);
    		
    		//ΪHtmlOutputText  ����ֵ
    		this.outputText.setValue("new friend");
    		
    		//����css����
    		this.outputText.setStyle("color:red");
    		
    		return "newFriend";
    		
    	}
    	
   		return "oldfriend";
    }
    
    
	public HtmlOutputText getOutputText() {
		return outputText;
	}

	public void setOutputText(HtmlOutputText outputText) {
		this.outputText = outputText;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
   
}
