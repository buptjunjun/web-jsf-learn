package org.junjun.spring.charpter4.second_apoAnnotation;

import org.springframework.stereotype.Component;

/**
 * �����ĵ���Ը�� 
 * @author junjun
 *
 */
public class Volunteer implements Thinker
{
	private String thoughts = null;
	public Volunteer()
	{}
	
	public void thinkOfSomething(String thoughts)
	{
		this.thoughts =thoughts;
	}
	
	public String getThoughts()
	{
		return this.thoughts;
	}

}
