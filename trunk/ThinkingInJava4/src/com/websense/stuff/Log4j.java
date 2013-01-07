package com.websense.stuff;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4j
{
	
	 static Logger logger = Logger.getLogger("auditlog");
	 public static  void main(String[] args)
	 {
		 PropertyConfigurator.configure("log4j.properties");
		 for(int i = 0; i < 1000;i++)
			 logger.error(i+"  test-");
	 }

}
