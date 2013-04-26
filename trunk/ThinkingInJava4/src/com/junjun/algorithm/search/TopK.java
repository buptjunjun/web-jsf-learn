package com.junjun.algorithm.search;

import java.util.Arrays;
import java.util.Comparator;

/**
 * TopK �����ҵ�һ�������е�����K����
 * @author andyWebsense
 *
 */
public class TopK
{
	
	//��������
	private int [] testData = {}; 
	
	/**
	 * ʹ�ö������ȡtopK
	 * @param k
	 * @return
	 */
	public int [] getTopK1(int k)
	{
		int [] ret = new int[k];
		int [] tmpdata = new int [this.testData.length+1];
		for(int i= 0; i<this.testData.length;i++)
		{
			tmpdata[i+1] = this.testData[i];
		}
		
		//����Ϊ�󶥶�
		int lastNoneLeaf = this.testData.length/2;
		for(int i = lastNoneLeaf; i>0; i--)
		{
			this.adjustHeap(tmpdata, i);
		}
		
		for(int i = 1; i <= k; i++)
		{
			ret[i-1] = tmpdata[1];
			tmpdata[1] = tmpdata[tmpdata.length-i];
			tmpdata[tmpdata.length-i] = Integer.MIN_VALUE;
			this.adjustHeap(tmpdata, i);
		}
		return ret;
	}
	
	/**
	 * ��һ���������Ϊһ���󶥶�
	 * @param data ����
	 * @param begin ���ĸ�λ�ÿ�ʼ����
	 */
	private void adjustHeap(int [] data,int begin)
	{	
		int i = begin;
		int tmp = data[begin];
		while(i < data.length)
		{
			 int lchild = i*2;    //����ӵ�λ��
			 int rchild = i*2+1;  //�Ҷ��ӵ�λ��
			 int next = -1;       // �ҵ����������нϴ��һ��
			 if(rchild < data.length)
			 {
				 next = data[rchild] > data[lchild] ? rchild:lchild;  		 
			 }
			 else if (rchild >= data.length && lchild < data.length)
			 {
				 next = rchild;
			 }
			 
			 if(next == -1)
			 {
				 break;
			 }
			 
			 if(data[i] < data [next])
			 {
				 tmp = data[i];
				 data[i] = data[next];
			     data[next] = tmp;
			 }
			 
			 
			 i = next;
		}
		
		data[i] = tmp;
	}
	
	
	/**
	 * ʹ�ö������ȡtopK
	 * @param k
	 * @return
	 */
	public int [] getTopK2(int k)
	{
		int [] ret = new int[k];
		//��ʼ������
		for(int i =0; i < k; i++)
			ret[i] = this.testData[i];
		this.bubbleSort(ret);
		for(int i = k; i<this.testData.length;i++)
		{
			//����ȵ�ǰ��������С��һ��С ֱ���ӵ�
			if(this.testData[i] <= ret[k-1])
				continue;
			//����ȴ��ڵ�ǰ��С �ҵ�һ������λ��
			int j = 0;
			for( j = k-1; j >0 && this.testData[i] >= ret[j]; j--)
				ret[j] = ret[j-1];
			ret[j] = this.testData[i];
			
		}
		
		return ret;
	}
	
	/**
	 * ð������
	 * @param data
	 */
	public   void bubbleSort(int [] data)
	{
		for(int i = 0; i < data.length-1; i++)
		{
			for(int j = 0; j < data.length-1; j++)
			{
				if(data[j] < data[j+1])
				{
					int tmp = data[j];
					data[j] = data[j+1];
					data[j+1] = tmp;
				}
			}
		}
	}
	
	public int[] getTestData()
	{
		return testData;
	}

	public void setTestData(int[] testData)
	{
		this.testData = testData;
	}
	
	public static void main(String[] args)
	{
		int []testdata =  {1111,-1,3,1,1,-1,-1};
		TopK topk = new TopK();
		topk.setTestData(testdata);
		// ʹ�ö��ҵ�topk
		int [] ret = topk.getTopK1(2);
		TopK.printTestData(ret);
		
		// ʹ�ö��ҵ�topk
		ret = topk.getTopK2(4);
		TopK.printTestData(ret);
		
		topk.bubbleSort(testdata);
		TopK.printTestData(testdata);
	}
	
	static public void printTestData(int []  data)
	{
		for(int i = 0; i < data.length; i++)
			System.out.print(data[i] + " ");
		System.out.println();
	}

}
