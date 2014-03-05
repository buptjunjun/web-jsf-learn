package org.junjun.spring.charpter3.second_autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 杂耍者 也是表演者
 * @author junjun
 *
 */
// @Componet表示可以被Spring实例化为bean
@Component
public class Juggler implements Performer {
	
	//杂耍者表演的豆袋个数
	//自动注入 并且可以使用el表达式
	@Autowired
	@Value("#{3}")
	private int beanBags = 3;
	
	public Juggler() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public Juggler( int beanBags) 
	{
		this.beanBags = beanBags;
	}
	
	public void perform() throws PerformanceException
	{
		
		System.out.println("juggler is juggling "+this.beanBags +" bean bags");
	}

}
