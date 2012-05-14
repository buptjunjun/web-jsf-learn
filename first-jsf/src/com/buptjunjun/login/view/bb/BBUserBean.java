package com.buptjunjun.login.view.bb;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.buptjunjun.login.model.bean.UserBean;
import com.buptjunjun.login.model.exception.UserException;
import com.buptjunjun.login.model.service.UserService;
import com.buptjunjun.login.model.serviceImpl.UserServiceImpl;


public class BBUserBean
{
	private String name = null;
    private String password = null;
    
    //用于依赖注入
    private UserService userService = null;
    
    //依赖注入
    public void setUserService(UserService userService) {
		this.userService = userService;
	}
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
    public String loginAction()
    {
    	
    	//申明一个用户对象
    	UserBean user = new UserBean();
    	//获取FacesContext 上下文
    	FacesContext facesContext = FacesContext.getCurrentInstance();
    	//访问登录业务对象
        //UserService userService = new UserServiceImpl(); //不这样用因为会产生依赖
    
        //调用业务对象的登陆方法	
    	try
    	{
    		user = userService.login(name, password);

    		if(user == null)
    		{  
    			System.out.println("BBuserBean user == null");
    			facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "password is increct.",""));
    			return "failure";
    		}
    		
    		
    		System.out.println("name = " + user.getName()+ " password = " + user.getPassword() + " return success");
    		return "success";
    		
    	}
    	catch(UserException ue)
    	{  System.out.println("ue" + ue.toString());
    		facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "could not find user.",""));
			return "failure";
    	}
    	catch(Exception e)
    	{
    		System.out.println("e" +e.toString());
    		facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "could not login .",""));
    		return "failure";
    	}
       
    }
}
