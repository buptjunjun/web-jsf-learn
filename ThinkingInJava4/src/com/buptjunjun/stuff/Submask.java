package com.buptjunjun.stuff;

import java.util.regex.Pattern;


public class Submask {

	/**
	 *  check if it is a valid submask
	 *  added by Andy 2012-8-30
	 * @param mask
	 * @return
	 */
	public static  boolean isValidSubmask(String mask)
	{
		// check if it is empty
		if(mask == null || "".equals(mask.trim()))
			return false;
		
		String maskPattern1 = "([0-2]?\\d\\d\\.){3}\\d+";
		String maskPattern2 = "/[1-9]\\d*";
		String maskPattern3 = "1*0*";
		String maskPattern4 = "[1-9]\\d*";
		
		if(Pattern.matches(maskPattern1, mask))
		{
			System.out.println("mask 1");
			String [] nums = mask.split("\\.");
			for(String num: nums )
			{
				System.out.print(num+"  ");
				try
				{
					if(!Pattern.matches(maskPattern4, num))
						return false;
						
					int number = Integer.parseInt(num);
					// if is between 0 and 255
					if(number > 255 || number < 0)
						return false;
					
					// if match 11111000 parttern
					String binaryStr = Integer.toBinaryString(number);
					System.out.println(binaryStr);
					if (!Pattern.matches(maskPattern3, binaryStr))
						return false;
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
				
			}
			return true;
		}
		else if(Pattern.matches(maskPattern2, mask))
		{
			System.out.println("mask 2");
			try
			{	mask = mask.split("/")[1];
				System.out.println("mask = " + mask);
				int number = Integer.parseInt(mask);			
				// if is between 0 and 255
				if(number > 32 || number < 0)
					return false;
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}

		System.out.println("over");
		return false;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		String maskPattern1 = "([0-2]?\\d\\d\\.){3}\\d+";
		String maskPattern2 = "/\\d+";
		String maskPattern3 = "1*0*";
		
		String maskStr1 = "255.255.255.0";
		String maskStr2 = "/26";
		
		System.out.println(isValidSubmask("255.255.255.002"));
		System.out.println(isValidSubmask("255.255.255.0"));
		System.out.println(isValidSubmask("/31"));
		System.out.println(isValidSubmask("33"));
		
		
		
		System.out.println("-------------");
		System.out.println(isValidIP("10.231.56.72"));
		System.out.println(isValidIP("10.231.56.2"));
		System.out.println(isvalidVLAN("022"));

	}
	
	/**
	 * The valid IP range is: 1-223.0-255.0-255.0-255 broadcase IP is:
	 * 224-254.0-255.0-255.0-255 it will return true if input string in range
	 * 1-254.0-255.0-255.0-255
	 * 
	 * @param str
	 *            input string
	 * @return valid IP return true else return false
	 */
	public static boolean isValidIP(String str) {
		String regex0 = "\\d"; // 0-9
		String regex1 = "[1-9]"; // 1-9
		String regex2 = "[1-9]\\d"; // 10-99
		String regex3 = "1\\d{2}"; // 100-199
		String regex4 = "(2[0-4]\\d)" + "|(25[0-5])"; // 200-255
		// Qiuyun comment on 2009-05-18 for warning
		// String regex5 = "(2[0-1]\\d)" + "|(22[0-3])"; //200-223
		String regex6 = "(2[0-4]\\d)" + "|(25[0-4])"; // 200-254

		String part1 = "(" + regex1 + ")|(" + regex2 + ")|(" + regex3 + ")|("
				+ regex6 + ")";
		String part2 = "(" + regex0 + ")|(" + regex2 + ")|(" + regex3 + ")|("
				+ regex4 + ")";

		String regex = "(" + part1 + ")\\.(" + part2 + ")\\.(" + part2
				+ ")\\.(" + part2 + ")";

		return Pattern.matches(regex, str);
	}

	/**
	 * check if it is a valid vlan
	 * @param vlan
	 * @return  VLAN number if it is valid vlan else return -1
	 */
	public static int isvalidVLAN(String vlan)
	{
		String pattern = "[1-9]\\d*";
		
		if(vlan == null || !Pattern.matches(pattern, vlan))
			return -1;
		
		try
		{
			int ret = Integer.parseInt(vlan);
			return ret;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
}
