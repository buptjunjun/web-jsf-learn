package org.junjun.nio;

import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.io.*;

/**
 *  copy file from one place to another,using NIO
 * @author andyWebsense
 *
 */
public class ChannelCopy
{
	public static void copyFile1()
	{
		String filename1 = "testchannel.txt";
		String filename2 = "testchannelCopy.txt";
		try
		{
			FileChannel in = new FileInputStream(filename1).getChannel();
			FileChannel out = new FileOutputStream(filename2).getChannel();
			//ByteBuffer buffer = ByteBuffer.allocate(1024);
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			
			while( in.read(buffer) != -1)
			{
				// set the limit to the current position, and set the position to 0
				buffer.flip();
				
				out.write(buffer);
				
				// set the limit to the capacity and set the position to 0
				buffer.clear();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	static public void channelConnect()
	{
		String filename1 = "testchannel.txt";
		String filename2 = "testchannelCopy1.txt";
		try
		{
			FileChannel in = new FileInputStream(filename1).getChannel();
			FileChannel out = new FileOutputStream(filename2).getChannel();
		
			in.transferTo(0, in.size(), out);
			// orout.transferFrom(in, 0, in.size());
						
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public static void main(String args[])
	{
		channelConnect();
		
	}

}
