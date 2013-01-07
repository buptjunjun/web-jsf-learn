package com.websense.stuff;

import java.util.regex.Pattern;

public class AppmngUtil
{
	/**
	 *  check if it is a valid submask
	 *  added by Andy 2012-8-30
	 * @param mask
	 * @return error -1. return the mask by Prfix format. For example inupt 255.255.0.0 will return 16,  inupt /16 will return 16.
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
						String binaryNumber = Integer.toBinaryString(number);
						System.out.println("--" + binaryNumber);
						if(binaryNumber.equals("0"))
						{
							binaryStr += binaryNumber;
							continue;
						}
						
						if(binaryNumber.length() < 8)
							return -1;
						binaryStr += binaryNumber;
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
						return -1;
					}
				}

				if (Pattern.matches(maskPattern3, binaryStr))
				{
					System.out.println(binaryStr);
					binaryStr = binaryStr.replace("0", "");
					int bit1count = binaryStr.length();
					if (bit1count <1 || bit1count > 31)
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
					if(number > 31 || number < 1)
						return -1;
					return number;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return -1;
				}
			}
			else if ( Pattern.matches(maskPattern4, mask))
			{
				try
				{
					int maskInt = Integer.parseInt(mask);
					return maskInt;
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
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		int ret = isValidSubmask("0.0.0.0");
		System.out.println(ret);

	}

}
