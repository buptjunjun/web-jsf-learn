package com.buptjunjun.stuff;

import java.util.regex.Pattern;


public class Submask {

	/**
	 *  check if it is a valid submask
	 *  added by Andy 2012-8-30
	 * @param mask
	 * @return
	 */
	public static  int isValidSubmask(String mask)
	{
		// check if it is empty
		if(mask == null || "".equals(mask.trim()))
			return -1;
		
		String maskPattern1 = "\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}";
		String maskPattern2 = "/[1-9]\\d*";
		String maskPattern3 = "(1)*(0)*";
		String maskPattern4 = "[1-9](\\d)*|[0-9]";
		
		if(Pattern.matches(maskPattern1, mask))
		{
			// split the string like "255.255.255.0"
			String [] nums = mask.split("\\.");
			
			// if length of nums is not equals 4 , the format of this submask is wrong
			if (nums == null || nums.length != 4)
				return -1;
			
			String binaryStr = "";
			
			for(String num: nums )
			{
				// if submask was "255.255.255.01", the  "01" is not right format
				if(num != null && num.length() >1 && num.startsWith("0"))
						return -1;
				try
				{
					// if submask was "255.255.255.01", the  "01" is not right format
					if(!Pattern.matches(maskPattern4, num))
						return -1;
						
					int number = Integer.parseInt(num);
					// if is between 0 and 255
					if(number > 255 || number < 0)
						return -1;
					
					// if match 11111000 parttern
					binaryStr += Integer.toBinaryString(number);	
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return -1;
				}	
			}
			
			if (Pattern.matches(maskPattern3, binaryStr))
			{
				binaryStr = binaryStr.replace("0", "");
				int bit1count = binaryStr.length();
				if (bit1count <=0 || bit1count > 32)
					return -1;
				return bit1count;
			}
		}
		else if(Pattern.matches(maskPattern2, mask))
		{
			try
			{	mask = mask.split("/")[1];
				int number = Integer.parseInt(mask);			
				// if is between 0 and 255
				if(number > 32 || number < 0)
					return -1;
				return number;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return -1;
			}
		}

		System.out.println("over");
		return -1;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		String maskPattern1 = "([0-2]?\\d\\d\\.){3}\\d+";
		String maskPattern2 = "/\\d+";
		String maskPattern3 = "(1)*(0)*";
		System.out.println(Pattern.matches(maskPattern3, "000"));
		
		String maskStr1 = "255.255.255.0";
		String maskStr2 = "/26";
		
		System.out.println("----------------");
		System.out.println("\n--" + isValidSubmask("255.255.192.0"));
		System.out.println("\n--" + isValidSubmask("255.255.255.0"));
		System.out.println("\n--" + isValidSubmask("/31"));
		System.out.println("\n--" + isValidSubmask("31"));
		System.out.println("\n--" + isValidSubmask("33"));
		
		
		
		System.out.println("-------------");
		System.out.println(isValidIP("10.231.56.72"));
		System.out.println(isValidIP("10.231.56.2"));
		System.out.println(isvalidVLAN("022"));
		
		int number = Integer.parseInt("003");
		System.out.println(number);

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
