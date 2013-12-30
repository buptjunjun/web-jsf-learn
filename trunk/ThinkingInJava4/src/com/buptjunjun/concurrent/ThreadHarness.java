package com.buptjunjun.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Latch √≈„≈
 * 
 * @author junjun
 *
 */
public class ThreadHarness {


	/**
	 * we create as many as threadCount Thread to excute a task.
	 * we need guarantee all the thread execute the task at the same time.
	 * and we need to caculate the time span from the first task to the end of last task.   
	 * CountDownLatch will provide us the ablility.
	 * @param threadCount
	 * @param task
	 * @return
	 * @throws InterruptedException
	 */
	public static  long testLatch(int threadCount, final Runnable task) throws InterruptedException
	{
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(threadCount);
		
		// create Thead
		for(int i = 0; i < threadCount;i++)
		{
			Thread t = new Thread()
			{
				@Override
				public void run() 
				{
					try
					{
						// wait start gate to open---startGate.countDown()
						startGate.await();
						try
						{
							task.run();
						}
						finally
						{
							endGate.countDown();
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			};
			
			t.start();
		}

		long start = System.nanoTime();
		startGate.countDown();	
		
		//wait all the thread to end---endGate.countDown()
		endGate.await();
		
		long end = System.nanoTime();
		return end - start;
	}

	
	public static void main(String[] args) throws InterruptedException
	{
		
		Thread task = new Thread()
		{
			@Override
			public void run() 
			{
				System.out.println(Thread.currentThread().getName()+"hello! I am running");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		
		long time=testLatch(10,task);
		System.out.println(time);
	}
}
