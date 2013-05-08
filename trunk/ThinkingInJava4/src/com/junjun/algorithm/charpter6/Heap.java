package com.junjun.algorithm.charpter6;
import java.util.*;
import java.util.Map.Entry;

/**
 * ������������� 
 */

public class Heap 
{
	int [] heap = null;

	/**
	 * ��һ������A[1,n]����Ϊһ���󶥶�  ���µ���
	 * @param A ������������
	 * @param i �������Ľڵ�
	 * @param length 
	 * @return
	 */
	public int []  adjustDOWN(int A[],int i,int n)
	{
		int Ai = A[i];
		int bigger = i*2;
		while(true)
		{
			// ���� ���ӵ�����
			int left = i*2; 
			int right = i*2+1;
			
			// �ҳ�left��right�еĽϴ�� �����bigger��
			bigger = left;
			if(right <= n && left <= n)
				bigger = A[left] > A[right]?left:right;
			else if( right > n && left <= n )
				bigger = left;
			else // ���left ��right �೬����n ����ѭ��
				break; 
			
			if(Ai < A[bigger])
				A[i] = A[bigger];
			else break;
			
			i = bigger;
		}
		
		if(i <= n)
			A[i] = Ai;
		return A;
	}
	
	/**
	 * ��һ������A[1,n]����Ϊһ���󶥶�   ���ϵ���
	 * @param A ������������
	 * @param i �������Ľڵ�
	 * @param n ���һ��Ҷ�ӽڵ��λ��
	 * @return
	 */
	public int []  adjustUP(int A[],int i,int n)
	{
		if(i > n ) 
			return A;
		
		int Ai = A[i];
		int parent = i/2;
		while(parent > 0)
		{
			//����������Ľڵ���丸�ڵ�� ����A[i]���丸�ڵ�
			if(Ai > A[parent])
			{
				A[i] = A[parent];
				i = parent;
				parent = i/2;
			}
			else
				break;
		}
		
		if(i > 0 )
			A[i] = Ai;
		return A;
	}
	
	
	/**
	 * ��A[1...n]����Ϊһ���󶥶�
	 * @param A
	 * @return
	 */
	public int [] adjust2BiggerHeap(int [] A,int n)
	{
		// �ӵ�һ����Ҷ�ӽڵ㿪ʼ����ÿһ���ڵ�
		for(int i = n/2+1; i >=1; i--)
		{
			adjustDOWN(A, i,n);
		}
		
		return A;
	}
	
	
	/**
	 * ����A[i] <-> A[j]
	 * @param A
	 * @param i
	 * @param j
	 */
	private void swap(int A [] , int i,int j)
	{
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}
	
	/**
	 * ��A[1..A.length]���ж�����
	 * @param A
	 */
	public void heapSort(int [] A )
	{
		//�ȵ���Ϊ�󶥶�
		adjust2BiggerHeap(A,A.length-1);
		int i = A.length-1;
		while(i >= 1)
		{	
			//�����Ѷ����β��
			swap(A,1,i);
			i--;
			
			//����Ϊ�󶥶�
			adjustDOWN(A,1,i);
		}
	}
	
	/**
	 * A�Ѿ���һ���󶥶�,����Ҫ���� A[i]��ֵ��ʹ������Ȼ��һ���󶥶�
	 * �㷨��
	 *    ��� newValue < parent[i] �ȸ��ڵ�С �����µ���  
	 *    ���  newValue >=  parent[i] ���ϵ���  �������ڵ���A[i]ֱ�� ��Ϊһ���󶥶�
	 * @param A ��Ŷѵ����� A[1,A.length]
	 * @param i Ҫ�ı��λ��
	 * @param �ѵı߽�
	 * @param newValue Ҫ�ı��ֵ
	 */
	public int [] changeValue(int [] A, int i,int n, int newValue)
	{
		A[i] = newValue;
		this.adjustDOWN(A, i, n);
		this.adjustUP(A, i, n);
		return A;
	}
	
	/**
	 * A[1...n]�Ѿ���һ���󶥶�,��һ���󶥶��в���һ���µ�ֵnewValue, Ҫ��A����Ϊһ���󶥶�
	 * �㷨��
	 *   A[n+1] = newValue;
	 *   ����adjustUP
	 *   
	 * @param A ��Ŷѵ����� A[1,n]
	 * @param n ����ǰ �ѵı߽�
	 * @param newValue Ҫ�����ֵ
	 */
	public int [] insertValue(int [] A, int n, int newValue)
	{	
		A[n+1] = newValue;
		this.adjustUP(A, n+1, n+1);
		return A;
	}

	public static void main(String [] args)
	{
		int [] A = {0,2,3,4,1,2,2,3,1000,10,22,44,-11,-21,Integer.MIN_VALUE};
		Heap h = new Heap();
		
		// ����Ϊ�󶥶�
		h.adjust2BiggerHeap(A,A.length-2);
		print(A, A.length-2);
		System.out.print("\n");
		
		// ����һ��Ԫ�ص�ֵ
		h.changeValue(A, 5, A.length-2, 100000);
		print(A, A.length-2);
		System.out.print("\n");
		
		// ����һ��Ԫ�ص�ֵ
		h.insertValue(A,A.length-2,12111);
		print(A, A.length-1);
		System.out.print("\n");
		
		//������
		h.heapSort(A);
		print(A, A.length-1);
	
	}
	
	static void print(int [] A,int n)
	{
		for(int i = 1; i<=n; i++)
		{
			System.out.print(A[i]+" ");
		}
	}
}
