package com.junjun.algorithm;

public abstract class Sort
{
	public static final int  INCREMENT = 0; //升序
	public static final int  DECREMENT = 0; //降序
	
	/**
	 * 排序过程
	 * @param s 带排数列
	 * @param incOrDec 控制升序还是降序
	 * @return
	 */
	public int [] sort(int [] s, int incOrDec){return null;};
	
	/**
	 * 打印 一个数列
	 * @param s
	 */
	public void print(int [] s)
	{
		if(s == null) { System.out.println("s == null"); return; }
		for(int i:s)
			System.out.print(i +" ");
	}
}
