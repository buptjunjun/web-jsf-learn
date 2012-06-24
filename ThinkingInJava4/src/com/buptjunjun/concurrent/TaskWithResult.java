package com.buptjunjun.concurrent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskWithResult implements Callable<String>{

	private int id;
	
	public TaskWithResult(int id)
	{
		this.id = id;
	}

	@Override
	public String call() throws Exception 
	{
		System.out.println(this.id+"sleep "+ id %10 * 1000);
		Thread.currentThread().sleep(id %10 * 1000);
		return "Result from TaskWithResult : " + id;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService exec = Executors.newCachedThreadPool();
		List<Future<String>>  results = new ArrayList<Future<String>>();
		for(int i = 0; i < 10; i++)
			results.add(exec.submit(new TaskWithResult(i)));
		
		for(Future f: results)
		{
			try {
				System.out.println(f.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				exec.shutdown();
			}
		}
	}

}
