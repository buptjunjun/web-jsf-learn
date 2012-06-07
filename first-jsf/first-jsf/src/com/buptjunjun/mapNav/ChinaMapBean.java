package com.buptjunjun.mapNav;
import java.awt.*;

import javax.faces.context.FacesContext;
import javax.faces.event.*;
import java.util.*;
public class ChinaMapBean
{
    private String outcome = null;
    
    //声明省份热区 
    private Rectangle xinjiangRect = new Rectangle(141,137,272,309);
    private Rectangle xizangRect = new Rectangle(68,345,361,528);
    
    //必须提供默认构造函数
    public ChinaMapBean(){}
    
    /**
     * 监听函数 参数必须是ActionEvent
     * @param e
     */
    public void mapAcitonListen( ActionEvent e)
    {
    	//获取face的上下文实例
    	FacesContext context = FacesContext.getCurrentInstance();
    	
    	//获取组建的客户端标示
    	String clientId = e.getComponent().getClientId(context);
    	
    	System.out.println("   client = " + clientId);
    	
    	//获取请求参数map
    	Map requestParams = context.getExternalContext().getRequestParameterMap();
    	Iterator i = requestParams.entrySet().iterator();
    	int num = 0;
    	while(i.hasNext())
    	{
    		System.out.println(num++ + " --- " + i.next().toString());
    	}
    	
    	try
    	{
	    	//获取鼠标点击时候的坐标
	    	int x = Integer.parseInt((String)requestParams.get(clientId+".x"));
	    	int y = Integer.parseInt((String)requestParams.get(clientId+".y"));
	    	System.out.println("(x,y) = " + "(" +x + "," + y +")");
	    	
	    	if(this.xinjiangRect.contains(new Point(x,y)))
	    	{
	    		this.outcome = "xinjiang";
	    	}
	    	else if(this.xizangRect.contains(new Point(x,y)))
	    	{
	    		this.outcome = "xizang";
	    	} 	
    	}
    	catch(Exception ee)
    	{
    		ee.printStackTrace();
    		this.outcome = "xinjiang";
    	}
    }
    
    /**
     * 应用程序的动作 返回点击到的区域的名字
     * @return outcome   
     */
    public String act()
    {
    	return this.outcome;
    }
}
