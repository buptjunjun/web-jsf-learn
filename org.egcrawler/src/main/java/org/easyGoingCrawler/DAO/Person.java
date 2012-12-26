package org.easyGoingCrawler.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person {

	  private String id;
	  private String name;
	  private int age;
	  private byte[] content = "niha ¿œ∆≈".getBytes();
	  private Date date = new Date();
	  private List<String> urls = new ArrayList<String>();
	  
	public List<String> getUrls()
	{
		return urls;
	}

	public void setUrls(List<String> urls)
	{
		this.urls = urls;
	}

	public Date getDate()

	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public void setContent(byte[] content)
	{
		this.content = content;
	}

	public Person(String name, int age) {
	    this.name = name;
	    this.age = age;
	    urls.add("aaa");
	    urls.add("bbbb");
	  }
	  
	  public String getId() {
	    return id;
	  }
	  public String getName() {
	    return name;
	  }
	  public int getAge() {
	    return age;
	  }
	  
	  
	  
	  @Override
	  public String toString() {
	    return "Person [id=" + id + ", name=" + name + ", age=" + age + "date" + date+ "urls = " + urls+"]";
	  }
	  
	  public byte[] getContent()
		{
			return content;
		}
	  public static void main(String[] args)
	  {
		  System.out.println("-------");
	  }
	}