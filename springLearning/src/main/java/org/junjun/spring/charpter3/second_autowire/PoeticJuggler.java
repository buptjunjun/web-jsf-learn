package org.junjun.spring.charpter3.second_autowire;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一边朗诵诗 一边杂耍的的表演者
 * @author junjun
 *
 */
public class PoeticJuggler extends Juggler {
	
	//诗
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
