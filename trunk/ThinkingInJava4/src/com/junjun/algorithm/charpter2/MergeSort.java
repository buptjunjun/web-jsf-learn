package com.junjun.algorithm.charpter2;

import com.junjun.algorithm.Sort;

/**
 * 归并排序 
 * @author buptjunjun
 *
 */
public class MergeSort extends Sort
{
	
	@Override
	public int[] sort(int[] s, int incOrDec)
	{
			this.mergeSort(s,0, s.length-1);
			return s;
	}
	
	/**
	 * 对数组 s进行归并排序,p<<q<r
	 * 其中p到s[p,q] 和s[q+1,r]都是排好序的，将他们合并成一个排好序的数组并替换s[p,r]
	 * @param s 数组
	 * @param p 
	 * @param q
	 * @param r
	 */
	private void merge(int [] s,int p,int q,int r)
	{
		int n1 = q-p+1; //左边一段s[p,q]的长度
		int n2 = r-q;   //右边一段s[q+1,r]的长度
		//多分配一个元素作为哨兵
		int [] L = new int [n1+1]; 
		int [] R = new int [n2+1];
		
		for(int i = 0; i < n1;i++)
			L[i] = s[p+i];
		for(int i = 0; i < n2; i++)
			R[i] = s[q+1+i];
		//最后一个元素哨兵
		L[n1] = Integer.MAX_VALUE; 
		R[n2] = Integer.MAX_VALUE;
		
		// 开始归并
		int i =0; 
		int j = 0;
		int cadidate = 0;
		for(int k = p; k <= r; k++)
		{
			// 选择较小的作为插入对象
			if(L[i] < R[j])
			{
				cadidate = L[i];
				i++;
			}
			else
			{
				cadidate = R[j];
				j++;
			}
			//插入
			s[k] = cadidate;
		}
	}
	
	/**
	 * 递归进行merge排序
	 * @param s
	 * @param p
	 * @param r
	 */
	private void mergeSort(int [] s, int p ,int r)
	{
		if(p>=r) return;
		
		int q = (p+r)/2;
		mergeSort(s,p,q);
		mergeSort(s,q+1,r);
		merge(s,p,q,r);		
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
