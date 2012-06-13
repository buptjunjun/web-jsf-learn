package com.buptjunjun.test;

public class TestBean {

	private String hello;
	
	
	public void submit()
	{
		System.out.println("hello = " + hello);
	}
	
	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}

}
