package com.buptjunjun.stuff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * ���� for�ļ���﷨
 * @author junjun
 *
 */

public class forSyntax 
{
	public static void main(String []args)
	{
		// ����������ʹ�� for(var v: l) �﷨
		float [] fs = new float[]{1.1f, 1.2f,3};		
		for(float f: fs)
		{
			System.out.println(f);
		}
		
		// container �������ʹ�� for(var v: l) �﷨
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("wan,xiao,lan".split(",")));	
		for(String s: list)
		{
			System.out.println(s);
		}
		
		Iterator<String> i = list.iterator();
		while(i.hasNext())
		{
			String tmp = (String) i.next();
			if(tmp.equals("wan"))
				i.remove();
		}
		
		for(String s: list)
		{
			System.out.println(s);
		}
		
		
	}

}
