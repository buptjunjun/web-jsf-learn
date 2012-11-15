package org.junjun.synchronizedtest;

import java.util.concurrent.TimeUnit;

public class ThreadA
{
	public static void main(String[] args)

	{

		ThreadB b = new ThreadB();

		b.start();

		System.out.println("b is start....");

		synchronized (b)// 括号里的b是什么意思,起什么作用?
		{

			try

			{

				System.out.println("Waiting for b to complete...");
				
			/*	if(b.isAlive())
				{
					System.out.println("wait");
					b.wait();
				}
				else
				{
					System.out.println("wait 500");
					b.wait(500);
				}*/
				System.out.println("Completed.Now back to main thread");
				//b.notify();

			} catch (Exception e)
			{
			}

		}

		System.out.println("Total is :" + b.total);

	}

}

class ThreadB extends Thread

{

	int total;

	public void run()

	{
		

		synchronized (this)

		{
			
			System.out.println("ThreadB is running..");

			for (int i = 0; i < 100; i++)

			{

				total += i;

				System.out.println("total is " + total);

			}
			
			try
			{
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
