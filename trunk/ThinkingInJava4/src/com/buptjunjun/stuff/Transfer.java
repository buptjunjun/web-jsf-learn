package com.buptjunjun.stuff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Transfer
{
	public static void main(String [] agrs)
	{
		transfer();
	}
	static  public void transfer()
		{
			String fileName = "C:/Users/andyWebsense/Desktop/name1.txt";
			BufferedReader  bf=null;
			try 
			{
				bf = new BufferedReader( new FileReader(fileName));
				
				String line = null;
				while(( line = bf.readLine() ) != null)
				{
					line = line.trim();
					line = "public static " + line;
					line = line.replace("null", "");
					System.out.println(line);
				}
				
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				if(bf != null)
					try {
						bf.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			}
		
		}
}
