package com.junjun.algorithm.charpter2;

import com.junjun.algorithm.Sort;

/**
 * 使用二分搜索改进的插入排序
 * @author buptjunjun
 * @time 2013-1-22
 *
 */
public class InsertSortBS extends Sort
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
			//下面进行二分查找 寻找插入点
			int insertPos = -1;// p就是要插入的位置
			int p = 0;
			int q = i-1; 
			int middle = p;
			while(p<q)
			{
			    middle = (p+q)/2;
				if(s[middle] == key)
					break;
				else if(key < s[middle]) q = middle-1;
				else p=middle+1;
			}

			//找到插入点 根据上面循环终止条件 决定插入点
			//如果 是由p==q跳出来的 插入点就是p
			//如果是由if(s[middle] == key) 跳出来的 插入点就是middle
			insertPos = p==q?q:middle;
			
			//将插入点后的元素依次往后移动
			for(int j = i; j>insertPos ; j--)
			{
				s[j] = s[j-1];
			}
			//将元素插入插入点 
			s[insertPos] = key;
		}		
		return s;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int [] s = {2,5,3,1,1,3,4,4,9,0};
		int [] ret = new InsertSortBS().sort(s,Sort.INCREMENT);		
		new InsertSortBS().print(s);
	}

}
