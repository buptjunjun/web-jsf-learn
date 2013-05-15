package com.junjun.algorithm.pekingACM;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
* 这道题用到2个知识：宽度优先搜索BFS和枚举
* BFS:宽度优先搜索可以用来求离初始状态的距离
* 枚举：地球人都知道的,不用说太多
*
*/

/**
 * 棋盘
 * 
 */
class Board
{
	//长宽
	public int width = 4;
	public int height = 4;
	private boolean [] board = null; 
	
	public Board father = null;

	public Board(int width, int height)
	{
		this.width = width;
		this.height = height;	
		board = new boolean [width*height];
		
		
	}
	
	public void init()
	{
		Scanner scan = new Scanner(System.in);
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
	}
	
	/**
	 * flip the [i,j] piece and the pieces around it 
	 * @param i
	 * @param j
	 */
	public void flip(int i,int j)
	{
		// caculate the position of [i,j] pieces in the Array  
		int position  = this.caculatePosition(i, j);
		// flip the [i,j] pieces
		this.board[position] = !this.board[position];
		
		// flip [i-1,j] if possible
		if(i-1 >= 0)
		{
			position = this.caculatePosition(i-1, j);
			// flip the [i-1,j] pieces
			this.board[position] = !this.board[position];
		}
		
		// flip [i+1,j] if possible
		if(i+1 < width)
		{
			position = this.caculatePosition(i+1, j);
			// flip the [i+1,j] pieces
			this.board[position] = !this.board[position];
		}
		
		// flip [i,j-1] if possible
		if(j-1 >= 0)
		{
			position = this.caculatePosition(i, j-1);
			// flip the [i,j-1] pieces
			this.board[position] = !this.board[position];
		}
		
		// flip [i,j+1] if possible
		if(j+1 < height)
		{
			position = this.caculatePosition(i, j+1);
			// flip the [i,j+1] pieces
			this.board[position] = !this.board[position];
		}
	}
	
	/**
	 * map [i,j] pieces to the position in the Array  
	 * @param i
	 * @param j
	 * @return the position in the array
	 */
	private int caculatePosition(int i,int j)
	{
		int position  = i*width+j;
		return position;
	}
	
	/**
	 * if we make it 
	 * @return
	 */
	public boolean ok()
	{
		boolean first = board[0];
		for(int i = 0; i < width*height; i++)
		{
			if(this.board[i] != first)
				return false;
		}
		// every field of the board is filled with true or false. we make it .oh yeah! 
		return true;
	}
	
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

	public Board clone() 
	{
		// TODO Auto-generated method stub
		Board b = new Board(this.width,this.height);
		b.setBoard(Arrays.copyOf(board, board.length));
		return b;
		
	}

	public void setBoard(boolean[] board)
	{
		this.board = board;
	}
	public boolean[] getBoard()
	{
		return this.board;
	}
	
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
}


public class Main1753
{
	// 使用set防止重复搜索
	static HashSet<Board> boards = new HashSet<Board>();
	static Queue<Board> queue = new LinkedList<Board>();
	static int count = 0;
	static public Board search()
	{
		//翻转所有的棋子,如果满足条件返回 ,否则将当前状态加入到队列中取
		while(true)
		{	
			Board b = queue.poll();
			count++;
			for(int i = 0; i<b.width;i++)
			{
				for(int j = 0;j<b.height;j++)
				{
					Board cb = b.clone();
					cb.flip(i, j);
					cb.father = b;
					//we find the board
					if(cb.ok())
						return cb;
					// 如果还不是最终状态 将当前状态加入到队列中取
					// 防止重复以前的状态 需要检查set中是否有这个board了
					if(!boards.contains(cb))						
					{
						queue.add(cb);
						boards.add(cb);
					}
				}
			}
		}
		
		//return null;
	}
	public static void main(String[] args)
	{
		Board b = new Board(4,4);
		b.init();
		queue.add(b);
		boards.add(b);
		Board ret = search();
		int count = 0;
		while(ret.father!=null)
		{
			count++;
			ret = ret.father;
		}
		if(ret == null)
			System.out.println("impossible");
		else System.out.println("\n"+count);
		
	}
}