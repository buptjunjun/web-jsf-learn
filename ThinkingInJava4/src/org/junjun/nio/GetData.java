package org.junjun.nio;

import java.nio.ByteBuffer;

public class GetData
{
	private static final int BSIZE = 1024;
	public static void main(String [] args)
	{
		ByteBuffer bf = ByteBuffer.allocate(BSIZE);
		System.out.println(bf.toString());
		
		int i = 0; 
		while(i++ < bf.limit())
		{
			if(bf.get() != 0)
				System.out.print("none zero,");
		}
		
		System.out.println("i = " + i);
		
		bf.rewind();
		
		//store and read a short
		bf.asShortBuffer().put((short)23);
		bf.limit(2);
		System.out.println(bf.toString());
		
		System.out.println(bf.getShort());
		
		//store and read a float
		bf.limit(8);
		bf.asFloatBuffer().put(1.12347f);
		System.out.println(bf.getFloat());
		
	}
}
