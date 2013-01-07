package org.cb.common;

public abstract class Task
{
	private String taskName;
	
	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	public void doTask(){};
}
