package org.cb.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.cb.data.Blog;

public class converter
{
	static public Blog doc2blog(Document doc)
	{
		return new Blog();
	}
	
	/*
	 * index a object using anotation
	 */
	public List<Field>   Blog2Fields(Object obj)
	{
		if(obj == null) return null;
		List<Field> list = new ArrayList<Field>();
		java.lang.reflect.Field[] fs= obj.getClass().getFields();
		for(java.lang.reflect.Field f: fs)
		{
			String fieldName = f.getName();
			try
			{
				Object fieldValue = f.get(obj);
				f.getAnnotation(annotationClass)
			} 
			catch (IllegalArgumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
