package org.junjun.controller.logic;

import java.security.MessageDigest;

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
}
