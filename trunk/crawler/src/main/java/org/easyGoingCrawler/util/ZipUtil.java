package org.easyGoingCrawler.util;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

public class ZipUtil
{
	// 将一个字符串按照zip方式压缩和解压
	public static  byte[] compress1(String str)
	{
		if (str == null)
			return null;

		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try
		{
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e)
		{
			compressed = null;
		} finally
		{
			if (zout != null)
			{
				try
				{
					zout.close();
				} catch (IOException e)
				{
				}
			}
			if (out != null)
			{
				try
				{
					out.close();
				} catch (IOException e)
				{
				}
			}
		}

		return compressed;
	}

	public static final String decompress(byte[] compressed)
	{
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try
		{
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			ZipEntry entry = zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1)
			{
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString("ISO-8859-1");
		} catch (IOException e)
		{
			decompressed = null;
		} finally
		{
			if (zin != null)
			{
				try
				{
					zin.close();
				} catch (IOException e)
				{
				}
			}
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
				}
			}
			if (out != null)
			{
				try
				{
					out.close();
				} catch (IOException e)
				{
				}
			}
		}

		return decompressed;
	}
	
	public static char [] compressURL(String url)
	{
			byte[] compressed = compress1(url);
			if (compressed == null)
				return null;
			char [] compressedChar = new char [compressed.length];
			for(int i = 0; i < compressed.length; i++)
			{
				byte [] toChar = {'0',compressed[i]};
				compressedChar[i] = byteToChar(toChar);
			}
			
			return compressedChar;
	}
	
	public static String decompressURL(char [] url)
	{
			if (url == null)
				return null;
			byte [] decomressedByte = new byte [url.length];
			for(int i = 0; i < url.length; i++)
			{
				byte [] b = charToByte(url[i]);
				decomressedByte[i] = b[1];
			}
			String ret = decompress(decomressedByte);
			return ret;
	}


    public static char byteToChar(byte[] b) 
    {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }
    
    public static byte[] charToByte(char c)
    {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }
    
	// 测试方法
	public static void main(String[] args) throws IOException
	{
		String url = "http://www.baidu.com/s?tn=baiduhome_pg&ie=utf-8&bs=%E9%95%BF%E8%BF%9E%E6%8E%A5&f=3&rsv_bp=1&rsv_spt=1&wd=urlencode%E7%BC%96%E7%A0%81&rsv_sug3=4&rsv_sug1=4&rsv_sug4=49&oq=urlen&rsp=1&rsv_sug2=0&inputT=2481";
		char[] compressed1 = ZipUtil.compressURL(url);
		System.out.println("starttime=" + compressed1);
		
		String original = ZipUtil.decompressURL(compressed1);

		System.out.println("starttime=" + original);

		System.out.println(original.equals(url));
		for(char i = 1; i < 255; i++ )
		{
			System.out.println((char)i);
		}
	}
}
