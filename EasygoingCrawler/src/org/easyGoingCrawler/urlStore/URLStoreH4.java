package org.easyGoingCrawler.urlStore;

import java.io.File;
import java.util.List;

import org.hibernate.Query;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * 
 * use hibernate4 to save url info mations
 * @author Andy  weibobee@gmail.com 2012-9-22
 *
 */
public class URLStoreH4 
{
	private SessionFactory  sf = null;
	public URLStoreH4()
	{
		 	Configuration cfg = new Configuration();  		 	
	        cfg.configure(new File("conf/hibernate.cfg.xml"));
	        ServiceRegistry  sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();   
	        sf = cfg.buildSessionFactory(sr);    
	}
	
	/**
	 * save or update one ser
	 * @param u
	 * @return
	 */
	public boolean saveOrUpdate(CrawlURI u )
	{
		Session s = null;
		try
		{
		  	s = sf.openSession();  
	        Transaction tx = s.beginTransaction();  
	        s.saveOrUpdate(u);
	        s.flush();
	        tx.commit();  
	        //System.out.println();
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
	
	public List query(String queryString)
	{
		Session s = null;
		try
		{
			 s = sf.openSession();  
			 Query query = s.createQuery(queryString);
			 query.setMaxResults(10);
			 List ret = query.list();
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
	
	public boolean queryIfExist(String url)
	{
		if (url == null)
			return false;
		
		Session s = null;
		try
		{
			 String querystr = " from CrawlURI where url = '" + url+"'" ;
			 s = sf.openSession();  
			 Query query = s.createQuery(querystr);
			 List l = query.list();
			 if (l == null || l.size() == 0)
				 return false;
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
	
	/**
	 * 
	 * @param args
	 */
	
	public boolean save(CrawlURI curl)
	{
		Session s = null;
		try
		{
		  	s = sf.openSession();  
	        Transaction tx = s.beginTransaction();  
	        s.save(curl);
	        s.flush();
	        tx.commit();  
	        //System.out.println();
	        return true;
		}
		catch(Exception e)
		{
			//e.printStackTrace();
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
				//e.printStackTrace();
			}
		}
		
		return false;
	}
	public static void main(String [] args)
	{
		CrawlURI curl = new CrawlURI("www.baidu.com");
        curl.setEncode("utf-8");  
		
        URLStoreH4 urlstore = new URLStoreH4();
        urlstore.queryIfExist("http://1615459.blog.51cto.com/1605459/949621");
      
        //urlstore.saveOrUpdate(curl);
//        List l = urlstore.query("from CrawlURI ");
//        
//        System.out.println(l.size());
	}
}
