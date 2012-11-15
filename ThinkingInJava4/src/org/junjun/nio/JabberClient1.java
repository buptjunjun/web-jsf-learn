package org.junjun.nio;

import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.io.*;
import java.nio.charset.*;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


public class JabberClient1
{

	static public final int clPort = 10015;
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		try
		{
			SocketChannel sc = SocketChannel.open();
			Selector sel = Selector.open();
			
			sc.configureBlocking(false);
			sc.connect( new InetSocketAddress("localhost",MultiJabberServer.PORT));
			sc.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT);
			
			
			int i = 0;
			boolean written = false, done = false;
			String encoding = System.getProperty("file.encoding");
			Charset cs = Charset.forName(encoding);
			
			ByteBuffer buf = ByteBuffer.allocate(16);
		
			while (!done)
			{	
				//System.out.println("before select");
				sel.select();
				
				Iterator it = sel.selectedKeys().iterator();
				while(it.hasNext())
				{
					SelectionKey key = (SelectionKey)it.next();
					
							   
					it.remove();
					
					printKey(key);
					sc  = (SocketChannel)key.channel();
					
					if (key.isConnectable())
					{
						System.out.println("conn1");
					
						 if(sc.isConnectionPending())
						 {
							 System.out.println("pending");
		                        sc.finishConnect();   
		                           
		                    }   
						 sc.configureBlocking(false);
						
					}
				/*	else if (key.isConnectable() && !sc.isConnected())
					{
						System.out.print("conn2");
						boolean success = sc.connect( new InetSocketAddress("localhost",MultiJabberServer.PORT));
						if(success == false)
							sc.finishConnect();
						sc.configureBlocking(false);
					}
						*/
					if (key.isReadable() && written)
					{
						//TimeUnit.SECONDS.sleep(1);
						System.out.println("read:");
						if (sc.read((ByteBuffer)buf.clear()) > 0)
							written = false;
						String response  = cs.decode((ByteBuffer)buf.flip()).toString();
						System.out.println(response);
						
					}
					
					if (key.isWritable() && !written)
					{
						TimeUnit.SECONDS.sleep(1);
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
			
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void printKey(SelectionKey key)
	{
		System.out.println("skey.isAcceptable() = " + key.isAcceptable() +"\n"
				+ "key.isConnectable() = " + key.isConnectable() +"\n"
				+ "key.isValid() = " + key.isValid() +"\n"
				+ " key.isReadable() = " + key.isReadable() +"\n"
				+ "key.isWritable()="   + key.isWritable());
	}

}
