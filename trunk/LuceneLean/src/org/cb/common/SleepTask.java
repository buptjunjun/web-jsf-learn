package org.cb.common;

import java.util.concurrent.TimeUnit;

public class SleepTask extends Task
{
	public SleepTask(String taskName)
	{
		this.setTaskName(taskName);
	}
	
	
	@Override
	public void doTask()
	{
		System.out.println(this.getTaskName() +" begin sleep ");
		try
		{
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(this.getTaskName() +" wake up now  ");
	}
}
