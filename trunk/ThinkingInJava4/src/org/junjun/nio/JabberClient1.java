package org.junjun.nio;

import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.*;
import java.nio.charset.*;
import java.util.Iterator;


public class JabberClient1
{

	static public final int clPort = 10015;
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		System.out.println("------");
		try
		{
			SocketChannel sc = SocketChannel.open(new InetSocketAddress(clPort));
			Selector sel = Selector.open();
			sc.configureBlocking(false);
			
			sc.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT);
			
			
			int i = 0;
			boolean written = false, done = false;
			String encoding = System.getProperty("file.encoding");
			Charset cs = Charset.forName(encoding);
			
			ByteBuffer buf = ByteBuffer.allocate(16);
			System.out.println("------");
			while (!done)
			{	System.out.println("------");
				sel.select();
				System.out.println("------");
				System.out.println("select");
				Iterator it = sel.selectedKeys().iterator();
				while(it.hasNext())
				{
					SelectionKey key = (SelectionKey)it.next();
					it.remove();
					sc  = (SocketChannel)key.channel();
					
					if (key.isConnectable() && ! sc.isConnected())
					{
						System.out.print("conn");
						InetAddress addr = InetAddress.getByName(null);
						boolean success  = sc.connect( new InetSocketAddress(addr,MultiJabberServer.PORT));
						
						if(!success ) sc.finishConnect();
						
					}
					if (key.isReadable() && written)
					{
						System.out.print("read");
						if (sc.read((ByteBuffer)buf.clear()) > 0)
							written = false;
						String response  = cs.decode((ByteBuffer)buf.flip()).toString();
						System.out.println(response);
						
					}
					
					if (key.isWritable() && !written)
					{
						System.out.print("write");
					 InputStreamReader isr = new InputStreamReader(System.in);
					  
					  BufferedReader br = new BufferedReader(isr);
					  String s = null;
					  try {
						 System.out.println("input:");
					    s = br.readLine();
					    System.out.println("tap:"+s);
					    sc.write(ByteBuffer.wrap(s.getBytes()));
					  } catch (IOException e) {
					   // TODO Auto-generated catch block
					   e.printStackTrace();					   
					  }
					  finally
					  {
						  written = true;
					  }

					}
					
				}
			}
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
