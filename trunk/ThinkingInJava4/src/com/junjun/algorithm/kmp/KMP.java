package com.junjun.algorithm.kmp;

import com.junjun.algorithm.Search;

/**
 * please reference http://blog.csdn.net/yearn520/article/details/6729426
 * @author andyWebsense
 *
 */
public class KMP 
{

	/**
	 * generate the next array
	 * please reference http://blog.csdn.net/yearn520/article/details/6729426
	 * @return
	 */
	private static int [] createNext(char [] s)
	{
		int [] next = new int [s.length];
		next[0] = 0;
		
		for(int i = 1;i<s.length;i++)
		{
			char c = s[i];
			int compare = next[i-1];
			while(c!=s[compare] && compare!=0)
			{
				compare = next[compare-1];
			}
			
			if(c == s[compare])
			{
				next[i] = compare+1;
			}
			else
			{
				next[i] = 0;
			}
				
		}
		
		return next;
	}
	
	
	/**
	 * 在str1中查找str2
	 * 例如 str1 = agctagcagctagctg str2=gc
	 * 返回 1
	 * @param str1
	 * @param str1
	 * @return 如果找到返回第一个匹配的位置，否则返回-1
	 */
	public static int index(String str1,String str2)
	{
		char [] cstr1 = str1.toCharArray();
		char [] cstr2 = str2.toCharArray();
		
		int [] next = createNext(cstr2);
		
		int current = next[0];
		for(int i = 0; i<cstr1.length;i++)
		{
			if(cstr2[current] == cstr1[i])
			{
				current++;
				if(current>=cstr2.length)
					return i-cstr2.length+1;
			}
			else
			{
				current = next[current];
				if(cstr2[current] == cstr1[i])
				{
					current++;
					if(current>=cstr2.length)
						return i-cstr2.length+1;
				}
			}
			
		
		}
		return -1;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int [] next = KMP.createNext("agctagcagctagctg".toCharArray());
		System.out.println("------next------");
		for(int o:next) System.out.print(o+" ");
		System.out.println();
		
		int index = KMP.index("asdf;ljsadfjisljfdslalssldfjsald;fj", "ppp");
		System.out.println("------index------");
		System.out.println("index ="+index);
		

	}
	
	public static void print(Object [] objs)
	{
		
	}

}
