package com.websense.stuff;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
public class IPv6
{


public static  boolean compareIPs(String ip1, String ip2)
{
	if(ip1!=null && ip2!=null)
	{
		ip1 = ip1.trim();
		ip2 = ip2.trim();
		try	
		{
			InetAddress inet1 = InetAddress.getByName(ip1);
			InetAddress inet2 = InetAddress.getByName(ip2);
			return inet1.equals(inet2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	return false;
}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		System.out.println(compareIPs("2001:da8:9000:B255:200:0000:0000:5c5e","2001:da8:9000:b255:200:0:0:5c5e"));
		System.out.println(compareIPs("2001:da8:9000:b255:200:0:0:5c5e","124.0.0.1 "));
		

	}

}
