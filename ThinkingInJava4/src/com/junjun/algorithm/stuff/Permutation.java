package com.junjun.algorithm.stuff;

/**
 * �ݹ��ӡһ���������������
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
	 * per�� permutation ��ʾһ������
	 * �ݹ��ӡһ���������������
	 * �㷨��
	 * ����A[0,1,....,q]
	 * 1.���ǿ��Դ�0,1,2...,q����ѡһ��x  swap(A,0,x)
	 * 2.Ȼ���ټ����A[1,...,q]������y
	 * 3.��x+y����A[0,...,q]��һ������
	 * @param A
	 * @param per ��ǰ������
	 */
	static public void permutation(int [] A,String per)
	{
		//��ӡһ������ ,����Ϊper
		if(A == null)
		{
			System.out.println(per);
			return;
		}
		
		for(int i = 0; i < A.length;i++)
		{	//��ǰ����
			String curPer = new String(per);
			swap(A,0, i);
			//��A������ѡ��һ�������뵽��ǰ������
			curPer+=A[0];
			
			//�������A[1,...,A.length-1]������
			int [] next=subArray(A,1,A.length-1);	
			permutation(next,curPer);
			
			//��סҪswap����
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
