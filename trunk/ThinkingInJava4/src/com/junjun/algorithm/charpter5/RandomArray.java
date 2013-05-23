package com.junjun.algorithm.charpter5;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

/**
 * ��һ��������������
 * @author junjun
 *
 */
public class RandomArray 
{
	private Random  r = new Random();
	 
	/**
	 * ������
	 * score����������
	 * num��Ŵ����е���
	 * @author andyWebsense
	 *
	 */
	class Item implements Comparable
	{
		int num = 0;
		long score = 0l;
		@Override
		public boolean equals(Object obj)
		{
			Item item = (Item)obj;
			return item.score == this.score;
		}
		
		@Override
		public int compareTo(Object arg0)
		{
			Item item = (Item)arg0;
			if(this.score > item.score)
				return -1;
			else if (this.score < item.score)
				return 1;
			else
				return 0;
		}
	}
	
	/**
	 * ��һ�����д���Ϊ�������
	 * �㷨��
	 * 1��ȡ������ĳ���n,����һ������ upperLimit = n^3
	 * 2����ÿһ������Ԫ������һ��[0,upperLimit]�ڵ��������score��ÿһ���������ͬ
	 * 3������score��������н�������(�������ݼ�����)
	 * 4���õ��������
	 *  ʱ�临�Ӷ�Ϊnlogn ��Ϊ ������nlogn��
	 * @param array
	 * @return ����һ�������Ժ������
	 */
	public int [] disturb1(int [] array)
	{
		//ȡ������ĳ���n,����һ������ upperLimit = n^3
		int upperlimit = (int) Math.pow(array.length,3);
		
		HashSet<Item> hset = new HashSet<Item>();
		for(int elem : array)
		{
			Item i = new Item();
			i.num = elem;
			i.score = -1;
			
			// ��֤ÿһ������score���� Ψһ��
			while(true)
			{	 
				int randomScore = r.random0n(upperlimit);
				i.score = randomScore;
				if(!hset.contains(i))
					break;
			}			
			hset.add(i);
		}
		
		//���շ��������н�������
		Object [] arrayItem =  hset.toArray();
		Arrays.sort(arrayItem);
		
		// ���� ���Һ������
		int [] ret = new int[arrayItem.length];
		for(int i = 0;i < arrayItem.length;i++)
		{	
			Item item = (Item)arrayItem[i];
			ret[i] = item.num;
		}
		
		return ret;
			
	}
	
	
	/**
	 * ��һ�����д���Ϊ�������
	 * �㷨 ���ǽ�����i��Ԫ����i��Ԫ�غ���������һ��Ԫ�أ� 
	 *   ������ΪA
	 *   for i <- 1 to length(A)
	 *     swap(A[i],A[Random(i,length(A))])
	 *     
	 *  ʱ�临�Ӷ�Ϊnlogn ��Ϊ ������nlogn��
	 * @param array
	 * @return ����һ�������Ժ������
	 */
	public int [] disturb2(int [] array)
	{
		//ȡ������ĳ���
		int  length = array.length;
		// copy һ��
		int [] tmp = Arrays.copyOf(array, length);
		
		for(int i = 0; i < length-1; i++)
		{
			int random = r.randomab(i, length-1);
			
			//swap(A[i],A[Random(i,length(A))])
			int tmpInt = tmp[random];
			tmp[random] = tmp[i];
			tmp[i] = tmpInt;
		}
		
		return tmp;
			
	}
	
	public static void main(String[] args)
	{
		// �����ҵ�����
		int [] array = {1,2,3,4,5,6,7,8,9};
		print(array,0,array.length - 1 ,"original array");
		
		RandomArray r = new RandomArray();
		int [] ret = r.disturb1(array);
		print(array,0,array.length - 1 ,"disturb1");
		
		ret = r.disturb2(array);	
		print(array,0,array.length - 1 ,"disturb2");
			
	}
	
	public static void print(int [] A,int from,int to,String msg)
	{	
		System.out.print(msg+" \n");
		for(int i = from; i<=to; i++)
		{
			System.out.print(A[i]+" ");
		}
		System.out.print("\n");
	}
}
