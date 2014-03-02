package org.junjun.spring.charpter2.first_competetion;

/**
 * 一边朗诵诗 一边杂耍的的表演者
 * @author junjun
 *
 */
public class PoeticJuggler extends Juggler {
	
	//诗
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
