package org.junjun.spring.charpter1.second_aop;

/**
 * ����ʫ�� ������quest֮ǰ��֮������knight���¼�
 * @author junjun
 *
 */
public class Minstrel 
{
	public void singBeforeQeust()
	{
		System.out.println("Fa la la; The knight is so brave!");
	}
	
	public void singAfterQeust()
	{
		System.out.println("Tee hee he; The brave knight did embark on a quest");
	}
}
