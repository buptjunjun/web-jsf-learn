package org.cb.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.cb.lucene.BlogSearcher;

public class ThreadPoolManager
{
	List<Task> taskQueue = Collections.synchronizedList( new LinkedList<Task>()); //任务队列
    private List<WorkerThread> workerQueue ;    //工作线程（真正执行任务的线程）
    private static int worker_num = 30;    //工作线程数量（默认工作线程数量是5）
    private static int worker_count = 0;

	static public void  main(String [] args)
	{
		List<String> listQuery = Arrays.asList("java c++ python 拟合 数据结构  机器学习 spring".split(" "));
		ThreadPoolManager pool = new ThreadPoolManager(2);
		for(int i = 0;i < listQuery.size(); i++)
		{
			BlogSearcher t = new BlogSearcher("E:/Lucene");
			t.setQueryStr(listQuery.get(i));
			//Task t = new SleepTask("sleep task-"+i);
			t.setTaskName(listQuery.get(i));
			pool.addTask(t);
		}
		
	}
	
	
	public ThreadPoolManager( int workerNumber)
	{
		this.worker_count = this.worker_num = workerNumber;
		this.workerQueue = new ArrayList<WorkerThread>();
		for(int i = 0; i < worker_count; i++)
			this.workerQueue.add(new WorkerThread("worker-"+i));
	}
	
	/**
	 * 
	 * @param task
	 */
	
	public void addTask(Task task)
	{
		synchronized(this.taskQueue)
		{
			if(task!=null)
			{
				this.taskQueue.add(task);
				this.taskQueue.notifyAll();
				System.out.println("add as task :"+task.getTaskName());
			}
		}
	}
	
	public void BatchAddTask(List<Task> tasks)
	{
		synchronized(this.taskQueue)
		{
			for(Task task: tasks)
			if(task!=null)
			{
				this.taskQueue.add(task);
				this.taskQueue.notifyAll();
				System.out.println("add as task :"+task.getTaskName());
			}
		}
	}
	
	public void destroy()
	{
		System.out.println("pool is going to be destroyed");
		if(this.workerQueue != null)
		{
			for(WorkerThread w: this.workerQueue)
			{
				w.setRuning(false);
			}
		}
		
		synchronized(this.taskQueue)
		{
			this.taskQueue.clear();
		}
		
		System.out.println("pool is  destroyed");
	}
	
	
	/**
	 *  worker thread
	 * @author andyWebsense
	 *
	 */
	private class WorkerThread extends Thread
	{
		private String workerName = null;
		private int taksId ;
		private boolean isRuning = true;
        private boolean isWaiting = false;
	        
		public WorkerThread(String workerName)
		{
			// TODO Auto-generated constructor stub
			this.workerName = workerName;
			start();
		}

		 public void setRuning(boolean isRuning)
		 {
		 	this.isRuning = isRuning;
		 }

		public boolean isRuning()
		{
			return isRuning;
		}

		public boolean isWaiting()
		{
			return isWaiting;
		}
		
		@Override
		public void run()
		{
			while(this.isRuning)
			{
				Task task = null;
				synchronized(taskQueue)
				{
					//当队列为空 等待 任务加入队列
					while(this.isRuning && taskQueue.isEmpty())
					{
						try
						{
							taskQueue.wait();
						} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					if(this.isRuning)
					{
						task = taskQueue.remove(0);
						
					}
				}
				
				if(task!=null)
				{
					System.out.println(task.getTaskName()+" begin to run");
					long begin = System.currentTimeMillis();
					this.isWaiting = false;
					task.doTask();
					this.isWaiting = true;
					long end = System.currentTimeMillis();
					System.out.println(task.getTaskName()+" reach to end used "+(end-begin)+" millionSeconds");
				}
				
				
			}
		}

	}
}
