package com.junjun.algorithm.charpter5;
import java.util.*;
import java.util.Map.Entry;

/**
 * ���ֲ�����
 * @author junjun
 *
 */
public class Random 
{
	private java.util.Random  r = new java.util.Random();
	/**
	 * �õ�һ���ȸ���  0,1
	 * @return
	 */
	public int random01()
	{
		return r.nextInt(2);
	}
	
	/**
	 * �������� random01() �õ�random 0 n 
	 * �㷨��
	 * 1�����n����ʹ�ö��ٸ�������λ��ʾ������ 8����ʹ��3λ��ʾ��10����ʹ��4λ��ʾ
	 * 2����ÿһλ���� ����һ��random01,�õ�һ�����������У����� 1101,0100
	 * 3��������������ת��Ϊʮ������ ret������1101 �õ�����ret=11,0100�õ�����ret=2
	 * 4������õ���ret����n ���»ص��ڶ��� ֪��ret����[0,n]Ϊֹ
	 * 5������ ret
	 * @param n
	 * @return �ȸ��ʷ���[0,n]
	 */
	public int random0n(int n)
	{
	    if (n < 1)
	    {
	    	System.out.println("a should not larger than b");
	    	return Integer.MAX_VALUE;
	    }
	    
	    // ����n�����ɶ��ٸ�������λ��ʾ
	    int countBit = 0;
	    int tmpn = n;
	    while( tmpn > 0)
	    {
	    	tmpn /= 2;
	    	countBit++;
	    }
	    
	    // total ��Ҫ���ɵ������
	   int total = 0; 
	   while(true)
	   {
		    //���� 2^0+2^1+...+2^countBit
	    	for( int i = 0; i < countBit; i++)
	    	{    
	    		//���random01Ϊ1�൱�ڵ�i������Ϊ1����Ҫ����2^i
	    		if(random01() == 1)
		    		total += Math.pow(2,i);
	    	}    
	    	// ���total����[0,n]��Χ�� ����ѭ��ֱ����������[0,n]Ϊֹ
	    	if(total > n)
	    		total = 0;
	    	else 
	    		break;
	   }
	   return total;
	    
	}
	
	/**
	 * �������� random01() �õ�random 0 n 
	 * randomab �����������ת��Ϊrandom0n����  [a,b] = [0+a,(b-a)+a]
	 * ����ֻ��Ҫ���� random(0,b-a)�õ�һ��������ټ���a����
	 * @param a ����
	 * @param b ����
	 * @return ��������[a,b]�������
	 */
	public int randomab(int a, int b)
	{
		return random0n(b-a)+a;
	}
	
	
	public static void main(String[] args)
	{
	}
}
