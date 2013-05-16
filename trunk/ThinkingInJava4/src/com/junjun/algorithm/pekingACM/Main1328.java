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
*
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
	public static boolean LEFT = true;
	public static boolean RIGHT = false;
	/**
	 * ilands 表示 岛屿的坐标,d表示雷达的最大半径
	 * 返回至少需要多少个雷达的个数
	 * @param ilands
	 * @param d
	 */
	public static int  search(Point [] islands,int d,boolean beginDirection)
	{
		
		Arrays.sort(islands);
	
		int count = 0;
		int i = 0;
		boolean direction = beginDirection;
		double [] baseField = null;
		if(beginDirection == RIGHT)
		{
			for(i = 0;i < islands.length;i++)
			{
				Point p = islands[i];
				
				// 如果p.y大于半径这个island
				if(p.y >d)
					return -1;
				
				double[] field = caculate(p,d,direction);
				
				if(baseField == null)
				{
					 baseField =field ;
					direction = !beginDirection;
					count++;
				}
				else
				{
					baseField = intersect(baseField,field);
					if (baseField[0] == Double.MIN_VALUE)
					{
						direction = beginDirection;
						count++;
						baseField = caculate(p,d,direction);
						direction = !beginDirection;
					}
				}		
			}
		}
		else
		{
			for(i = islands.length-1;i >=0 ;i--)
			{
				Point p = islands[i];
				
				// 如果p.y大于半径这个island
				if(p.y >d)
					return -1;
				
				double[] field = caculate(p,d,direction);
				
				if(baseField == null)
				{
					 baseField =field ;
					direction = !beginDirection;
					count++;
				}
				else
				{
					baseField = intersect(baseField,field);
					if (baseField[0] == Double.MIN_VALUE)
					{
						direction = beginDirection;
						count++;
						baseField = caculate(p,d,direction);
						direction = !beginDirection;
					}
				}		
			}
		}

		return count;
	}
	
	
	public static double[] caculate(Point p,int d,boolean direction)
	{   
		double [] ret = {Double.MIN_VALUE,Double.MIN_VALUE};
		if(p.y > d)
			return ret;
		double width = Math.sqrt(d*d-p.y*p.y);
		
		if(direction == LEFT)
		{
			ret[0] = p.x-width;
			ret[1] = p.x;
		}
		else if (direction == RIGHT)
		{
			ret[0] = p.x;
			ret[1] = p.x+width;
		}
		
		return ret;
	}
	
	public static double[] intersect(double [] range1,double [] range2)
	{
		double [] ret = {Double.MIN_VALUE,Double.MIN_VALUE};
		if(range1[0]>=range2[1] || range1[1]<=range2[0])
			return ret;
		
		double left = 0;
		if(range1[0]<range2[0])
			left = range2[0];
		else left = range1[0];
		
		double right = 0;
		if(range1[1]>range2[1])
			right = range2[1];
		else right = range1[1];
		
		ret[0] = left;
		ret[1] = right;
		
		return ret;
	}
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		
		String line = scanner.nextLine();
		int caseCount = 1;
		while(!line.startsWith("0 0"))
		{
			
			String [] nums = line.split(" ");			
			int n = Integer.valueOf(nums[0]);
			int d = Integer.valueOf(nums[1]);
			
			Point [] list = new Point[n];
			for(int i = 0; i<n; i++)
			{
				line = scanner.nextLine();
				nums = line.split(" ");			
				Double x = Double.valueOf(nums[0]);
				Double y = Double.valueOf(nums[1]);
				
				Point p = new Point(x,y);
				list[i] = p;
			}
			
			int count1 = search(list,d,LEFT);
			int count2 = search(list,d,RIGHT);
			int count = -1;
			if( count1  < 0 && count2 < 0)
				count = -1;
			else if( count1  == -1 && count2 >= 0)
			{
				count = count2;
			}
			else if( count1  >=0  && count2 == -1)
			{
				count = count1;
			}
			else
			{
				count = count1>count2?count2:count1;
			}
			
			System.out.println("Case "+caseCount+": "+count);
			caseCount++;
			line = scanner.nextLine();
			if(line.startsWith("0 0"))
				break;
			if(line.isEmpty())
			{	
				line = scanner.nextLine();
				if(line.startsWith("0 0"))
				break;
			}
			
		}
		scanner.close();
		
	}
}