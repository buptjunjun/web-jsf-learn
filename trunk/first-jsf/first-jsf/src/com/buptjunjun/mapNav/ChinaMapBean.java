package com.buptjunjun.mapNav;
import java.awt.*;

import javax.faces.context.FacesContext;
import javax.faces.event.*;
import java.util.*;
public class ChinaMapBean
{
    private String outcome = null;
    
    //����ʡ������ 
    private Rectangle xinjiangRect = new Rectangle(141,137,272,309);
    private Rectangle xizangRect = new Rectangle(68,345,361,528);
    
    //�����ṩĬ�Ϲ��캯��
    public ChinaMapBean(){}
    
    /**
     * �������� ����������ActionEvent
     * @param e
     */
    public void mapAcitonListen( ActionEvent e)
    {
    	//��ȡface��������ʵ��
    	FacesContext context = FacesContext.getCurrentInstance();
    	
    	//��ȡ�齨�Ŀͻ��˱�ʾ
    	String clientId = e.getComponent().getClientId(context);
    	
    	System.out.println("   client = " + clientId);
    	
    	//��ȡ�������map
    	Map requestParams = context.getExternalContext().getRequestParameterMap();
    	Iterator i = requestParams.entrySet().iterator();
    	int num = 0;
    	while(i.hasNext())
    	{
    		System.out.println(num++ + " --- " + i.next().toString());
    	}
    	
    	try
    	{
	    	//��ȡ�����ʱ�������
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
     * Ӧ�ó���Ķ��� ���ص���������������
     * @return outcome   
     */
    public String act()
    {
    	return this.outcome;
    }
}
