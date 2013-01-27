package com.junjun.algorithm.charpter2;

import com.junjun.algorithm.Sort;

/**
 * ʹ�ö��������Ľ��Ĳ�������
 * @author buptjunjun
 * @time 2013-1-22
 *
 */
public class InsertSortBS extends Sort
{	
	/**
	 * ��������
	 * @param s ��������
	 * @param incOrDec ���������ǽ���
	 * @return
	 */
	@Override
	 public int[] sort(int[] s,int incOrDec)
	 {
		if(s == null || s.length <=1) return s;	
		for(int i = 1;i <s.length; i++)
		{	
			int key = s[i];
			//������ж��ֲ��� Ѱ�Ҳ����
			int insertPos = -1;// p����Ҫ�����λ��
			int p = 0;
			int q = i-1; 
			int middle = p;
			while(p<q)
			{
			    middle = (p+q)/2;
				if(s[middle] == key)
					break;
				else if(key < s[middle]) q = middle-1;
				else p=middle+1;
			}

			//�ҵ������ ��������ѭ����ֹ���� ���������
			//��� ����p==q�������� ��������p
			//�������if(s[middle] == key) �������� ��������middle
			insertPos = p==q?q:middle;
			
			//���������Ԫ�����������ƶ�
			for(int j = i; j>insertPos ; j--)
			{
				s[j] = s[j-1];
			}
			//��Ԫ�ز������� 
			s[insertPos] = key;
		}		
		return s;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int [] s = {2,5,3,1,1,3,4,4,9,0};
		int [] ret = new InsertSortBS().sort(s,Sort.INCREMENT);		
		new InsertSortBS().print(s);
	}

}
