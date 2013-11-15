package org.junjun.designpattern.factory;

/**
 * 
 * @author junjun
 *
 */
public class BeanFactory 
{
	public final static int HotBean=0;
	public final static int ColdBean=1;
	
	public Bean createBean(int which)
	{
		Bean bean = null;
		switch(which)
		{
		case HotBean:
			bean = new HotBean();
			break;
		case ColdBean:
			bean = new ColdBean();
			break;
		default:
			bean = null;
		}
		
		return bean;
	}
}
