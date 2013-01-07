package com.websense.stuff;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
public class ReadCAMTimeOptions
{

	public List<String> showTimeRotareCAM(String path)
	{
		List<String> ret = new ArrayList<String>();
		
		try
		{
			BufferedReader f = new BufferedReader(new FileReader(path));
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
		return ret;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
