package com.junjun.algorithm.pekingACM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
*   
*   知识点:贪心算法,排序
*   算法: 对于一组数据 雷达范围为d, 每一个岛屿为一个点p(i,j)
*         1、按照岛屿的横坐标i对岛屿进行排序,从小到大.
*         2、如果某一个点的纵坐标(离海岸线距离大于d) 问题无解
*         3、计算每一个岛屿p,以d为半径与海岸线的(x轴)的交点（有两个,或者一个)这两个点在x轴上的区间为field[a,b]
*         4、从左边第一个点开始,找有交集的fields,比如p1,p2,p3的filed有交集，p4的field与前面的点没有交集。那么p1,p2,p3就可以用一个雷达覆盖
*            依次这样做,美找到一个这样的集合就需要一个雷达,最后返回需要的雷达数目。
*    http://poj.org/problem?id=1328    
*         
*/

/**
 * 岛屿 的坐标
 */
class Point implements Comparable
{
	public double x = 0;
	public double y = 0;
	public Point(double x,double y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return x+" "+y;
	}
	
	@Override
	public boolean equals(Object arg0)
	{
		// TODO Auto-generated method stub
		Point p = (Point)arg0;
		if(this.x == p.x && this.y == p.y)
			return true;
		return false;
	}
	
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return (int)(this.x+this.y);
	}
	@Override
	public int compareTo(Object arg0)
	{
		Point p = (Point)arg0;
		if(this.x == p.x)
			return 0;
		else if (this.x > p.x)
			return 1;
		else return -1;
		
	}
}


public class Main1328
{
	/**
	 * 返回需要的雷达数目
	 * 返回至少需要多少个雷达的个数
	 * @param ilands 表示 岛屿的坐标
	 * @param d d表示雷达的最大半径
	 */
	public static int countRadar(Point [] islands,Double d)
	{
		//对岛屿按照x坐标排序
		Arrays.sort(islands);
		int count = 0;
	
		 //一个岛屿按照d为半径与 x轴相交的区间
		double [] baseField = null;
		
		for(int i = 0;i < islands.length;i++)
		{
			Point p = islands[i];
			
			// 如果p.y大于半径这个island ,无解
			if(p.y >d)
				return -1;
			
			 //一个岛屿按照d为半径与 x轴相交的区间
			double[] field = caculate(p,d);
			
			// 如果baseField为空表示一个新的集合开始
			if(baseField == null)
			{
				 baseField =field ;
				 count++;
			}
			else
			{
				// 计算两个区间 的交集
				baseField = intersect(baseField,field);
				
				// 如果 baseField[0] == Double.MIN_VALUE 表示没有交集
				if (baseField[0] == Double.MIN_VALUE)
				{
					//如果  表示没有交集,当成一个集合的新的开始 计数count加一,field作为当前的区间.
					count++;
					baseField = field;
				}
			}		
		}
	
		return count;
	}
	
	/**
	 * 一个岛屿按照d为半径与 x轴相交的区间
	 * @param p 岛屿坐标
	 * @param d 雷达半径
	 * @return 交集区间 如果没有交集 返回 {Double.MIN_VALUE,Double.MIN_VALUE};
	 */
	public static double[] caculate(Point p,double d)
	{   
		double [] ret = {Double.MIN_VALUE,Double.MIN_VALUE};
		if(p.y > d)
			return ret;
		
		double width = Math.sqrt(d*d-p.y*p.y);
		ret[0] = p.x-width;
		ret[1] = p.x+width;

		return ret;
	}
	
	/**
	 * 计算两个区间的相交的区间
	 * @param range1
	 * @param range2
	 * @return 返回交集，如果不想交返回{Double.MIN_VALUE,Double.MIN_VALUE};
	 */
	public static double[] intersect(double [] range1,double [] range2)
	{
		double [] ret = {Double.MIN_VALUE,Double.MIN_VALUE};
		if(range1[0]>range2[1] || range1[1]<range2[0])
			return ret;
		
		double left = 0;
		if(range1[0]<=range2[0])
			left = range2[0];
		else left = range1[0];
		
		double right = 0;
		if(range1[1]>=range2[1])
			right = range2[1];
		else right = range1[1];
		
		ret[0] = left;
		ret[1] = right;
		
		return ret;
	}
	
	
	/**
	 * 删除重复的岛屿坐标
	 * @param p
	 * @return
	 */
	public static Point [] removeDuplicate(Point [] p)
	{
		Set<Point> set = new HashSet<Point>();
		set.addAll(Arrays.asList(p));
		return set.toArray(new Point[set.size()]);
	}
	
	
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		
		//存放每一个case岛屿的数据
		List<Point []> datas = new ArrayList<Point []>();
		
		//存放每一个case的雷达的半径
		List<Double> ds = new ArrayList<Double>();
		
		//取得数据
		String line = scanner.nextLine();
		while(!line.startsWith("0 0"))
		{
			
			String [] nums = line.split(" ");			
			double n = Double.valueOf(nums[0]);
			double d = Double.valueOf(nums[1]);
			
			List<Point> listPoint = new ArrayList<Point>();
			for(int i = 0; i<n; i++)
			{
				line = scanner.nextLine();
				nums = line.split(" ");			
				double x = Double.valueOf(nums[0]);
				double y = Double.valueOf(nums[1]);
				
				//if(y<0) continue;
				Point p = new Point(x,y);
				listPoint.add(p);
			}
			
			Point [] list = listPoint.toArray(new Point[listPoint.size()]);
			
			// 去除重复的点
			list = removeDuplicate(list);
			datas.add(list);
			ds.add(d);
			
			line = scanner.nextLine();		
			line = scanner.nextLine();
		}
		scanner.close();
		
		//计算结果。
		int caseCount = 0;
		for(Point []list:datas)
		{	
			//当前case雷达的半径
			double d = ds.get(caseCount);
			//最少需要多少个雷达
			int count = countRadar(list,d);			
			System.out.println("Case "+(caseCount+1)+": "+count);
			caseCount++;
		}
		
	}
}