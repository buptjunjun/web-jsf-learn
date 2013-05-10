package com.junjun.algorithm.charpter7;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;

/**
 * 线性排序相关内容 计数排序 
 */

public class CountSort 
{
		
	/**
	 * 计数排序 是线性时间排序的一种，但是需要较多的额外内存，并且需要一些较强的条件：比如需要知道待排序元素都在某一个范围 比如[0,k]
	 * 计数排序的基本思想是，对于一个待排列的数组A，如果知道了一个元素e与比这个元素小的元素个数c就，就知道了e应该在排序所在的位置---c+1
	 * 计数排序 假设数组 A 的元素都是属于[0,k]的
	 * 需要使用两个辅助数组 B[0,length[A]],用于存放最后的结果，C[0，k]用于计数
	 * for i <- 0 to k 
	 *    C[i] = 0    //初始化C
	 * 
	 * for i <- 0 to length[A] 
	 *    C[A[i]] =  C[A[i]]+1  //C[i]记录等于i出现的个数
	 * 
	 * for i <- 0 to k 
	 *   C[i] = C[i]+C[i-1]     // 现在C[i]记录了小于或等于i的个数
	 * 
	 * for i <- length(A) to 0	//决定A[i]的位置
	 *     B[C[A[i]]] = A[i]    //A[i]的位置应该是C[A[i]]
	 *     C[A[i]] =  C[A[i]]-1 // 因为有重复所以需要减1，比如有2个10,第一个10在C[10] = 11，第二个10应该在C[10]-1=9处
	 *   
	 * @param A
	 * @return
	 */
	public int [] sort(int A[],int maxElem)
	{
		int [] C = new int[maxElem];
		int [] B = new int[A.length+1];
				
		// 初始化 C 为[0,0,...,0]
		Arrays.fill(C, 0);
		
		//C[i]记录等于i出现的个数
		for(int i = 0; i < A.length; i++)
		{
			C[A[i]] = C[A[i]] + 1;
		}
		
		//现在C[i]记录了小于或等于i的个数
		for(int i = 1; i < C.length; i++)
		{
			C[i] = C[i-1] + C[i];
		}
		
		//开始 排序 寻找A[i]的应该的位置
		for(int i = 0; i < A.length; i++)
		{
			B[C[A[i]]] = A[i];  //A[i]的位置应该是C[A[i]]
			C[A[i]]--;           //因为有重复所以需要减1，比如有2个10,第一个10在C[10] = 11，第二个10应该在C[10]-1=9处
		}
		return B;
	}
	
	
	public static void main(String [] args)
	{
		int [] A = {4,2,8,7,1,3,5,6,2,3};
		CountSort cs = new CountSort();
		print(A, 0,A.length-1,"original array ");
		int [] B = cs.sort(A, 10);
		print(B, 1,B.length-1,"CountSort array ");
	
		

	}
	
	static void print(int [] A,int from,int to,String msg)
	{	
		System.out.print(msg+" \n");
		for(int i = from; i<=to; i++)
		{
			System.out.print(A[i]+" ");
		}
		System.out.print("\n");
	}
}
