package com.buptjunjun.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ThreadFuture {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FutureTask<Integer> ft = new  FutureTask<Integer>(new Callable<Integer>()
		{
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				TimeUnit.SECONDS.sleep(10);
				return 10;
			}
		});
		
		
		Thread t = new Thread(ft);
		t.start();
		try {
			// will block here
			System.out.println("start wait");
			int result = ft.get();
			System.out.println("result="+result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
