package org.junjun.spring.charpter3.second_autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ��ˣ�� Ҳ�Ǳ�����
 * @author junjun
 *
 */
// @Componet��ʾ���Ա�Springʵ����Ϊbean
@Component
public class Juggler implements Performer {
	
	//��ˣ�߱��ݵĶ�������
	//�Զ�ע�� ���ҿ���ʹ��el���ʽ
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
