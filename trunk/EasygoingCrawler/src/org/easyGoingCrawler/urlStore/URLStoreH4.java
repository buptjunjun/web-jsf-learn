package org.easyGoingCrawler.urlStore;

import java.util.List;

import org.hibernate.Query;
import org.easyGoingCrawler.hibernate.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * 
 * use hibernate 4 to save url info mations
 * @author Andy  weibobee@gmail.com 2012-9-22
 *
 */
public class URLStoreH4 
{
	private SessionFactory  sf = null;
	public URLStoreH4()
	{
		 	Configuration cfg = new Configuration();  
		 	
	        cfg.configure();  
	        ServiceRegistry  sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();   
	        sf = cfg.buildSessionFactory(sr);    
	}
	
	
	public boolean saveOrUpdate(User u )
	{
		Session s = null;
		try
		{
		  	s = sf.openSession();  
	        Transaction tx = s.beginTransaction();  
	        s.saveOrUpdate(u);  
	        tx.commit();  
	        
	        return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
			if(s != null)
				s.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public List query()
	{
		Session s = null;
		try
		{
			 s = sf.openSession();  
			 Query query = s.createQuery ("from User");
			 List ret = query.list();
			 s.close();
			 return ret;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
			if(s != null)
				s.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return null;
		 
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if(this.sf!=null)
		{
			this.sf.close();
		}
		System.out.println("reallocated");
	}
}
