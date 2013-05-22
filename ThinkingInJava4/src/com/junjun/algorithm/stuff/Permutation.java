package com.junjun.algorithm.stuff;

/**
 * 递归打印一个数组的所有排列
 */
import java.util.Arrays;

public class Permutation
{

	static int [] subArray(int []  A,int begin,int end)
	{
		if(begin > end)
			return null;
		
		int [] ret = new int[end-begin+1];
		for(int i = begin;i<=end;i++)
		{
			ret[i-begin] = A[i];
		}
		
		return ret;
	}

	public static void swap(int [] a, int p,int q)
	{
		int tmp = a[p];
		a[p] = a[q];
		a[q] = tmp;
	}
	
	/**
	 * per即 permutation 表示一个排列
	 * 递归打印一个数组的所有排列
	 * 算法：
	 * 对于A[0,1,....,q]
	 * 1.我们可以从0,1,2...,q中任选一个x  swap(A,0,x)
	 * 2.然后再计算出A[1,...,q]的排列y
	 * 3.将x+y就是A[0,...,q]的一个排列
	 * @param A
	 * @param per 当前的排列
	 */
	static public void permutation(int [] A,String per)
	{
		//打印一个排列 ,排列为per
		if(A == null)
		{
			System.out.println(per);
			return;
		}
		
		for(int i = 0; i < A.length;i++)
		{	//当前排列
			String curPer = new String(per);
			swap(A,0, i);
			//从A中任意选择一个数加入到当前排列中
			curPer+=A[0];
			
			//下面计算A[1,...,A.length-1]的排列
			int [] next=subArray(A,1,A.length-1);	
			permutation(next,curPer);
			
			//记住要swap回来
			swap(A,0, i);
			
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		int [] test = {1,2,3};
		permutation(test,"");
	}

}
