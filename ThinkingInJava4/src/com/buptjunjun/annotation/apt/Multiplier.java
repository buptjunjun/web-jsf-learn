package com.buptjunjun.annotation.apt;

/**
 * 从Multiplier抽取一个接口
 * @author junjun
 *
 */

@ExtractInterface("IMultiplier")
public class Multiplier 
{
	/**
	 * 乘法
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
	 * 加法
	 * @return
	 */
	private int add(int x, int y) {return x+y;}; 
	
}
