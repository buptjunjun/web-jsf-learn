package com.buptjunjun.concurrent;

import java.util.concurrent.*;
public class FixedCachedThreadPool 
{
	public static void main(String [] args)
	{
		ExecutorService exec = Executors.newFixedThreadPool(3);
		for(int i = 0; i < 5; i ++)
		{
			exec.execute(new LiftOff());
		}
		exec.shutdown();
	}

}
