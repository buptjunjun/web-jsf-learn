package org.junjun.spring.charpter4.first_apowithxml;

/**
 * �����ĵ���Ը�� 
 * @author junjun
 *
 */
public class Volunteer implements Thinker
{
	private String thoughts = null;
	public void thinkOfSomething(String thoughts)
	{
		this.thoughts =thoughts;
	}
	
	public String getThoughts()
	{
		return this.thoughts;
	}

}
