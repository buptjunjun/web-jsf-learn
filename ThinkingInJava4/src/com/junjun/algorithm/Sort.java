package com.junjun.algorithm;

public abstract class Sort
{
	public static final int  INCREMENT = 0; //����
	public static final int  DECREMENT = 0; //����
	
	public int [] sort(int [] s, int incOrDec){return null;};
	public void print(int [] s)
	{
		if(s == null)
		{
			System.out.println("s == null");
			return;
		}
		
		for(int i:s)
		{
			System.out.print(i +" ");
		}
	}
}
