package com.junjun.algorithm.charpter2;

import com.junjun.algorithm.Sort;

/**
 * �ڶ��� ϰ��2-4
 * ����s[0...N]�����i<j ,s[i]>j�ͱ�ʾ��һ�������
 * �������������е��������
 * @author junjun
 *
 */
public class InversePair extends Sort
{
	//������Եĸ���
	private static int count = 0;
	
	/**
	 * ʹ�ù鲢�����������
	 * @param s
	 */
	public int countInversePair(int [] s)
	{
		count=0;
		this.mergeSort(s,0, s.length-1);
		return count;
	}

	/**
	 * ������ s���й鲢����,p<<q<r
	 * ����p��s[p,q] ��s[q+1,r]�����ź���ģ������Ǻϲ���һ���ź�������鲢�滻s[p,r],������������ҵ�����Եĸ���
	 * @param s ����
	 * @param p 
	 * @param q
	 * @param r
	 */
	private void merge(int [] s,int p,int q,int r)
	{
		int n1 = q-p+1; //���һ��s[p,q]�ĳ���
		int n2 = r-q;   //�ұ�һ��s[q+1,r]�ĳ���
		int [] L = new int [n1]; 
		int [] R = new int [n2];
		
		for(int i = 0; i < n1;i++)
			L[i] = s[p+i];
		for(int i = 0; i < n2; i++)
			R[i] = s[q+1+i];
	
		//��ӡ��ǰ������ұߵ����� L��R
		System.out.println("\n----------");
		System.out.print("L:");
		this.print(L);
		System.out.print("\nR:");
		this.print(R);
		System.out.println("");
		
		// ��ʼ�鲢
		int i =0; 
		int j = 0;
		int k = p;
		while(i < n1 && j < n2)
		{
			// ѡ���С����Ϊ�������
			if(L[i] <= R[j])
			{
				s[k++] =  L[i++];
			}
			else //���L[i]>R[j]���ܴ��������
			{
				/*�ұ�ÿ����һ��������n1-i������Դ���,��Ϊ���ұ�ʣ�µ�ÿһ��Ԫ��L[i...n1]����R[j]��С��
				* ����L=2,3 R=0,2,7
				* ��һ�֣�����R[0]=0, ��L=[2,3] ��2������� (2,0),(3,0)
				* �ڶ��֣�����L[0]=2,  �������� ,0�������
				* �����֣�����R[1]=2,  L=[3],��1������� (3,2)
				* �����֣�����L[1]=3,  ��������, 0�������
				* �����֣�����R[2]=7,  L=[] 0������� 
				* ������3������� 
				*/
				count+= (n1-i);		
				//��ӡ�����
				for(int l = i;l <n1;l++)
					System.out.print("("+L[l]+","+R[j]+")  ");
				
				s[k++] =  R[j++];
				
			}
		}
		
		//��ʣ�µĲ���
		while(i<n1) s[k++] = L[i++];			
		while(j<n2) {s[k++] = R[j++];}	
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
	
	
	public static void main(String[] args)
	{
		// �������5�� (2,1),(3,1),(8,1),(6,1),(8,6)
		int [] s = {2,3,8,6,1};
		
		InversePair ip = new InversePair();
		System.out.println("\n����Ը�����"+ip.countInversePair(s));;
		System.out.println("�����������飺");
		ip.print(s);
	}

}
