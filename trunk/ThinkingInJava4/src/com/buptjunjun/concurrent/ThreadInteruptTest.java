package com.buptjunjun.concurrent;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ThreadInteruptTest {

	@Test
	public void test() 
	{
		Thread t = new Thread(){
			@Override
			public void run() {
				try
				{
					while(true)
					{
						System.out.println("i am alive");
						
							TimeUnit.SECONDS.sleep(1);
						
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//Thread.currentThread().interrupt();
				}
			}
		};
		
		t.start();
		
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.interrupt();
		
		System.out.println(t.isInterrupted());
	}

}
