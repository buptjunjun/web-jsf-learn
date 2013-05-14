package com.junjun.algorithm.charpter9;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * ��λ�� ˳��ͳ��ѧ
 */

public class Chapter9 
{

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
	 * �ҵ�����A[from,to]������Ԫ��
	 * ʱ�临�Ӷ�Ϊo(n) ͬ���ҵ���СֵҲ����ͬ�ĵ���
	 * @return ������λ��
	 */
	public int findMax(int []A ,int from, int to)
	{
		int max = Integer.MIN_VALUE;
		int index = from;
		for(int i = from; i <= to; i++)
		{
			if(A[i] > max)
			{
				max = A[i];
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * ͬʱ�ҵ����ֵ����Сֵ
	 * �����ÿһ��Ԫ�����Ƕ�ȥ�뵱ǰ ���ֵ����Сֵ�Ƚ�һ�µĻ���ÿһ��Ԫ�ض�Ҫ�Ƚ�2�Σ�ʱ��Ϊ 2n
	 * ���������������������Ƚϣ��ٽ��ϴ���뵱ǰ���ıȽϣ���С���뵱ǰ��С�ıȽϣ��ٵõ���ǰ��С�����ֵ��
	 * ��������2��Ԫ�ؽ�����3�αȽ� ƽ��ÿһ��Ԫ�رȽ� 3/2 ��,����ֻ��Ҫ�Ƚ�3n/2 ��
	 * @param args
	 * @return ����[�����С]
	 */
	public int[] findMaxMin(int []A ,int from, int to)
	{
		int []  ret = {-1,-1};
		int maxIndex = from;
		int minIndex = from;
		
		int currentMin = Integer.MAX_VALUE;
		int currentMax = Integer.MIN_VALUE;
		
		//�����Ƚ�
		int i = from;
		for(i = from ; i < to; i+=2)
		{
			if(i+1 > to) //���������
				break;
			
			int smaller = Integer.MIN_VALUE;
			int bigger = Integer.MAX_VALUE;
			/*����������αȽ�*/
			//�����Ƚ� �ҵ��ϴ��
			if(A[i] <= A[i+1])
			{
				smaller = i;
				bigger = i+1;
			}
			else
			{
				smaller = i+1;
				bigger = i;
			}
			
			//��С���뵱ǰ��Сֵ�Ƚ�
			if(A[smaller] < currentMin)
			{
				currentMin = A[smaller];
				minIndex = smaller;
			}
			
			//�ϴ��뵱ǰ���ֵ�Ƚ�
			if(A[bigger] > currentMax)
			{
				currentMax = A[bigger];
				maxIndex = bigger;
			}		
		}
		
		if(i == to) //˵����������  
		{
			//���һ��Ԫ���뵱ǰ�����СԪ�رȽ�
			if (A[to] < currentMin)
				minIndex = to;
			//���һ��Ԫ���뵱ǰ�����СԪ�رȽ�
			if (A[to] > currentMax)
				maxIndex = to;
			
		}
		ret[0] = maxIndex;
		ret[1] = minIndex;
		return ret;
	}
	
	/**
	 * ������A[from,to]���ҵ�һ����kС��Ԫ�� 
	 * random-select �㷨��
	 * �����ڿ��������е����ѡ��һ��pivot=p,��pivot��ߵĶ���pivotС A[from,]��pivot�ұߵĶ���pivot��
	 * ��� pivot���(����pivot)ǡ����k�������� ����pivot ��Ϊ���ǵ�k���
	 * ���pivot���(����pivot)��n���� ��n>k��pivot��ߵ�Ԫ�صݹ��������㷨
	 * ���pivot���(����pivot)��n����n<k,˵����������֪����n����С��ֵ�ˣ�ֻ��Ҫ�ݹ����pivot�ұ��ҵڣ�k-n������Сֵ����
	 * �㷨ʱ�临�Ӷ�Ϊo(n)
	 * @param A
	 * @param from
	 * @param to
	 * @param k
	 * @return
	 */
	public int findKthMin(int []A ,int from, int to,int k)
	{
		if (from == to)
			return from;
		
		QuickSort qs = new QuickSort(); //���Կ�������
		Random r = new Random(); //���ѡ��[from,...,to]�е�һ��
		int rand = r.nextInt(to-from+1)+from;
		int pivot = qs.partition1(A, from, to, rand);	
		int left = pivot - from +1;
		if (left == k)    //��� pivot���(����pivot)ǡ����k�������� ����pivot ��Ϊ���ǵ�k���
			return pivot;
		else if(left > k) //���pivot���(����pivot)��left���� ��left>k��pivot��ߵ�Ԫ�صݹ��������㷨
		{
			return findKthMin(A,from,pivot-1,k);
		}
		else
		{  
			//���pivot���(����pivot)��left����left<k,˵����������֪����left����С��ֵ�ˣ�ֻ��Ҫ�ݹ����pivot�ұ��ҵڣ�k-left������Сֵ����
			//�������ǵ�ԭʼ������λ2,3,1,5,6,8,10(�±��1��ʼ,2���±�Ϊ1) ����Ҫ�ҵ�6С����,k=6,����pivot=4(����5) ����4<6 ( ���4�����ȵ�kС����С)
			//��������ֻ��Ҫ��pivot���ұ� ��:6,8,10�ҵ�2 (����6-4)С�������ɣ��ұߵڶ�С�����������������6С������
			k = k - left;
			return findKthMin(A,pivot+1,to,k);
		}
		
		
		
	}
	public static void main(String [] args)
	{
		int [] A = {4,2,8,7,1,3,5,6,9090,0};
		Chapter9 ch9 = new Chapter9();
		print(A, 0,A.length-1,"original array ");
		
		//�ҵ����ֵ
		int p = ch9.findMax(A,0,A.length-1);
		System.out.println("max is:"+A[p]);
		
		//ͬʱ�������С
		int[] ret = ch9.findMaxMin(A,0,A.length-1);
		System.out.println("max is:"+A[ret[0]]+" min is:"+A[ret[1]]);
		
		//ͬʱ�������С
		for(int k = 1; k<= A.length; k++)
		{
			int kthMax = ch9.findKthMin(A,0,A.length-1,k);
			System.out.println("��"+k+"С����:"+A[kthMax]);
		}
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
