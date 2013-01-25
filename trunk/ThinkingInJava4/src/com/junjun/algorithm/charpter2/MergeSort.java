package com.junjun.algorithm.charpter2;

import com.junjun.algorithm.Sort;

/**
 * �鲢���� 
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
	 * ������ s���й鲢����,p<<q<r
	 * ����p��s[p,q] ��s[q+1,r]�����ź���ģ������Ǻϲ���һ���ź�������鲢�滻s[p,r]
	 * @param s ����
	 * @param p 
	 * @param q
	 * @param r
	 */
	private void merge(int [] s,int p,int q,int r)
	{
		int n1 = q-p+1; //���һ��s[p,q]�ĳ���
		int n2 = r-q;   //�ұ�һ��s[q+1,r]�ĳ���
		//�����һ��Ԫ����Ϊ�ڱ�
		int [] L = new int [n1+1]; 
		int [] R = new int [n2+1];
		
		for(int i = 0; i < n1;i++)
			L[i] = s[p+i];
		for(int i = 0; i < n2; i++)
			R[i] = s[q+1+i];
		//���һ��Ԫ���ڱ�
		L[n1] = Integer.MAX_VALUE; 
		R[n2] = Integer.MAX_VALUE;
		
		// ��ʼ�鲢
		int i =0; 
		int j = 0;
		int cadidate = 0;
		for(int k = p; k <= r; k++)
		{
			// ѡ���С����Ϊ�������
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
			//����
			s[k] = cadidate;
		}
	}
	
	/**
	 * �ݹ����merge����
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
