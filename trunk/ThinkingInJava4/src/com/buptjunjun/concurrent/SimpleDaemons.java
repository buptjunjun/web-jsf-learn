package com.buptjunjun.concurrent;

import java.util.concurrent.TimeUnit;

public class SimpleDaemons implements Runnable 
{
	public void run()
	{
		while(true)
		{
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() +" ," + this);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 3; i++)
		{
			Thread deaom = new Thread(new SimpleDaemons());
			deaom.setDaemon(true);
			deaom.start();
		}
		
		System.out.println("all daemon started...");
		try {
			// 当main结束时候 所有的deamon都会自动结束
			TimeUnit.SECONDS.sleep(10);
			System.out.println("over.............");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
