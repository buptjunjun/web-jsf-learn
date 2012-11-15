package org.junjun.nio;

import java.io.FileWriter;
import java.io.*;
import java.util.List;


public class MyWorker extends Thread
{
	String file = "result.txt";
	private Upload task;
	private String workerID = "NoID";
	private boolean running = false;
	FileWriter f = null;
	private List  threadPool = null;
	public MyWorker(String id ,List threadPool)
	{
		this.workerID = id;
		this.threadPool = threadPool;
		try
		{
			 f = new FileWriter(new File(file));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	
	public void setTask(Upload task)
	{
		// if add a task then notify  other thread.
		synchronized(this)
		{
			this.task = task;
			this.notify();
		}
	}
			
	@Override
	public void run()
	{
		while(true)
		{
			synchronized(this)
			{
				try
				{
					this.running = true;
					if(this.task != null)
					{
						System.out.println(this.getName() +" working to  " + task.toString());
						//f.write(this.getName() +" working to  " + task.toString()+"\n");
						task.start();
					}
					synchronized(this.threadPool)
					{
						threadPool.notify();
					}
					running =false;	
					this.wait();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					running =false;
				}
			}
		}
	}
	
	
	public boolean isRunning()
	{
		return running;
	}


	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
	}

}
