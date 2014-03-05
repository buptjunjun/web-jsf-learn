package org.junjun.spring.charpter3.second_autowire;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * һ������ʫ һ����ˣ�ĵı�����
 * @author junjun
 *
 */
public class PoeticJuggler extends Juggler {
	
	//ʫ
	@Autowired
	private Poem poem =null;
	
	public PoeticJuggler(int beanBags, Poem poem)
	{
		super(beanBags);
		this.poem = poem;
	}
	
	
	@Override
	public void perform() throws PerformanceException 
	{
		super.perform();
		System.out.println("while reciting....");
		poem.recite();
	}
}
