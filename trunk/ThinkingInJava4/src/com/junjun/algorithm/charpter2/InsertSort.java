package com.junjun.algorithm.charpter2;

import com.junjun.algorithm.Sort;

/**
 * 
 * @author buptjunjun
 * @time 2013-1-22
 *
 */
public class InsertSort extends Sort
{	
	/**
	 * 插入排序
	 * @param s 带排数列
	 * @param incOrDec 控制升序还是降序
	 * @return
	 */
	@Override
	 public int[] sort(int[] s,int incOrDec)
	 {
		if(s == null || s.length <=1) return s;	
		for(int i = 1;i <s.length; i++)
		{
			int key = s[i];
			int j = i-1;
			//下面将key插入到已经排好的数列中去
			if(incOrDec == Sort.INCREMENT) //升序
			{	
				while(j>=0 && key<s[j]){ s[j+1] = s[j]; j--;}
			}
			else if(incOrDec == Sort.DECREMENT) //降序
			{
				while(j>=0 && key>s[j]){ s[j+1] = s[j]; j--;}
			}
			s[j+1] = key;
		}		
		return s;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int [] s = {2,5,3,1,1,3,4,4,9,0};
		int [] ret = new InsertSort().sort(s,Sort.INCREMENT);		
		new InsertSort().print(s);
	}

}
