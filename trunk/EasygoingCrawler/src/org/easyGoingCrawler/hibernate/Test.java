package org.easyGoingCrawler.hibernate;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.urlStore.URLStoreH4;
import org.easyGoingCrawler.util.EGLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.apache.log4j.PropertyConfigurator;;

public class Test 
{

	static
	{
		PropertyConfigurator.configure("conf/log4j.properties");
	}
	
	static private Logger logger =  Logger.getLogger(Test.class);
	public static void main(String[] args) {  
		CrawlURI curl = new CrawlURI("http://www.iteye.com/forums/tag_hot/Ajax");
        curl.setEncode("utf-8");  
		curl.setHttpstatus(200);
        URLStoreH4 urlstore = new URLStoreH4();
        
      
        urlstore.saveOrUpdate(curl);
       List l = urlstore.query("from CrawlURI limit 3");
        
        //System.out.println( ((CrawlURI)l.get(0)).getLastCrawlDate());


    }  

}
