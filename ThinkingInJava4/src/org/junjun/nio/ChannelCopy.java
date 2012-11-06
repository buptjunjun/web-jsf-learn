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
	public static void main(String args[])
	{

		String filename1 = "testchannel.txt";
		String filename2 = "testchannelCopy.txt";
		try
		{
			FileChannel in = new FileInputStream(filename1).getChannel();
			FileChannel out = new FileOutputStream(filename2).getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
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

}
