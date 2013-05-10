package com.junjun.algorithm.charpter7;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;

/**
 * ��������������� �������� 
 */

public class CountSort 
{
		
	/**
	 * �������� ������ʱ�������һ�֣�������Ҫ�϶�Ķ����ڴ棬������ҪһЩ��ǿ��������������Ҫ֪��������Ԫ�ض���ĳһ����Χ ����[0,k]
	 * ��������Ļ���˼���ǣ�����һ�������е�����A�����֪����һ��Ԫ��e������Ԫ��С��Ԫ�ظ���c�ͣ���֪����eӦ�����������ڵ�λ��---c+1
	 * �������� �������� A ��Ԫ�ض�������[0,k]��
	 * ��Ҫʹ�������������� B[0,length[A]],���ڴ�����Ľ����C[0��k]���ڼ���
	 * for i <- 0 to k 
	 *    C[i] = 0    //��ʼ��C
	 * 
	 * for i <- 0 to length[A] 
	 *    C[A[i]] =  C[A[i]]+1  //C[i]��¼����i���ֵĸ���
	 * 
	 * for i <- 0 to k 
	 *   C[i] = C[i]+C[i-1]     // ����C[i]��¼��С�ڻ����i�ĸ���
	 * 
	 * for i <- length(A) to 0	//����A[i]��λ��
	 *     B[C[A[i]]] = A[i]    //A[i]��λ��Ӧ����C[A[i]]
	 *     C[A[i]] =  C[A[i]]-1 // ��Ϊ���ظ�������Ҫ��1��������2��10,��һ��10��C[10] = 11���ڶ���10Ӧ����C[10]-1=9��
	 *   
	 * @param A
	 * @return
	 */
	public int [] sort(int A[],int maxElem)
	{
		int [] C = new int[maxElem];
		int [] B = new int[A.length+1];
				
		// ��ʼ�� C Ϊ[0,0,...,0]
		Arrays.fill(C, 0);
		
		//C[i]��¼����i���ֵĸ���
		for(int i = 0; i < A.length; i++)
		{
			C[A[i]] = C[A[i]] + 1;
		}
		
		//����C[i]��¼��С�ڻ����i�ĸ���
		for(int i = 1; i < C.length; i++)
		{
			C[i] = C[i-1] + C[i];
		}
		
		//��ʼ ���� Ѱ��A[i]��Ӧ�õ�λ��
		for(int i = 0; i < A.length; i++)
		{
			B[C[A[i]]] = A[i];  //A[i]��λ��Ӧ����C[A[i]]
			C[A[i]]--;           //��Ϊ���ظ�������Ҫ��1��������2��10,��һ��10��C[10] = 11���ڶ���10Ӧ����C[10]-1=9��
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
