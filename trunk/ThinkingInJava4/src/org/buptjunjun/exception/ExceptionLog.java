package org.buptjunjun.exception;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.*;
/**
 * 在exception 中使用log机制
 * @author junjun
 *
 */

class LoggingException extends Exception
{
	//生成一个logger
	private static Logger logger = Logger.getLogger("LoggingException");
	
	//将一个文件与logger 关联
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
		// 使用StringWriter来获取printStackTrace的信息
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
