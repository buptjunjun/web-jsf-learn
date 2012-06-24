package com.buptjunjun.concurrent;

import java.util.concurrent.*;

public class SimplePriorities  implements Runnable
{
	private int countDown = 5;
	private volatile double d;
	private int priority ;
	
	int id = 0;
	public String toString() {
		return Thread.currentThread() + ": " +" id = " + id +"   "+ countDown;
		}
	
	public SimplePriorities(int priority,int id)
	{
		this.id = id;
		this.priority = priority;
	}
	
	public void run()
	{
		Thread.currentThread().setPriority(this.priority);
		while(true)
		{
			for(int i = 0; i < 10000; i++)
			{
				d += (Math.PI + Math.E)/(double)i;
				if (i % 1000 == 0)
					Thread.yield();
			}
			
			System.out.println(this);
			if(--this.countDown == 0)
				return;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 5; i++)
		{
			exec.execute(new SimplePriorities(Thread.MIN_PRIORITY+1, i));
			exec.execute(new SimplePriorities(Thread.MAX_PRIORITY, i));
			//exec.shutdown();
		}
		

	}

}
