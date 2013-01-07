package org.junjun.io;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class testReadCMD
{
	
	public static void main(String[] args) 
	{
	  InputStreamReader isr = new InputStreamReader(System.in);
	  
	  BufferedReader br = new BufferedReader(isr);
	  String s = null;
	  try {
	    s = br.readLine();
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	  
	  System.out.println("Hello: " + s);
	  
	  
	 }

}
