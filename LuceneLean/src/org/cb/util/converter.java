package org.cb.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.cb.common.ObjectToField;
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
	public static Document   Object2Doc(Object obj)
	{
		java.lang.reflect.Field[] fs = obj.getClass().getDeclaredFields();
		Document doc = new Document();
		for(java.lang.reflect.Field f:fs)
		{
			f.setAccessible(true);
			ObjectToField tmp = f.getAnnotation(ObjectToField.class);
			if(tmp == null) continue;
			
			//System.out.println(tmp.fieldName() +"  " +tmp.type());
			Field field = null;
			try
			{
				Object value = f.get(obj);
				String type = tmp.type();
				String fieldName = tmp.fieldName();
				boolean store = tmp.store();
				boolean analyzed = tmp.analyzed();
							
				Store store1 = Field.Store.NO;
				if (store == true)
				{
					store1 = Field.Store.YES;
				}
				
				Index analyzed1 = Field.Index.NOT_ANALYZED;
				if (analyzed == true)
				{
					analyzed1 = Field.Index.ANALYZED;
				}
				
				if (value == null)
					continue;
		
				if("String".equals(type))
				{
					String v = (String)value;
					//System.out.println("String :"+ v);
					field = new Field(fieldName,v,store1,analyzed1);
				}
				else if("Integer".equals(type))
				{
					int v = (Integer)value;
					//System.out.println("String :"+ v);
					field = new IntField(fieldName,v,store1);
				}
				else if("Date".equals(type))
				{
					Date v = (Date)value;
					Long longv = v.getTime();
					//System.out.println("String :"+ v);
					field = new LongField(fieldName,longv,store1);
				}
				else if ("List".equals(type))
				{
					List v = (List)value;
					//System.out.println("String :"+ v);
					field = new Field(fieldName,v.toString(),store1,analyzed1);
				}		
				
			
			} 
			catch (IllegalArgumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(field != null)
				doc.add(field);
		}
		return doc;
	}
}
