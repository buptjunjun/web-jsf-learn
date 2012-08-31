package com.buptjunjun.stuff;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileSize {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File f = new File("D:\\work\\software\\eclipse\\eclipse-jee-indigo-win32.zip");
		int totalsize = 0;
		try {	
			FileInputStream fi = new FileInputStream(f);
			totalsize = fi.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(f.getName() + " total size = " + totalsize/(1024*1024));
		
		
	}

}
