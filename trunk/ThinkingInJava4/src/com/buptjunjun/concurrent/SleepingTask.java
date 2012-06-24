package com.buptjunjun.concurrent;

import java.util.concurrent.*;
public class SleepingTask extends LiftOff{

	public void run()
	{
		while(this.countDown -- > 0)
		{
			System.out.println(this.status());
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		
		for(int i = 0; i < 5; i++)
		{
			exec.execute(new SleepingTask());
		}
		
	}

}
