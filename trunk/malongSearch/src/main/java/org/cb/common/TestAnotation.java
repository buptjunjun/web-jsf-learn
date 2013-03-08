package org.cb.common;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.cb.data.Blog;
import org.cb.util.converter;

public class TestAnotation
{
	public String getID()
	{
		return ID;
	}
	public void setID(String iD)
	{
		ID = iD;
	}
	public int getAge()
	{
		return age;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	@ObjectToField(type="String",fieldName="ID1", analyzed = false, store = false)
	private String ID = "aaaaaaaaaaaaaaaaaaddddddddddd";
	@ObjectToField(type="Integer",fieldName="AGE", analyzed = false, store = false)
	private int    age= 10;
	@ObjectToField(type="Date",fieldName="AGE", analyzed = false, store = false)
	private Date    date= new Date();
	
	private String abc = "";
	
	static public void main(String [] args)
	{

		Blog b = new Blog();
		b.setUrl("badu.com");
		b.setBlogerURL("baidu.com");
		b.setContent("helloÄãºÃÂð");
		b.setPostDate(new Date());
		b.setComment(100);
		b.setVisit(10);
		b.setCrawledDate(new Date());
		b.setId("aabcdef");
		b.setHost("baidu.com");
		b.setPictures(4);
		
		Document doc = converter.Object2Doc(b);
		System.out.print(doc);
	}
}
