package com.buptjunjun.annotation.apt;

/**
 * ��Multiplier��ȡһ���ӿ�
 * @author junjun
 *
 */

@ExtractInterface("IMultiplier")
public class Multiplier 
{
	/**
	 * �˷�
	 * @param x
	 * @param y
	 * @return
	 */
	public int multiply(int x, int y)
	{
		int total = 0;
		for(int i = 0; i < x; i++)
		{
			total = this.add(total,y);
		}
		
		return total;
	}
	
	/**
	 * �ӷ�
	 * @return
	 */
	private int add(int x, int y) {return x+y;}; 
	
}
