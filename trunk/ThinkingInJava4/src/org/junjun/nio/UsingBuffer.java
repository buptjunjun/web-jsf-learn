package org.junjun.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class UsingBuffer
{

	public static void  symmetricScramble(CharBuffer buffer)
	{
		while(buffer.hasRemaining())
		{
			//Sets this buffer's mark at its position. 
			buffer.mark();
			char c1 = buffer.get();
			char c2 = buffer.get();
			System.out.println(c1 +" " + c2);
			//Resets this buffer's position to the previously-marked position. 
			buffer.reset();
			buffer.put(c2).put(c1);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		char[] data = "UsingBuffers".toCharArray();
		ByteBuffer bb = ByteBuffer.allocate(32);
		bb.limit(data.length*2);
		
		System.out.println(bb);
		CharBuffer cb = bb.asCharBuffer();
		cb.put(data);
		//Rewinds this buffer. The position is set to zero and the mark is discarded
		System.out.println(cb.rewind());
		symmetricScramble(cb);	
		
		System.out.println(bb);
		System.out.println(cb.rewind());
		
		symmetricScramble(cb);
		System.out.println(cb.rewind());

	}

}
