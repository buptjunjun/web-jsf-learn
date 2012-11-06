package com.buptjunjun.md5;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.*;

public class ZipUtil
{
	// 将一个字符串按照zip方式压缩和解压
	// 压缩
	public static String compress(String str) throws IOException
	{
		if (str == null || str.length() == 0)
		{
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString("ISO-8859-1");
	}

	// 解压缩
	public static String uncompress(String str) throws IOException
	{
		if (str == null || str.length() == 0)
		{
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
				str.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0)
		{
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString("ISO-8859-1");
	}

	public static final byte[] compress1(String str)
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
			decompressed = out.toString("utf-8");
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

	// 测试方法
	public static void main(String[] args) throws IOException
	{
		System.out.println("starttime=" + ZipUtil.compress("starttime"));
		String s = ZipUtil.compress("starttime");
		System.out.println(s);

		System.out.println(ZipUtil.uncompress(s));

		String url = "http://www.baidu.com/s?tn=baiduhome_pg&ie=utf-8&bs=%E9%95%BF%E8%BF%9E%E6%8E%A5&f=3&rsv_bp=1&rsv_spt=1&wd=urlencode%E7%BC%96%E7%A0%81&rsv_sug3=4&rsv_sug1=4&rsv_sug4=49&oq=urlen&rsp=1&rsv_sug2=0&inputT=2481";
		byte[] compressed1 = ZipUtil.compress1(url);
		System.out.println("starttime=" + compressed1);
		String original = ZipUtil.decompress(compressed1);

		System.out.println("starttime=" + original);

		System.out.println(original.equals(url));
	}
}
