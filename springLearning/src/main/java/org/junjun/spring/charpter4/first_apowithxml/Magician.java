package org.junjun.spring.charpter4.first_apowithxml;

/**
 *  能读心的魔术师 能读 thinker的心
 */

public class Magician implements MindReader
{
	private String thoughts = null; 
	public void interceptThoughts(String thoughts)
	{
		System.out.println("Intercepting volunteer's thoughts");
		this.thoughts = thoughts;
		
	}

	public String getThoughts()
	{
		// TODO Auto-generated method stub
		return this.thoughts;
	}

}
