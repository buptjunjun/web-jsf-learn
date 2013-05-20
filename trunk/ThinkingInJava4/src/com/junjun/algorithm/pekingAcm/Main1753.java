package com.junjun.algorithm.pekingAcm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
* 这道题用到2个知识：宽度优先搜索BFS和枚举(暴力搜索吧)
* BFS:宽度优先搜索可以用来求离初始状态的距离,把4x4的棋盘看做是一个16叉树(我们可以翻动16个棋子中的任何一个,一共有16中选择,所以叫16叉树),我们在这棵树上进行宽度优先搜索
* 如果搜索到了满足条件的棋子，这个棋子在这棵树的第k层，我们最少需要k步完成游戏.
* 枚举：地球人都知道的,不用说太多
* 问题：http://poj.org/problem?id=1753
* 测试数据:
* http://hi.baidu.com/albert02/item/2b78ec886169e453e63d19b5
*
*/

/**
 * 棋盘 board
 * 
 */
class Board
{
	//长宽
	static public int width = 4;
	static public int height = 4;
	
	//用boolean数组表示棋盘
	private boolean [] board = null;
	
	static Scanner scan = null;
	//level是表示BFS 可以把问题看做一个16叉数,level表示这个board在第几层了。
	public int level = 0;
	
	//用于将棋盘转换为一个整数,add[i] = 2^i ,(在hashcode中用到)
	//例如[1,1,1,1,1,1,1,1，1,1,1,1，1,1,0,1]=add[0]+add[1]+...........+add[13]+0+add[15]
	static int [] add = new int [width*height];
	
	public Board(int width, int height)
	{
		Board.width = width;
		Board.height = height;	
		//初始化一个board数组
		board = new boolean [width*height];
		
		int current = 1;
		//初始化add
		for(int i = 0; i<width*height;i++)
		{
			add[i] = current;
			current*=2;
		}
	}
	
	/**
	 * 从控制台读取数据,初始化一个棋盘
	 * w-白色 用true表示
	 * b-黑色 用false表示
	 */
	public void init()
	{
		if(scan == null)
		{
			scan = new Scanner(System.in);
		}
		
		for(int i = 0; i < width; i++)
		{
			
			String line = scan.nextLine();
			char[] carray = line.toCharArray();
			for(int j = 0; j < this.height;j++)
			{				
				char c = carray[j];
				if(c=='w')
					this.board[i*width+j] = true;
				else
					this.board[i*width+j] = false;			
			}
		}
		scan.close();
		scan =null;
		 
	}
	
	/**
	 * 翻转 [i,j] 位置的棋子 以及 [i,j]周围的棋子(如果有的话)
	 * @param i
	 * @param j
	 */
	public void flip(int i,int j)
	{
		// 将[i,j]坐标映射为数组中的位置 
		int position  = this.caculatePosition(i, j);
		// 翻转 [i-1,j]位置的棋子
		this.board[position] = !this.board[position];
		
		// 翻转 [i-1,j] 如果存在的话
		if(i-1 >= 0)
		{
			position = this.caculatePosition(i-1, j);
			// 翻转 the [i-1,j] 位置的棋子
			this.board[position] = !this.board[position];
		}
		
		// 翻转 [i+1,j] 如果存在的话
		if(i+1 < width)
		{
			position = this.caculatePosition(i+1, j);
			// 翻转 [i+1,j] 位置的棋子
			this.board[position] = !this.board[position];
		}
		
		// 翻转 [i,j-1] 如果存在的话
		if(j-1 >= 0)
		{
			position = this.caculatePosition(i, j-1);
			// 翻转 [i,j-1] 位置的棋子
			this.board[position] = !this.board[position];
		}
		
		// 翻转[i,j+1] 如果存在的话
		if(j+1 < height)
		{
			position = this.caculatePosition(i, j+1);
			// 翻转 [i,j+1] 位置的棋子
			this.board[position] = !this.board[position];
		}
	}
	
	/**
	 * 将[i,j]坐标映射为数组中的位置 
	 * @param i
	 * @param j
	 * @return the position in the array
	 */
	private int caculatePosition(int i,int j)
	{
		return i*width+j;
	}
	
