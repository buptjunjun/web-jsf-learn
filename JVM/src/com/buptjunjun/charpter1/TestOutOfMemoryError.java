package com.buptjunjun.charpter1;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author junjun
 * ≤‚ ‘ OutOFMemoryError VM≤Œ ˝£∫
 * test1 :-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * test2 :-Xss10m;
 */
public class TestOutOfMemoryError {
	
	byte [] bytes = new byte[1024*1024];
	
	/**
	 * OutOfMemoryError happen on Heap
	 * test1 :-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
	 */
	static void test1()
	{
		// TODO Auto-generated method stub
		List<TestOutOfMemoryError> list = new ArrayList<TestOutOfMemoryError>();


		while(true)
		{
			list.add(new TestOutOfMemoryError());
		}
	}
	
	static public void donotstop()
	{
		while(true)
		{
			
		}
	}
	/**
	 * OutOfMemoryError happen on stack
	 * test2 :-Xss10m;
	 */
	static void test2()
	{
		while(true)
		{
			Thread t = new Thread(){
				@Override
				public void run() {
					donotstop();
				}
			};
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//new TestOutOfMemoryError().test1();
		new TestOutOfMemoryError().test2();
	}

}
