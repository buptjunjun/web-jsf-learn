package org.easyGoingCrawler.save2remote;

import org.easyGoingCrawler.DAO.EGDAOMongo;
import org.easyGoingCrawler.docWriter.Blog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Save2Remote
{
	
	private ApplicationContext appcontext = new ClassPathXmlApplicationContext("springcofigure.xml");
	private EGDAOMongo mongo = (EGDAOMongo) appcontext.getBean("rEGDAOMongo");
	
	public void indexAndSave(Blog blog)
	{
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
