package com.buptjunjun.stuff;

import java.io.File;
import java.io.IOException;

public class CreateFile
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub
		File f = new File("/opt/appliance/local/manager/CAMAudit.log");
		if(!f.exists())
			f.createNewFile();
		System.out.println(f.getAbsolutePath());
	}

}
