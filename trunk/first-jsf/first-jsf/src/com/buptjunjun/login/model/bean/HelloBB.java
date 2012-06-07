package com.buptjunjun.login.model.bean;

import javax.el.ValueExpression;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

public class HelloBB
{
    private String userName = "user name";
     
    //用于显示 文本的输出组建
    private HtmlOutputText outputText;
    
    public String sayHelloAction()
    {
    	if(this.userName.equalsIgnoreCase("stranger"))
    	{
    		FacesContext context = FacesContext.getCurrentInstance();
    		//声明一个输出文本组件
    		this.outputText = new HtmlOutputText();
    		//获得一个值表达式
    		ValueExpression value = context.getApplication().getExpressionFactory()
    								.createValueExpression(context.getELContext(),"#hello.sayHelloAction", String.class);
    		this.outputText.setValueExpression("value",value);
    		
    		//为HtmlOutputText  设置值
    		this.outputText.setValue("new friend");
    		
    		//设置css属性
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
