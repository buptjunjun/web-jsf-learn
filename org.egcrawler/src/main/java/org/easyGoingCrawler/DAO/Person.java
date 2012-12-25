package org.easyGoingCrawler.DAO;

import java.util.Date;

public class Person {

	  private String id;
	  private String name;
	  private int age;
	  private byte[] content = "niha ¿œ∆≈".getBytes();
	  private Date date = new Date();

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
	    return "Person [id=" + id + ", name=" + name + ", age=" + age + "date" + date+"]";
	  }
	  
	  public byte[] getContent()
		{
			return content;
		}
	  
	}