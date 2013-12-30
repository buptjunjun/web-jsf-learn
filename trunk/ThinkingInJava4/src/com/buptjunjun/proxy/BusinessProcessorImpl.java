package com.buptjunjun.proxy;

public class BusinessProcessorImpl implements BusinessProcessor{
	public void process1()
	{
		System.out.println("hello");
	}
	
	public String process2()
	{
		this.process1();
		
		return "hi";
	}
}
