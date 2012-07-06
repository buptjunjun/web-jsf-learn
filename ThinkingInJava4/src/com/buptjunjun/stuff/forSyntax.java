package com.buptjunjun.stuff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试 for的简洁语法
 * @author junjun
 *
 */

public class forSyntax 
{
	public static void main(String []args)
	{
		// 数组的情况下使用 for(var v: l) 语法
		float [] fs = new float[]{1.1f, 1.2f,3};		
		for(float f: fs)
		{
			System.out.println(f);
		}
		
		// container 的情况下使用 for(var v: l) 语法
		List<String> list = Arrays.asList("wan,xiao,lan".split(","));	
		for(String s: list)
		{
			System.out.println(s);
		}
	}

}