	/**
	 * 是否满足 全是black，或者全是white 
	 * @return true 如果得到最终结构，否则返回false
	 */
	public boolean ok()
	{
		boolean first = board[0];
		for(int i = 0; i < width*height; i++)
		{
			if(this.board[i] != first)
				return false;
		}
		return true;
	}
	
	/**
	 * 打印棋盘
	 * @param msg
	 */
	public void print(String msg)
	{
		System.out.println("\n"+msg);
		for(int i = 0;i<width*height; i++)
		{
			if(i!=0 && i%width==0)
				System.out.println();
			if(this.board[i])
				System.out.print("w");
			else 
				System.out.print("b");
		}
		
	}

	/**
	 * 克隆一个棋盘
	 */
	public Board clone() 
	{
		// TODO Auto-generated method stub
		Board b = new Board(this.width,this.height);
		b.setBoard(Arrays.copyOf(board, board.length));
		return b;
		
	}

	/**
	 * 判断2个棋盘是否一样
	 */
	public boolean equals(Object obj)
	{
		Board b = (Board)obj;
		for(int i = 0; i < this.width*this.height;i++)
		{
			if(this.board[i] != b.getBoard()[i])
				return false;
		}
		
		return true;
	}
	
	/**
	 * hashcode 表示棋盘对应的数， 1表示true，0 表示false
	 * 比如：棋盘为[1,1,1,....,0,0,1]= 2^0+2^1+....+2^15
	 */
	@Override
	public int hashCode()
	{
		int hashcode = 0;
		for(int i = 0;i<this.board.length;i++)
		{
			if(this.board[i]==true)
			 hashcode+=add[i];
		}
		return hashcode;
	}
	
	public void setBoard(boolean[] board)
	{
		this.board = board;
	}
	public boolean[] getBoard()
	{
		return this.board;
	}
	
}


public class Main1753
{
	/*
	 *  防止重复搜索 flag[i]中的i表示 [1,0,....,0]这个board状态代表二进制数 比如  [1,0,....,0] 对应1,i=1
	 *  fla[1]为true表示[1,0,0...,0]已经搜索过了,否则没有搜索过, 65536表示这个board一共有65536种状态，65536=2^16
	 */
	static boolean[]  flag = new boolean[65536]; 
	//队列用于宽度优先搜索
	static Queue<Board> queue = new LinkedList<Board>();
	
	//count 表示当前搜索过的棋盘数 最大为65536，因为最多只有65536个棋盘
	static int count = 0;
	static public Board search()
	{
		Arrays.fill(flag, false);
		
		while(queue.size()>0)
		{				
			Board b = queue.poll();
			//如果初始条件就满足 直接返回
			if(b.ok())
				return b;
			
			//每一轮翻转所有的16棋子,如果满足条件返回 ,否则将当前状态加入到队列中
			for(int i = 0; i<b.width;i++)
			{
				for(int j = 0;j<b.height;j++)
				{
					// 克隆一个board
					Board cb = b.clone();
					//翻转这个克隆的board
					cb.flip(i, j);
					
					
					// 防止重复以前的状态 需要检查是否在以前已经出现过 这个board了
					if(flag[cb.hashCode()]==true)
					{
						cb = null;
						continue;
					}
					// 如果还不是最终状态 将当前状态加入到队列中取 并将这个board对应的flag设置为true，表示已经搜索过这个board了
					flag[cb.hashCode()]=true;
					//当前搜索过的board数量加1
					count++;
					
					//b是cb的父亲，cb的层比b的层数多1.
					cb.level=b.level+1;
					//判断是否达到了需要的状态
					if(cb.ok())
						return cb;
					//加入队列中
					queue.add(cb);
					
					//如果我们已经搜索了65536个boad都没有找到，问题无解了
					if(count >= 65536)
						return null;
				}
			
			}
			b=null;
		}
		
		//
		return null;
	}
	public static void main(String[] args)
	{
		//初始状态的board
		Board b = new Board(4,4);
		b.init();
		//将初始状态加入队列
		queue.add(b);
		//搜索
		Board ret = search();
		
		//返回空表示无解
		if(ret == null)
		{
			System.out.println("Impossible");
			return;
		}
		//打印问题的结果
	    System.out.println(ret.level);
	}
}