package com.junjun.algorithm.pekingAcm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
* ������õ�2��֪ʶ�������������BFS��ö��(����������)
* BFS:������������������������ʼ״̬�ľ���,��4x4�����̿�����һ��16����(���ǿ��Է���16�������е��κ�һ��,һ����16��ѡ��,���Խ�16����),������������Ͻ��п����������
* ������������������������ӣ����������������ĵ�k�㣬����������Ҫk�������Ϸ.
* ö�٣������˶�֪����,����˵̫��
* ���⣺http://poj.org/problem?id=1753
* ��������:
* http://hi.baidu.com/albert02/item/2b78ec886169e453e63d19b5
*
*/

/**
 * ���� board
 * 
 */
class Board
{
	//����
	static public int width = 4;
	static public int height = 4;
	
	//��boolean�����ʾ����
	private boolean [] board = null;
	
	static Scanner scan = null;
	//level�Ǳ�ʾBFS ���԰����⿴��һ��16����,level��ʾ���board�ڵڼ����ˡ�
	public int level = 0;
	
	//���ڽ�����ת��Ϊһ������,add[i] = 2^i ,(��hashcode���õ�)
	//����[1,1,1,1,1,1,1,1��1,1,1,1��1,1,0,1]=add[0]+add[1]+...........+add[13]+0+add[15]
	static int [] add = new int [width*height];
	
	public Board(int width, int height)
	{
		Board.width = width;
		Board.height = height;	
		//��ʼ��һ��board����
		board = new boolean [width*height];
		
		int current = 1;
		//��ʼ��add
		for(int i = 0; i<width*height;i++)
		{
			add[i] = current;
			current*=2;
		}
	}
	
	/**
	 * �ӿ���̨��ȡ����,��ʼ��һ������
	 * w-��ɫ ��true��ʾ
	 * b-��ɫ ��false��ʾ
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
	 * ��ת [i,j] λ�õ����� �Լ� [i,j]��Χ������(����еĻ�)
	 * @param i
	 * @param j
	 */
	public void flip(int i,int j)
	{
		// ��[i,j]����ӳ��Ϊ�����е�λ�� 
		int position  = this.caculatePosition(i, j);
		// ��ת [i-1,j]λ�õ�����
		this.board[position] = !this.board[position];
		
		// ��ת [i-1,j] ������ڵĻ�
		if(i-1 >= 0)
		{
			position = this.caculatePosition(i-1, j);
			// ��ת the [i-1,j] λ�õ�����
			this.board[position] = !this.board[position];
		}
		
		// ��ת [i+1,j] ������ڵĻ�
		if(i+1 < width)
		{
			position = this.caculatePosition(i+1, j);
			// ��ת [i+1,j] λ�õ�����
			this.board[position] = !this.board[position];
		}
		
		// ��ת [i,j-1] ������ڵĻ�
		if(j-1 >= 0)
		{
			position = this.caculatePosition(i, j-1);
			// ��ת [i,j-1] λ�õ�����
			this.board[position] = !this.board[position];
		}
		
		// ��ת[i,j+1] ������ڵĻ�
		if(j+1 < height)
		{
			position = this.caculatePosition(i, j+1);
			// ��ת [i,j+1] λ�õ�����
			this.board[position] = !this.board[position];
		}
	}
	
	/**
	 * ��[i,j]����ӳ��Ϊ�����е�λ�� 
	 * @param i
	 * @param j
	 * @return the position in the array
	 */
	private int caculatePosition(int i,int j)
	{
		return i*width+j;
	}
	
	/**
	 * �Ƿ����� ȫ��black������ȫ��white 
	 * @return true ����õ����սṹ�����򷵻�false
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
	 * ��ӡ����
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
	 * ��¡һ������
	 */
	public Board clone() 
	{
		// TODO Auto-generated method stub
		Board b = new Board(this.width,this.height);
		b.setBoard(Arrays.copyOf(board, board.length));
		return b;
		
	}

	/**
	 * �ж�2�������Ƿ�һ��
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
	 * hashcode ��ʾ���̶�Ӧ������ 1��ʾtrue��0 ��ʾfalse
	 * ���磺����Ϊ[1,1,1,....,0,0,1]= 2^0+2^1+....+2^15
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
	 *  ��ֹ�ظ����� flag[i]�е�i��ʾ [1,0,....,0]���board״̬����������� ����  [1,0,....,0] ��Ӧ1,i=1
	 *  fla[1]Ϊtrue��ʾ[1,0,0...,0]�Ѿ���������,����û��������, 65536��ʾ���boardһ����65536��״̬��65536=2^16
	 */
	static boolean[]  flag = new boolean[65536]; 
	//�������ڿ����������
	static Queue<Board> queue = new LinkedList<Board>();
	
	//count ��ʾ��ǰ�������������� ���Ϊ65536����Ϊ���ֻ��65536������
	static int count = 0;
	static public Board search()
	{
		Arrays.fill(flag, false);
		
		while(queue.size()>0)
		{				
			Board b = queue.poll();
			//�����ʼ���������� ֱ�ӷ���
			if(b.ok())
				return b;
			
			//ÿһ�ַ�ת���е�16����,��������������� ,���򽫵�ǰ״̬���뵽������
			for(int i = 0; i<b.width;i++)
			{
				for(int j = 0;j<b.height;j++)
				{
					// ��¡һ��board
					Board cb = b.clone();
					//��ת�����¡��board
					cb.flip(i, j);
					
					
					// ��ֹ�ظ���ǰ��״̬ ��Ҫ����Ƿ�����ǰ�Ѿ����ֹ� ���board��
					if(flag[cb.hashCode()]==true)
					{
						cb = null;
						continue;
					}
					// �������������״̬ ����ǰ״̬���뵽������ȡ �������board��Ӧ��flag����Ϊtrue����ʾ�Ѿ����������board��
					flag[cb.hashCode()]=true;
					//��ǰ��������board������1
					count++;
					
					//b��cb�ĸ��ף�cb�Ĳ��b�Ĳ�����1.
					cb.level=b.level+1;
					//�ж��Ƿ�ﵽ����Ҫ��״̬
					if(cb.ok())
						return cb;
					//���������
					queue.add(cb);
					
					//��������Ѿ�������65536��boad��û���ҵ��������޽���
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
		//��ʼ״̬��board
		Board b = new Board(4,4);
		b.init();
		//����ʼ״̬�������
		queue.add(b);
		//����
		Board ret = search();
		
		//���ؿձ�ʾ�޽�
		if(ret == null)
		{
			System.out.println("Impossible");
			return;
		}
		//��ӡ����Ľ��
	    System.out.println(ret.level);
	}
}