package org.junjun.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import java.io.*;

public class NIOReadLine
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		oldIOReadLine();
		
	}

	
	public void nioreadLine()
	{
		try
		{	
			Charset charset = Charset.defaultCharset();
			FileChannel fc = new FileInputStream("d://test.txt").getChannel();
			ByteBuffer bf = ByteBuffer.allocate(1024);
			StringBuffer sb = new StringBuffer();
			
			while(fc.read(bf) != -1)
			{
				bf.flip();
				CharBuffer cb = bf.asCharBuffer();
				charset.encode(cb);
				sb.append(cb.toString());
				bf.clear();
				
				System.out.println(sb.toString());
				int end = sb.indexOf("\n");
				
				if(end == -1)
					continue;
				
				String line = sb.substring(0, end);
				String remains = sb.substring(end+1, sb.length());
				sb.replace(0, sb.length(),remains);
				System.out.println(line);
			}
			
			
			int end = sb.indexOf("\n");
		    while(end != -1)
		    {
				String line = sb.substring(0, end);
				String remains = sb.substring(end+1, sb.length());
				sb.replace(0, sb.length(),remains);
				System.out.println(line);
		    }
			

				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void oldIOReadLine()
	{
		String url = "d:\\test.txt";
		int endOfTime = 20;
		long upper = 0;
		long lower = Long.MAX_VALUE;
		List<Long> list = new LinkedList<Long>();
		SimpleDateFormat  formater = new SimpleDateFormat ("MM-dd");
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(url)));
			
			String line = null;
			while ( (line = reader.readLine()) != null)
			{
				System.out.println(line);
				String time = line.substring(0, endOfTime);
				
				long date = Date.parse(time);
				
				if (date <lower)
					lower = date;
				if (date > upper)
					upper = date;
				
			}
			
			
			Calendar d = Calendar.getInstance();
			Date pre = new Date(d.getTimeInMillis());	
			Date cur = new Date(d.getTimeInMillis());
			
			boolean flag = true;
			for(int i = 0; i < 5 && flag; i++)
			{						
				d.add(Calendar.DAY_OF_YEAR, -7);
				pre = cur;
				cur = new Date(d.getTimeInMillis());
				
				if( d.getTimeInMillis() < lower)
				{
					cur = new Date(lower);
					flag = false;
				}
				
				String timespan = "";
				if(i == 0)
					timespan = formater.format(cur)+" - "+ "present";
				else
					timespan = formater.format(cur)+" - "+ formater.format(pre);
				
				System.out.println(timespan);
			}
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}