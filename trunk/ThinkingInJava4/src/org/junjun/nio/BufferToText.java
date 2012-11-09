package org.junjun.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

public class BufferToText
{
	public static final int BSIZE = 1024;
	
	public static void availableCharset()
	{
		SortedMap<String,Charset> charSets =
				Charset.availableCharsets();
				Iterator<String> it = charSets.keySet().iterator();
				while(it.hasNext()) {
				String csName = it.next();
				System.out.print(csName);
				Iterator aliases =
				charSets.get(csName).aliases().iterator();
				if(aliases.hasNext())
				System.out.print(": ");
				while(aliases.hasNext()) {
				System.out.print(aliases.next());
				if(aliases.hasNext())
				System.out.print(", ");
				}
				}	
	}
	
	public static void main(String [] args)
	{
		String fileName = "testchannel.txt";
		FileChannel fc = null;
		try
		{
			// read the file 
			fc = new FileInputStream(fileName).getChannel();
			ByteBuffer bf = ByteBuffer.allocate(BSIZE);
			fc.read(bf);
			
			//set the limit to the current position and set the position to 0
			bf.flip();
			
			// this will not work
			System.out.println("1: " + bf.asCharBuffer());
			
			// decode using the default charset of this system
			bf.rewind();
			String encoding = System.getProperty("file.encoding");
			System.out.println("2: decoded using " + encoding +": " + Charset.forName(encoding).decode(bf));
			
			
			//we encode something that will print
			fc = new FileOutputStream("data.txt").getChannel();
			fc.write(ByteBuffer.wrap("some text".getBytes("gbk")));
			fc.close();
			
			bf.clear();
			fc = new FileInputStream("data.txt").getChannel();
			fc.read(bf);
			bf.flip();
			System.out.println("3: " + bf.asCharBuffer());
			fc.close();
			
			
			// use a ByteBuffer to write through
			bf.clear();
			fc = new FileOutputStream("data.txt").getChannel();
			bf.asCharBuffer().put("some text ÄãºÃ");
			fc.write(bf);
			fc.close();
			
			bf.clear();
			fc = new FileInputStream("data.txt").getChannel();
			fc.read(bf);
			bf.flip();
			System.out.println("4: " + bf.asCharBuffer());
			fc.close();
			
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		availableCharset();
	}
}
