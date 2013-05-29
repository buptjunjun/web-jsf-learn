package com.junjun.algorithm.charpter15;
import java.util.*;
import java.util.Map.Entry;

import org.w3c.dom.Node;

import com.junjun.algorithm.charpter13.RBTree;
import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * 
 *动态规划:矩阵链乘法,最长公共子序列
 * 
 */

public class DynamicProgramming
{	
	
	
	public void MatrixChainOrder(int [] p)
	{
		//计算矩阵链乘法最优值
		int [][] m =null;
		int [][] s = null;
		
		int n = p.length-1 ;
		m = new int[n+1][n+1];
		s = new int[n+1][n+1];
		//单独一个矩阵需要0次标量操作
		for(int i = 1;i <=n;i++)
			m[i][i] = 0;

		//l表示一个当前的m[i,j]的长度
		for(int l = 2;l<=n;l++)
		{
			for(int i = 1;i<=n-l+1;i++)
			{
				int j = i+l-1;
				m[i][j] = Integer.MAX_VALUE;
				
				//找最小的一个 作为m[i,j]的最优值
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
		System.out.print("\n最优的矩阵计算顺序是:");
		printOptimalParens(s,1,n);
		System.out.println("\n需要执行的标量计算次数为:"+m[1][n]);
	}
	
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
	public static void main(String [] args)
	{
		DynamicProgramming dp = new DynamicProgramming();
		//动态规划计算最优的矩阵计算顺序
		System.out.println("动态规划计算最优的矩阵计算顺序:");
		int [] p = {30,35,15,5,10,20,25};
		dp.MatrixChainOrder(p);
	}
}
