package com.junjun.algorithm.pekingACM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
* 
*  深度优先搜索(寻找有几棵树)
*  http://poj.org/problem?id=1562
* 
*/


/**
 * 一个矩形区域，被分割成MxN的小方格
 * @author andyWebsense
 *
 */
class Region
{
	static public char VISITED = '^';
	//m行n列
	int m = 0;
	int n = 0;
	
	//用于存放一个region
	char [][] region = null;
	
	public Region(int m,int n)
	{
		this.m = m;
		this.n = n;
		
	}
	
	/**
	 * 初始化一个region
	 * @param scanner
	 */
	public void init(Scanner scanner)
	{
		region = new char [m][n];
		for(int i = 0;i < m;i++)
		{
			String line = scanner.nextLine();
			for(int j=0;j < n; j++)
				region[i][j] = line.charAt(j);
		}
	}
	
	/**
	 *  将坐标为(x,y)的grid向direction方向移动
	 * 方向定义如下
	 * 1 2 3
	 * 8 @ 4
	 * 7 6 5
	 * @param x
	 * @param y
	 * @param direction
	 * @return
	 */
	public int[] getNextPosition(int x,int y,int direction)
	{
		//寻找direction方向的坐标
		switch(direction)
		{
			case 1:
				x--;y--;break;
			case 2:
				x--;break;
			case 3:
				x--;y++;break;
			case 4:
				y++;break;
			case 5:
				x++;y++;break;
			case 6:
				x++;break;
			case 7:
				x++;y--;break;
			case 8:
				y--;break;
				
		}
		
		return new int[]{x,y};
	}
	
	/**
	 * move到x,y
	 * @param direction
	 * @return 返回true 如果move成功 否则返回false
	 */
	public boolean move(int x,int y)
	{	
		if(x>this.m-1 || x<0 || y>this.n-1 || y<0 || this.region[x][y]=='*'|| this.region[x][y]==VISITED)
			return false;
		//move到新的位置并将其设置为* 表示已经访问过了
		this.region[x][y] = VISITED;
		return true;
	}
	
	/**
	 * 深度优先搜索  寻找一个oil deposit
	 * @return
	 */
	public void  DFS(int x,int y)
	{	
		//遍历8个方向进行深度优先搜索
		for(int direction = 1; direction <= 8;direction++)
		{
			int [] nextpos = this.getNextPosition(x, y, direction);
			if(move(nextpos[0],nextpos[1]) == true)
			{
				DFS(nextpos[0],nextpos[1]);
			}
		}		
	}
	
	/**
	 * 深度优先找有多少个oil deposit
	 * @return
	 */
	public int countOilDeposit()
	{
		int count = 0;
		for(int i = 0; i < m; i++)
			for(int j = 0; j < n; j++)
			{
				if(move(i,j)== true)
				{
					count++;
					DFS(i,j);
				}
			}
		
		return count;
	}
}
public class Main1562
{
	
	
	public static void main(String[] args)
	{	
		Scanner scan = new Scanner(System.in);
		int m = 0;int n = 0;
		m = scan.nextInt();
		n = scan.nextInt();
		scan.nextLine();
		while(m!=0)
		{
			Region r = new Region(m,n);
			r.init(scan);
			int ret = r.countOilDeposit();
			System.out.println(ret);
			m = scan.nextInt();
			n = scan.nextInt();
			if (m==0)
				break;
			scan.nextLine();
		}
		scan.close();
	}
}