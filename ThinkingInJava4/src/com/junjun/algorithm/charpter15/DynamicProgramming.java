package com.junjun.algorithm.charpter15;
import java.util.*;
import java.util.Map.Entry;

import org.w3c.dom.Node;

import com.junjun.algorithm.charpter13.RBTree;
import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * 
 *��̬�滮:�������˷�,�����������
 * 
 */

public class DynamicProgramming
{	
	
	
	/**
	 *  ��̬�滮 ��������˷�����
	 * @param p
	 */
	public void MatrixChainOrder(int [] p)
	{
		//����������˷�����ֵ
		//m[i,j]��ʾAi,...,Aj �˷����ŵı����˷�����
		int [][] m =null;
		//s[i][j]��ʾ ��Ai,...,Aj����ʱ�ķֽ�(Ai..As)(As+1..Aj) 
		int [][] s = null;
		
		int n = p.length-1 ;
		m = new int[n+1][n+1];
		s = new int[n+1][n+1];
		//����һ��������Ҫ0�α�������
		for(int i = 1;i <=n;i++)
			m[i][i] = 0;

		//l��ʾһ����ǰ��m[i,j]�ĳ���
		for(int l = 2;l<=n;l++)
		{
			for(int i = 1;i<=n-l+1;i++)
			{
				int j = i+l-1;
				m[i][j] = Integer.MAX_VALUE;
				
				//����С��һ�� ��Ϊm[i,j]������ֵ
				for(int k = i;k<=j-1;k++)
				{
					int q = m[i][k]+m[k+1][j]+p[i-1]*p[k]*p[j];
					if(q < m[i][j])
					{
						m[i][j] = q;
						s[i][j] = k;
					}
				}
			}
		}
		for(int i = 1;i<p.length;i++)
			System.out.print("A"+i+"("+p[i-1]+","+p[i]+")  ");
		System.out.print("\n���ŵľ������˳����:");
		printOptimalParens(s,1,n);
		System.out.println("\n��Ҫִ�еı����������Ϊ:"+m[1][n]);
	}
	
	/**
	 * ��ӡ ���ŵľ������˳��
	 * @param s
	 * @param i
	 * @param j
	 */
	public void printOptimalParens(int [][] s,int i,int j)
	{
		if(i == j)
			System.out.print("A"+i);
		else
		{
			System.out.print("(");
			printOptimalParens(s, i,s[i][j]);
			printOptimalParens(s,s[i][j]+1,j);
			System.out.print(")");
		}
	}
	
	/**
	 * ����������� longest common sequence 
	 * ����c 
	 *     
	 * @param str1
	 * @param str2
	 */
	public void LCS(char[] str1, char[] str2)
	{
		int len1 = str1.length;
		int len2 = str2.length;
		int [][] c = new int[len1][len2];
		char [][] b = new char[len1][len2];
		for(int i = 0;i <len1;i++)
			c[i][0] = 0;
		
		for(int j = 0;j <len2;j++)
			c[0][j] = 0;
		
		for(int i =1;i<len1;i++)
			for(int j = 1;j<len2;j++)
			{
				if(str1[i] == str2[j])
				{
					c[i][j] = c[i-1][j-1]+1;
					b[i][j] = '\\';
				}
				else
				{
					if(c[i-1][j] < c[i][j-1])
					{
						c[i][j] = c[i][j-1];
						b[i][j] = '-';
					}
					else
					{
						c[i][j] = c[i-1][j];
						b[i][j] = '|';
					}
				}
				
			}
		
		System.out.print("  ");
		for(int j = 1;j < len2;j++)
			System.out.print(str2[j]+" ");
		System.out.println();
		for(int i = 1;i < len1;i++)
		{   
			System.out.print(str1[i]+" ");
			for(int j = 1;j < len2;j++)
				System.out.print(b[i][j]+" ");
			System.out.println();
		}
		System.out.println("str1:"+new String(str1).substring(1));
		System.out.println("str2:"+new String(str2).substring(1));
		System.out.println("��󹫹�������Ϊ:");
		printLCS(b,str1,str1.length-1,str2.length-1);
	}
	
	public void printLCS(char [][] b,char [] str1,int i,int j)
	{
		if(i==0 || j==0)
			return;
		if(b[i][j] == '\\')
		{
			System.out.print((char)str1[i]);
			printLCS(b,str1,i-1,j-1);
		}
		else  if(b[i][j] == '-')
			printLCS(b,str1,i,j-1);
		else
			printLCS(b,str1,i-1,j);
			
	}
	
	
	/**
	 * ������ִ� 
	 */
	public void LCS1(char[] str1, char[] str2)
	{
		int posi = 0;
		int posj = 0;
		int max = 0;
		
		int len1 = str1.length;
		int len2 = str2.length;
		int [][] c = new int[len1][len2];
		for(int i = 0;i <len1;i++)
			c[i][0] = 0;
		
		for(int j = 0;j <len2;j++)
			c[0][j] = 0;
		
		for(int i =1;i<len1;i++)
			for(int j = 1;j<len2;j++)
			{
				if(str1[i] == str2[j])
				{
					c[i][j] = c[i-1][j-1]+1;
					if(c[i][j] > max)
					{
						max = c[i][j];
						posi = i;
						posj = j;
					}
				}
				else
				{
					c[i][j] = 0;					
				}
				
			}
		
		System.out.print("  ");
		for(int j = 1;j < len2;j++)
			System.out.print(str2[j]+" ");
		System.out.println();
		for(int i = 1;i < len1;i++)
		{   
			System.out.print(str1[i]+" ");
			for(int j = 1;j < len2;j++)
				System.out.print(c[i][j]+" ");
			System.out.println();
		}
		System.out.println("str1:"+new String(str1).substring(1));
		System.out.println("str2:"+new String(str2).substring(1));
		System.out.println("��󹫹��Ӵ�Ϊ:" +new String(str1).subSequence(posi-max+1, posi+1));
		
	}
	
	
	public static void main(String [] args)
	{
		DynamicProgramming dp = new DynamicProgramming();
		//��̬�滮�������ŵľ������˳��
		System.out.println("---��̬�滮�������ŵľ������˳��---");
		int [] p = {30,35,15,5,10,20,25};
		dp.MatrixChainOrder(p);
		
		//��������� str1[0]û����
		System.out.println("\n---��󹫹�������---");
		char [] str1 = {'&','b','d','c','a','b'};
		char [] str2 = {'&','a','b','c','b','d','a','b'};
		dp.LCS(str1, str2);
		
		//��������� str1[0]û����
		System.out.println("\n\n---��󹫹�������---");
		char [] str3 = {'&','b','d','c','a','b','a'};
		char [] str4 = {'&','a','d','c','a','d','a','b'};
		dp.LCS1(str3, str4);
		
	}
	
	
}
