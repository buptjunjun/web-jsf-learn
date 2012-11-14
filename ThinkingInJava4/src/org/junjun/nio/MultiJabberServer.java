package org.junjun.nio;
//: c15:MultiJabberServer.java

//A server that uses multithreading 

//to handle any number of clients.

//{RunByHand}

import java.io.*;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class MultiJabberServer {  

static final int PORT = 8080;

public static void main(String[] args)

   throws IOException
   {
	
	String encoding =System.getProperty("file.encoding");
	
	Charset cs = Charset.forName(encoding);
	ByteBuffer buffer = ByteBuffer.allocate(16);
	
	SocketChannel ch = null;
	ServerSocketChannel ssc = ServerSocketChannel.open();
	Selector sel = Selector.open();
	
	try
	{
		ssc.configureBlocking(false);
		
		ssc.socket().bind(new InetSocketAddress(PORT));
		
		SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);
		
		System.out.println("Server on port : " + PORT);
		
		while(true)
		{
			sel.select();
			Iterator i = sel.selectedKeys().iterator();
			while(i.hasNext())
			{
				SelectionKey skey = (SelectionKey) i.next();
				System.out.println(skey.toString());
				
				//Tests whether this key's channel is ready to accept a new socket connection
				if (skey.isAcceptable())
				{
					ch = ssc.accept();
					System.out.println("accept connectionf rom :"+ ch.socket());
					ch.configureBlocking(false);
					
					ch.register(sel, SelectionKey.OP_READ);
				}
				else//if (skey.isReadable() || skey.isWritable()) 
				{
					 ch = (SocketChannel) skey.channel();
					 ch.read(buffer);
					 
					 CharBuffer cb= cs.decode((ByteBuffer)buffer.flip());
					 String response = cb.toString();
					 System.out.println(response);
					 
					 ch.write((ByteBuffer)buffer.rewind());
					 
					 if( response.equalsIgnoreCase("end"))
						 break;
					 buffer.clear();
				}
			}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
   
   }
 

} ///:~
