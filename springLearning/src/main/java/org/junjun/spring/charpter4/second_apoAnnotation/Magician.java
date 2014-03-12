package org.junjun.spring.charpter4.second_apoAnnotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *  能读心的魔术师 能读 thinker的心
 */
@Component("magician")
@Aspect
public class Magician implements MindReader
{
	
	private String thoughts = null;

	/**
	 * 定义一个带参数的 pointcut
	 * @param thing
	 */
	@Pointcut("execution(* org.junjun.spring.charpter4.second_apoAnnotation.Thinker.thinkOfSomething(String)) && args(t)")
	public void thinking(String t){};
	
	@Before("thinking(t)")
	public void interceptThoughts(String t)
	{
		System.out.println("Intercepting volunteer's thoughts");
		this.thoughts = t;
		
	}

	public String getThoughts()
	{
		// TODO Auto-generated method stub
		return this.thoughts;
	}

}
