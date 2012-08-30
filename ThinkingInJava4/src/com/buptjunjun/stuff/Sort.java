package com.buptjunjun.stuff;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class Sort
{
	public static void main(String []args)
	{
		String [] testStr = {"aa","d","c_se",";lksdfdf"};
		List<String> l = Arrays.asList(testStr);
		Collections.sort(l);
		System.out.println(l);
	}
}