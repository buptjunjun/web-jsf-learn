package org.junjun.controller.logic;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.datanucleus.util.StringUtils;




public class GAEJPA<T>
{
	public  final static int ASCENDING = 0;
	public  final static int DESCENDING = 1;
	
	public void insert(Object obj)
	{
		
		EntityManager em = EMF.get().createEntityManager();
		try
		{
		
			em.persist(obj);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			em.close();
		}
		
	}
	

	/**
	 * update  records which meets the constrains of "constrains"
	 * constrains is a map like : [id:123,"name":"abcd"]
	 * @param obj
	 * @param constrains
	 */
	public void update(T obj,Map<String,Object> constrains)
	{
		if(obj == null) 
			return;
		Class cls = obj.getClass();
		Field [] fields = cls.getDeclaredFields();
		Set<String> fieldset = new HashSet<String>();
		for(Field field: fields)
		{
			fieldset.add(field.getName());
		}
		
		this.update(obj, null, null, constrains, fieldset);
	}
	
	/**
	 * update  records which meets the constrains of "constrains"
	 *  constrains is a map like : {"value":100} indicates value =100  
	 * constrainsGT is constrain greater than, for example, {"value":100} indicates value >=100  
	 *  constrainsGT is constrain less than,or example, {"value":100} indicates value < 100
	 *    
	 * @param obj
	 * @param constrains
	 *  @param constrainsLT
	 *   @param constrainsGT
	 * @param fieldNames which fields you want to update
	 */
	public void update(T obj,Map<String ,Object> constrainsLT,Map<String ,Object> constrainsGT,Map<String, Object> constrains ,Set<String> fieldNames)
	{
		Class cls = obj.getClass();		
		EntityManager em = EMF.get().createEntityManager();
		try
		{
		
			Field idfieled = cls.getDeclaredField("id");
			idfieled.setAccessible(true);
			
			Object id = (Object)idfieled.get(obj);
			
			
			Object item = em.find(obj.getClass(), id);
			
			// if there is no item insert obj;
			if(item == null)
			{
				em.persist(obj);
				return;
			}
			
			Field [] fields = cls.getDeclaredFields();
			for(Field field : fields)
			{
				String fieldName = field.getName();
				if(!fieldNames.contains(fieldName) || fieldName.startsWith("jdo"))
					continue;
				try
				{
					field.setAccessible(true);
					field.set(item, field.get(obj));
					
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
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			em.close();
		}
	}



	
	/**
	 * search records which meets the constrains of "constrains"
	 * constrains is a map like : {"value":100} indicates value =100  
	 * constrainsGT is constrain greater than, for example, {"value":100} indicates value >=100  
	 *  constrainsGT is constrain less than,or example, {"value":100} indicates value < 100  
	 * @param <T>
	 * @param constrains
	 * @param constrainsGT
	 * 	@param constrainsLT
	 * @param limit
	 * @param cls
	 * @return
	 */
	public <T extends Object>  List<T> search (Map<String ,Object> constrainsLT,Map<String ,Object> constrainsGT,Map<String ,Object> constrains , String sortFiled,int sortWay, int limit, Class cls)
	{	
		String tablename = cls.getSimpleName();
		
		String jsql = "select item from "+tablename+"  item ";	
		int flag = 0;
		HashMap<String,Object> name2type = new HashMap<String,Object>();
		
		if(constrains !=null)
		{
			for(Map.Entry entry:constrains.entrySet())
			{
				
				String key = (String)entry.getKey();
				Object value = entry.getValue();
				
				if(flag == 0)
				{
					jsql+= " where item."+key +"=:"+key;
					flag = 1;
				}
				else 
				{
					jsql+= "and  item."+key +"=:"+key;
				}
					
					
				name2type.put(key, value);			
			}
		}
		
		// gt
		if(constrainsGT !=null)
		{
			for(Map.Entry entry:constrainsGT.entrySet())
			{
				String key = (String)entry.getKey();
				Object value = entry.getValue();
				
				
				if(flag == 0)
				{
					jsql+= " item."+key +"=:"+key;
					flag = 1;
				}
				else 
				{
					jsql+= "and  item."+key +"=:"+key;
				}
				name2type.put(key, value);		
			
			}
		}
		
		// LT
		if(constrainsLT !=null)
		{
			for(Map.Entry entry:constrainsLT.entrySet())
			{
				String key = (String)entry.getKey();
				Object value = entry.getValue();
				
				
				if(flag == 0)
				{
					jsql+= " item."+key +"=:"+key;
					flag = 1;
				}
				else 
				{
					jsql+= "and  item."+key +"=:"+key;
				}
				
				name2type.put(key, value);		
			}
					
		}
		
		// order
		if(sortFiled!=null &&( sortWay == ASCENDING | sortWay == DESCENDING))
		{
			String order =  " order by item."+sortFiled;
			if(sortWay == DESCENDING)
			{
				order += " desc ";
			}
			else
			{
				order += " asc  ";
			}
			jsql += order;	
		}
		
		// limit 
		
		
		
		jsql = jsql.trim();
		System.out.println("jsql:"+jsql);
		EntityManager em = EMF.get().createEntityManager();
		try
		{
		
			TypedQuery query = em.createNamedQuery(jsql, cls);
			for(Map.Entry entry:name2type.entrySet())
			{
				String key = (String) entry.getKey();
				Object value =  entry.getValue();
				
				if(value.getClass().getName().contains("Date"))
				{
					query.setParameter(key, (Date)value, TemporalType.DATE);
				}
				else
				{
					query.setParameter(key, value);
				}
				
			}
			
			if(limit > 0)
			{
				query.setFirstResult(0).setMaxResults(limit);
			}
			
			List ret = query.getResultList();
			return ret;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			em.close();
		}
		
        return null;
         
	}
	
	
	/**
	 * search records which meets the constrains of "constrains"
	 * constrains is a map like : {"value":100} indicates value =100  
	 * constrainsGT is constrain greater than, for example, {"value":100} indicates value >=100  
	 *  constrainsGT is constrain less than,or example, {"value":100} indicates value < 100  
	 * @param constrains
	 * @param constrainsGT
	 * 	@param constrainsLT
	 * @param cls
	 * @return
	 */
	 private String  getQueryJPQL (Map<String ,Object> constrainsLT, Map<String ,Object> constrainsGT,Map<String ,Object> constrains , String sortFiled,int sortWay, Class cls)
	 {
		String tablename = cls.getName();
		
		String jsql = "";
		int flag = 0;
		HashMap<String,String> name2type = new HashMap<String,String>();
		
		if(constrains !=null)
		{
			for(Map.Entry entry:constrains.entrySet())
			{
				
				String key = (String)entry.getKey();
				Object value = entry.getValue();
				
				if(StringUtils.isEmpty(jsql))
					jsql = "select item from "+tablename+" where item  ";	
				if(flag == 0)
				{
					jsql+= " item."+key +"=:"+key;
					flag = 1;
				}
				else 
				{
					jsql+= "and  item."+key +"=:"+key;
				}
					
					
				name2type.put(key, value.getClass().getName());				
			}
		}
		
		// gt
		if(constrainsGT !=null)
		{
			for(Map.Entry entry:constrainsGT.entrySet())
			{
				String key = (String)entry.getKey();
				Object value = entry.getValue();
				
				if(StringUtils.isEmpty(jsql))
					jsql = "select item from "+tablename+" where item ";	
				
				if(flag == 0)
				{
					jsql+= " item."+key +"=:"+key;
					flag = 1;
				}
				else 
				{
					jsql+= "and  item."+key +"=:"+key;
				}
				name2type.put(key, value.getClass().getName());				
			
			}
		}
		
		// LT
		if(constrainsLT !=null)
		{
			for(Map.Entry entry:constrainsLT.entrySet())
			{
				String key = (String)entry.getKey();
				Object value = entry.getValue();
				
				if(StringUtils.isEmpty(jsql))
					jsql = "select item from "+tablename+" where item ";	
				
				if(flag == 0)
				{
					jsql+= " item."+key +"=:"+key;
					flag = 1;
				}
				else 
				{
					jsql+= "and  item."+key +"=:"+key;
				}
				
				name2type.put(key, value.getClass().getName());		
			}
					
		}
		
		// order
		if(sortFiled!=null || sortWay == ASCENDING | sortWay == DESCENDING)
		{
			String order =  " order by item."+sortFiled;
			if(sortWay == DESCENDING)
			{
				order += " desc ";
			}
			else
			{
				order += " asc  ";
			}
			jsql += order;	
		}
		
		// limit 
		
		
		
		jsql = jsql.trim();
		return jsql;
	}
	public static void main(String [] args)
	{
		
		EntityManager em = EMF.get().createEntityManager();
		Test test = new Test();
		em.persist(test);
		em.close();
	}
}
