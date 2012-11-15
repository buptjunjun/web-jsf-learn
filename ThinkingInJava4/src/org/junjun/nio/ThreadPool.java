package org.junjun.nio;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class ThreadPool extends Thread
{
	private static final int DEFAULT_NUM_WORKERS = 5;
	private LinkedList tasklist = new LinkedList();
	private LinkedList workerPool = new LinkedList();
	private boolean stopped = false;
	
	public ThreadPool()
	{
		this(DEFAULT_NUM_WORKERS);
	}
	
	public ThreadPool(int numOfWorkers)
	{
		for(int i = 0; i < numOfWorkers; i++)
		{
			workerPool.add(new Worker("" + i,this));
		}
		start();
	}
	
	public void run()
	{
		try
		{
			while(!stopped)
			{
				if (this.tasklist.isEmpty())
				{
					synchronized(this.tasklist)
					{
						tasklist.wait();
					}
				}
				else if (workerPool.isEmpty())
				{
					synchronized(workerPool)
					{
						workerPool.wait();
					}
				}
				this.getWorker().setTask((Runnable)tasklist.removeLast());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void addTask(Runnable task)
	{
		tasklist.add(task);
		synchronized(tasklist)
		{
			tasklist.notify();
		}
	}
	
	public void putWorker(Worker worker)
	{
		workerPool.addFirst(worker);
		
		synchronized(workerPool)
		{
			workerPool.notify();
		}
	}
	
	
	private Worker getWorker()
	{
		return (Worker)this.workerPool.removeLast();
	}
	
	
	public boolean isStopped()
	{
		return stopped;
	}
	
	public void stopThreads()
	{
		stopped =  true;
		Iterator i = workerPool.iterator();
		while(i.hasNext())
		{
			Worker w = (Worker)i.next();
			synchronized(w)
			{
				w.notify();
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		ThreadPool tp = new ThreadPool();
		
		for(int i = 0; i < 100; i++)
		{
			tp.addTask(new Runnable()
			{

				@Override
				public void run()
				{
					try
					{
						System.out.println(Thread.currentThread().getName() +"sleep 1 second");
						TimeUnit.SECONDS.sleep(1);
						System.out.println("A");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
			});
		}
		
		try
		{
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tp.stopThreads();
	}

}
