package org.junjun.nio;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyThreadPool extends Thread
{

	LinkedList<MyWorker> threadPool = new  LinkedList<MyWorker>();
	LinkedList<Upload> taskPool =    new LinkedList<Upload>();
	
	public MyThreadPool(int numberOfWorker)
	{
		for (int i = 0;  i < numberOfWorker; i++)
		{
			MyWorker worker = new MyWorker("worker " + i, this.threadPool);
			this.threadPool.add(worker);
			worker.start();
		}
	}
	
	public void addTask(Upload task)
	{	
		
		synchronized(this.taskPool)
		{
			this.taskPool.add(task);
			this.taskPool.notify();
		}
	}
	
	@Override
	public void run()
	{			
		while(true)
		{
			//System.out.println("wait");
			boolean available = false;
			for(int i = 0; i < threadPool.size(); i++)
			{
				MyWorker w = this.threadPool.get(i);
				if (!w.isRunning())
				{
					available = true;
					break; 
				}
			}	
			//System.out.println("1");
			if( available == false)
			{
				synchronized(threadPool)
				{
					try
				
					{
						threadPool.wait();
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//System.out.println("2");
					// TODO Auto-generated method stub
					if(taskPool.isEmpty())
					{
						synchronized(taskPool)
						{
					
							try
							{
								taskPool.wait();
							} catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}			
						}
					}
				}
			}
					
		
			
			//System.out.println("3");
			synchronized(taskPool)
			{
				MyWorker w = null;
				synchronized(this.threadPool)
				{
					
				
					for(int i = 0; i < threadPool.size(); i++)
					{
						w = this.threadPool.get(i);
						if (!w.isRunning())
						{			
							break; 
						}
					}
				}
					
				if(taskPool != null && !taskPool.isEmpty() && w != null)
				{
					Upload upload = null;
					try
					{
						upload = taskPool.removeFirst();
						if(upload!=null)
							w.setTask(upload);
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("taskPool size = " + (taskPool == null ? 0: taskPool.size()) );
					}
					
					
					
				}
			}
		}
		
}
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		MyThreadPool pool = new MyThreadPool(10);
		pool.start();
		for(int i = 0; i < 100; i++)
		{
			pool.addTask(new Upload("picture " + i));
			
			if (i == 50)
			try
			{
				Thread.sleep(30000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			else
			{
				try
				{
					Thread.sleep(50);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}
	}
	
}


class Upload
{
	String name = "";
	public Upload(String name)
	{
		this.name = name;
		// TODO Auto-generated constructor stub
	}
	public void start()
	{
		long time = (long) (100+new Random().nextFloat()*3000);
		try
		{
			
			TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(" uploading  " + name + "finished  take " + time/1000 +"second");
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return this.name;
	}
}
