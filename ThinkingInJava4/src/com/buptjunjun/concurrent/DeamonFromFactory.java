package com.buptjunjun.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeamonFromFactory implements Runnable
{
	
	public void run()
	{
		while(true)
		{
			try 
			{
				TimeUnit.MILLISECONDS.sleep(1000);
				System.out.println(Thread.currentThread() + "  " + this);
				
			}
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadFactory());
		for(int i = 0;i < 5; i++)
		{
			exec.execute(new DeamonFromFactory());
		}
		
		System.out.println("all deamon thread started....");
		
		Thread.sleep(10000);
		System.out.println("over........");
	}

}
