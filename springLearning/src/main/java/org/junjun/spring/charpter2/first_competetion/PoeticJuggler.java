package org.junjun.spring.charpter2.first_competetion;

/**
 * һ������ʫ һ����ˣ�ĵı�����
 * @author junjun
 *
 */
public class PoeticJuggler extends Juggler {
	
	//ʫ
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
