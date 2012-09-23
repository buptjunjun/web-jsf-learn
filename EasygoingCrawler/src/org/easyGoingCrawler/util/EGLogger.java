package org.easyGoingCrawler.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.SimpleLayout;


/**
 * the log class for crawler
 * @author andy
 *
 */

public class EGLogger {

	// file name to load
	private static String file = Localizer.getMessage("AUDIT_LOG_FILE_PATH"); 
	private static String MaxBackupIndex =  Localizer.getMessage("MAX_BACKUP_INDEX");
	private static String MaxFileSize = Localizer.getMessage("MAX_FILE_SIZE"); 
	
	private static RollingFileAppender rollingAppender   = null;
	static 
	{
		try
		{
			int maxBackupIndexInt = Integer.parseInt(MaxBackupIndex);
			
			rollingAppender   = new RollingFileAppender ();	
	    	rollingAppender.setFile(file);   	
	    	rollingAppender.setMaxBackupIndex(maxBackupIndexInt);
	    	rollingAppender.setMaxFileSize(MaxFileSize);
	    	rollingAppender.setImmediateFlush(true);
	    	rollingAppender.setAppend(true);
	    	rollingAppender.activateOptions();
	    	rollingAppender.setLayout(new PatternLayout());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private String name = null;
	
	public static Logger getLogger( Class clas) 
	{		
		//this.name = clas.getName();
    	// get a random logger
    	Logger logger = Logger.getLogger(clas.getName());
    	logger.addAppender(rollingAppender);
    	return logger;
	}
	
}
