package org.weibo.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class WeiboUtil {
	
	static Logger logger = Logger.getLogger(WeiboUtil.class);
	
	/**
	 * use md5 to encode a String
	 * @param url
	 * @return
	 */
	public static String encode(String url)
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
	 *  load keywords from file.
	 */
	static private String keywords_file  = "src/main/resources/keywords.json";
	static public List<KeywordInfo> loadKeyWords()
	{
		Gson gson = new Gson();
		try {
			Reader reader = new BufferedReader((new InputStreamReader(new FileInputStream(keywords_file),"UTF-8")));
			List<KeywordInfo> keywords = (List<KeywordInfo>)gson.fromJson(reader,new com.google.gson.reflect.TypeToken<List<KeywordInfo>>() {}.getType());
			logger.info("load keywords:"+keywords);
			return keywords;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("load keywords error :"+e.toString());
		} 
		return null;
	}
	
	
	static public String getfileName(String keyword,Date date,int type)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String t = "Sina";
		if(type == Constants.TX)
			t = "TX";
		String folder = "result/"+keyword+"/"+t+"/";
		File f = new File(folder);
		if(!f.exists())
			f.mkdirs();
		return folder+sdf.format(date);
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

    public static  void main(String [] args)
    {
    	String file = getfileName("北航", new Date(), 0);
    	System.out.println(file);
    }
}
