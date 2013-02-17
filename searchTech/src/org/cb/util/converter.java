package org.cb.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	public static String urlEncode(String url)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte [] b = md5.digest(url.getBytes());
			StringBuffer buf = new StringBuffer(""); 
			int i;
			for (int offset = 0; offset < b.length; offset++) 
			{ 
				i = (int)b[offset]; 
				if(i<0) i+= 256; 
				if(i<16) 
				buf.append("0"); 
				buf.append(Integer.toHexString(i)); 
			}
			return  buf.toString();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
	
	
	/**
	 * convert a doc to a blog
	 * @param doc
	 * @return
	 */
	static public Blog doc2blog(Document doc)
	{
		Blog blog = new Blog();
		java.lang.reflect.Field[] fields = blog.getClass().getDeclaredFields();
		for(java.lang.reflect.Field f: fields)
		{
			ObjectToField otf = f.getAnnotation(ObjectToField.class);
			
			if(otf == null)
				continue;
			f.setAccessible(true);
			String docfieldname = otf.fieldName();
			String objfieldname = f.getName();
			String type = otf.type();
			try
			{
				Object docfieldValue =doc.get(docfieldname) ;
				if(docfieldValue == null) continue;
				
				if ("Date".equals(type))
				{
					
					f.set(blog, new Date(Long.parseLong((String)docfieldValue)));
				}
				else if ("String".equals(type))
				{
					
					f.set(blog, (String)docfieldValue);
				}
				else if ("Integer".equals(type))
				{
					
					f.setInt(blog, Integer.parseInt((String)docfieldValue));
				}
				else if ("List".equals(type))
				{
					String tmpValue = (String)docfieldValue;
					tmpValue = tmpValue.replaceAll("\\[|\\]", "");
					String [] strs = tmpValue.split("\\s+");
					if(strs != null)
					{
						f.set(blog, Arrays.asList(strs));
					}
				}
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
		}
		return blog;
	}
	
	/*
	 * index a object using anotation ,no boosts value is set
	 */
	public static Document   Object2Doc(Object obj)
	{
		Map<String,Field> mf =  Object2FieldMap(obj);
		if(mf == null) return null;
		
		Document doc = new Document();
		for(Entry<String,Field> entry: mf.entrySet())
		{
			Field f = entry.getValue();
			if(f!=null)
				doc.add(f);
		}
		
		return doc;
	}
	
	
	/*
	 * index a object using anotation and set the field's boost value.
	 */
	public static Document   Object2Doc(Object obj,Map<String,Float> mboost)
	{
		Map<String,Field> mf =  Object2FieldMap(obj);
		if(mf == null) return null;
		
		Document doc = new Document();
		for(Entry<String,Field> entry: mf.entrySet())
		{
			String fieldName = entry.getKey();
			Field f = entry.getValue();
			
			// set the boost value of this field
			if(fieldName != null && mboost != null)
			{
				Float boost4field = mboost.get(fieldName);
				if(boost4field != null)
					f.setBoost(boost4field);				
			}
			doc.add(f);
		}
		
		return doc;
	}
	
	
	/*
	 * index a object using anotation
	 * return a map from field name to Field
	 */
	public static Map<String,Field>   Object2FieldMap(Object obj)
	{
		java.lang.reflect.Field[] fs = obj.getClass().getDeclaredFields();
		Document doc = new Document();
		Map<String,Field> mf = new HashMap<String,Field>();
		for(java.lang.reflect.Field f:fs)
		{
			f.setAccessible(true);
			ObjectToField tmp = f.getAnnotation(ObjectToField.class);
			if(tmp == null) continue;
			
			//System.out.println(tmp.fieldName() +"  " +tmp.type());
			Field field = null;
			String fieldName = null;
			try
			{
				Object value = f.get(obj);
				String type = tmp.type();
				fieldName = tmp.fieldName();
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
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
			
			mf.put(fieldName, field);
		}
		return mf;
		
	}
	
	
}
