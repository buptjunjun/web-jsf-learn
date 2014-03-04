package org.junjun.spring.charpter2.second_spEL;

import java.lang.reflect.Field;

/**
 * 用于测试表达式语言的类
 * @author andyWebsense
 *
 */
public class SpELBasicType
{
	private int typeInt = 0;
	private String typeString = null;
	private Object typeNull = new Object();
	private float typeFloat = 0.0f;
	private boolean typeBoolean = false;
	
	public String getOurStringAndInt()
	{
		return typeString+","+typeInt;
	}
	
	public int getTypeInt()
	{
		return typeInt;
	}
	public void setTypeInt(int typeInt)
	{
		this.typeInt = typeInt;
	}
	public String getTypeString()
	{
		return typeString;
	}
	public void setTypeString(String typeString)
	{
		this.typeString = typeString;
	}
	public Object getTypeNull()
	{
		return typeNull;
	}
	public void setTypeNull(Object typeNull)
	{
		this.typeNull = typeNull;
	}
	public float getTypeFloat()
	{
		return typeFloat;
	}
	public void setTypeFloat(float typeFloat)
	{
		this.typeFloat = typeFloat;
	}
	public boolean isTypeBoolean()
	{
		return typeBoolean;
	}
	public void setTypeBoolean(boolean typeBoolean)
	{
		this.typeBoolean = typeBoolean;
	}
	
	
	
	@Override
	public String toString()
	{
		return  this.typeString+","
			   +this.typeInt+","
			   +this.typeFloat+","
			   +this.typeNull+","
			   +this.typeBoolean;
	}
}
