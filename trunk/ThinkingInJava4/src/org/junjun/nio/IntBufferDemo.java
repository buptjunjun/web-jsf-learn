package org.junjun.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class IntBufferDemo
{

	static public final int BSIZE= 1024;
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ByteBuffer bf = ByteBuffer.allocate(BSIZE);
		IntBuffer ib = bf.asIntBuffer();
		
		ib.put(new int [] {1,2,3,4,5,6,7,8});
		
		// absolute location read and write
		System.out.println(ib.get(3));
		
		System.out.println(ib);
		ib.put(3, 111);
		ib.flip();
		System.out.println(ib);
		
		while(ib.hasRemaining())
		{
			System.out.println(ib.get());
			System.out.println(ib);
		}
		
	}

}
