package org.buptjunjun.exception;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.*;
/**
 * ��exception ��ʹ��log����
 * @author junjun
 *
 */

class LoggingException extends Exception
{
	//����һ��logger
	private static Logger logger = Logger.getLogger("LoggingException");
	
	//��һ���ļ���logger ����
	static
	{
		try {
			logger.addHandler(new FileHandler("loggingException.logs",true));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public LoggingException()
	{
		// ʹ��StringWriter����ȡprintStackTrace����Ϣ
		StringWriter trace = new StringWriter();
		this.printStackTrace(new PrintWriter(trace));
		logger.severe(trace.toString());
	}
}
public class ExceptionLog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			throw new LoggingException();
		}
		catch(LoggingException le)
		{
			System.err.println("Caught " + le);
		}
		
		try
		{
			throw new LoggingException();
		}
		catch(LoggingException le)
		{
			System.err.println("Caught " + le);
		}
	}

}
