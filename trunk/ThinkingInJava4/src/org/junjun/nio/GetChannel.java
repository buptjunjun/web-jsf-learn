package org.junjun.nio;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;;

public class GetChannel
{
	public static final int BSIZE = 1024;
	public static void main(String [] args)
	{
		String fileName = "testchannel.txt";
		FileChannel fc = null;
		try
		{
			fc = new FileOutputStream(fileName).getChannel();
			fc.write(ByteBuffer.wrap("some text".getBytes()));		
			fc.close();
			
			// add to the end of file
			fc = new RandomAccessFile(fileName,"rw").getChannel();
			fc.position(fc.size());
			fc.write(ByteBuffer.wrap("\n some more".getBytes()));
			fc.close();
			
			// read the file 
			fc = new FileInputStream(fileName).getChannel();
			ByteBuffer bf = ByteBuffer.allocate(BSIZE);
			fc.read(bf);
			
			//set the limit to the current position and set the position to 0
			bf.flip();
			// get will increase the position pointer
			while(bf.hasRemaining())
			{
				System.out.print((char)bf.get());
			}
			
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
		
		
	}

}
