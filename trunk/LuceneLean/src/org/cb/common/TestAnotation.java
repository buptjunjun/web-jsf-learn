package org.cb.common;

import java.lang.reflect.Field;
import java.util.Date;

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
		TestAnotation t = new TestAnotation();
		Field [] fs = t.getClass().getDeclaredFields();
		
		for(Field f:fs)
		{
			ObjectToField tmp = f.getAnnotation(ObjectToField.class);
			if(tmp == null) continue;
			
			System.out.println(tmp.fieldName() +"  " +tmp.type());
			
			try
			{
				Object value = f.get(t);
				String type = tmp.type();
				if("String".equals(type))
				{
					System.out.println("String :"+ (String)value);
				}
				else if("Integer".equals(type))
				{
					System.out.println("int :"+ (Integer)value);
				}
				else if("Date".equals(type))
				{
					System.out.println("int :"+ (Date)value);
				}
				
			} catch (IllegalArgumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
