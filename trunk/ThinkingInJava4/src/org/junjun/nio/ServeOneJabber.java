package org.junjun.nio;

import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.io.*;

public class ServeOneJabber extends Thread
{
	Charset cs =  Charset.forName(System.getProperty("file.encoding"));
	private SocketChannel channel = null;
	private Selector sel;
	
	public ServeOneJabber(SocketChannel ch) throws IOException
	{
		this.channel = ch;
		channel.configureBlocking(false);
		sel =  Selector.open();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void run()
	{
		ByteBuffer buffer = ByteBuffer.allocate(16);
		String response = "";
		try
		{
			this.channel.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		
		
			boolean done = false;
			boolean read = false;
			while(!done)
			{
				sel.select();
				//System.out.println("---");
				Iterator it = sel.selectedKeys().iterator();
				while(it.hasNext())
				{
					
					SelectionKey key = (SelectionKey)it.next();
					it.remove();
					
					if(key.isReadable() && !read)
					{
						if (this.channel.read(buffer) > 0)
							read = true;
						else read = false;
							
						CharBuffer cb = cs.decode((ByteBuffer)buffer.flip());
						response = cb.toString();
						
					}
					
					if(key.isWritable() && read)
					{
						System.out.println("encoding :" + response);
						this.channel.write((ByteBuffer)buffer.rewind());
					
						if (response.equalsIgnoreCase("end")) done  = true;
						
						buffer.clear();
						read = false;
					}
					
				}
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try
			{
				this.channel.close();
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
	}

}
