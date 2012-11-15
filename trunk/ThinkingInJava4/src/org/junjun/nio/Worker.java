package org.junjun.nio;

public class Worker extends Thread
{

	private String workerID;
	private Runnable task;
	private ThreadPool threadpool;
	
	public Worker(String id, ThreadPool pool)
	{
		 this.workerID = id;
		 this.threadpool = pool;
		 start();
	}
			
	public void setTask (Runnable t)
	{
		task = t;
		synchronized (this)
		{
			notify();
		}
	}
			
	
	public void run()
	{
		try
		{
			while(!threadpool.isStopped())
			{
				synchronized(this)
				{
					if (task != null)
					{
						try
						{
							task.run();
						}
						catch(Exception e1)
						{
							e1.printStackTrace();
						}
						// return itself to threadpool
						this.threadpool.putWorker(this);
					}
					wait();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String toString()
	{
		return "worker : " + workerID;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
