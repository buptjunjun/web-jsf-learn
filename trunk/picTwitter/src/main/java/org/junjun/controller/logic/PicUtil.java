package org.junjun.controller.logic;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;

public class PicUtil 
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
	 * get the date whic is count day before "date",if count < 0 it change to  "getDateAfter".
	 * @param date
	 * @param days
	 */
	public static Date getDateBefore(Date date,int count)
	{
		// weekly 
		Calendar c = Calendar.getInstance();     
        c.setTime(date);  
        int day = c.get(Calendar.DATE);  
        c.set(Calendar.DATE, day - count);  
        
        Date newdate = c.getTime();
        return newdate;
	}
}
