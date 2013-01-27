package com.junjun.algorithm.charpter2;

import com.junjun.algorithm.Search;

/**
 * ���ֲ�����
 * @author junjun
 *
 */
public class BinarySearch extends Search
{
	/**
	 *  ���ֲ��� ��s[p...q]�ϲ���r
	 *  @param s �����ҵ����� ������������
	 *  @param p 
	 *  @param q
	 *  @return ����ҵ� �����ҵ�Ԫ�ص��±�,���򷵻�-1
	 */
	public int search(int[] s, int p, int q,int r)
	{		
		while(p <=q)
		{
			int middle = (p+q)/2;              //ȡ�м��λ�ý��ж���
			if(r == s[middle]) return middle;  //����ҵ�����middle
			else if(r<s[middle]) q = middle-1;
			else p = middle+1;
		}
		return -1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BinarySearch bs = new BinarySearch();
		int [] s = {0 ,1 ,1 ,2 ,3 ,3 ,4 ,5 ,8 ,9};
		for(int i = 0; i < s.length;i++)
		{
			System.out.print("s["+i+"] = "+s[i]+" ");
		}
		System.out.println("\n3 is at the position:"+bs.search(s, 0, s.length-1, 3));
		System.out.println("9 is at the position:"+bs.search(s, 0, s.length-1, 9));	
		System.out.println("11 is at the position:"+bs.search(s, 0, s.length-1, 11));	
	}
}
