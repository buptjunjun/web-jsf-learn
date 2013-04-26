package com.junjun.algorithm.search;

import java.util.Arrays;
import java.util.Comparator;

/**
 * TopK 用于找到一个数列中的最大的K个数
 * @author andyWebsense
 *
 */
public class TopK
{
	
	//测试数据
	private int [] testData = {}; 
	
	/**
	 * 使用堆排序获取topK
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
		
		//调整为大顶堆
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
	 * 将一个数组调整为一个大顶堆
	 * @param data 数组
	 * @param begin 从哪个位置开始调整
	 */
	private void adjustHeap(int [] data,int begin)
	{	
		int i = begin;
		int tmp = data[begin];
		while(i < data.length)
		{
			 int lchild = i*2;    //左儿子的位置
			 int rchild = i*2+1;  //右儿子的位置
			 int next = -1;       // 找到两个儿子中较大的一个
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
	 * 使用堆排序获取topK
	 * @param k
	 * @return
	 */
	public int [] getTopK2(int k)
	{
		int [] ret = new int[k];
		//初始化数组
		for(int i =0; i < k; i++)
			ret[i] = this.testData[i];
		this.bubbleSort(ret);
		for(int i = k; i<this.testData.length;i++)
		{
			//如果比当前数组中最小的一个小 直接扔掉
			if(this.testData[i] <= ret[k-1])
				continue;
			//如果比大于当前最小 找到一个插入位置
			int j = 0;
			for( j = k-1; j >0 && this.testData[i] >= ret[j]; j--)
				ret[j] = ret[j-1];
			ret[j] = this.testData[i];
			
		}
		
		return ret;
	}
	
	/**
	 * 冒泡排序
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
		// 使用堆找到topk
		int [] ret = topk.getTopK1(2);
		TopK.printTestData(ret);
		
		// 使用堆找到topk
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
