package com.junjun.algorithm.charpter13;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter12.BinarySearchTree;
import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * �����:
 * �������һ�ֶ��������,��ͬ���ǣ�����ÿһ���ڵ㶼��һ����ɫ����ɫ���ߺ�ɫ,ͨ���Ը���Ҷ�ӽڵ��·���ϵĸ����ڵ���ɫ��ʽ�����ƣ�
 * ��������Ա�֤��ÿһһ��·��������һ��·����2�����������ǽӽ�ƽ��ġ�
 * һ�ö�������������������ʽ����������
 * 1 ÿһ���ڵ��Ǻ�Ļ��ߺڵ�
 * 2 ���ڵ��Ǻڵ�
 * 3 Ҷ�ڵ��Ǻڵ�
 * 4 ���һ���ڵ��Ǻ�������������Ǻڵ�
 * 5 ÿ���ڵ� ����ÿһ������ڵ�·��������ͬ���ݵĺڽڵ㡣
 * 
 * ƽ�������(AVL)������(RBT)
 * ƽ���������׷�����ȫ�־��⣬���������룬ɾ������ʱ����Ҫ��������������Ȼ���Ƿ�ʱ�ģ����ϣ����������ʱ���Ǿֲ��������������˺����������һ�ָ�Ч�����ݽṹ(Ҳ�����̬��һ�����ݽṹ)
 */

public class RBTree
{
	public Node root = null;
	static public boolean RED = true;
	static public boolean BLACK = false;
	private Node NIL = new Node(Integer.MAX_VALUE,BLACK);
	
	//��������һ���ڵ�
	class Node
	{
		public boolean color = RED;
		public int data = 0;       //����
		public Node p = null;       //����
		public Node lchild = null;  //����
		public Node rchild = null;  //�Һ���
		
		public Node(int data,boolean color)
		{
			// TODO Auto-generated constructor stub
			this.data = data;
		}
	}
	
	/**
	 * ����һ�ö��������
	 * @param datas
	 * @return
	 */
	public void createTree(int [] datas)
	{
		this.root = null;
		for(int i = 0;i < datas.length;i++)
		{		
			this.insertNode(datas[i]);
		}
	}
	
	/**
	 * �������ĸ߶�
	 * @param node
	 * @return
	 */
	public int height(Node node)
	{
		if(node == null)
			return 0;
		int hl = height(node.lchild);
		int hr = height(node.rchild);
		if(hl < hr)
			return hr+1;
		else return hl+1;
	}
	
	/**
	 * ɾ����
	 */
	public void clear()
	{
		while(this.root != null)
			this.deleteNode(root);
	}
	
	
	
	private Node searchInsertPos(int data)
	{
		//�����Ϊ�� nodeֱ����Ϊ����
		if(this.root == null)		
			return null;
		
		//���Ҳ����
		Node cur = this.root;
		Node pre = this.root;
		while(true)
		{
			pre = cur;
			if(data < cur.data)
				cur = cur.lchild;
			else if(data > cur.data)
				cur = cur.rchild;
			else  //ÿһ��node��������ͬ
				return null;
			
			//�ҵ��˲���λ��
			if(cur == this.NIL)
			{
				return pre;
			}	
		}
	}
	
	/**
	 * ����ֵΪdata�Ľڵ�
	 * @param data
	 * @return null ���û���ҵ������򷵻�����ڵ�
	 */
	public Node search(int data)
	{
		Node cur = this.root;
		while(cur!=null)
		{
			if(data < cur.data)
				cur = cur.lchild;
			else if(data > cur.data)
				cur = cur.rchild;
			else  
				break;
		}
		return cur;
	}
	
	
	/**
	 * ��һ�ö���������в���һ���ڵ�
	 * @param node
	 * @return
	 */
	public Node insertNode(int data)
	{
		//�����Ϊ�� nodeֱ����Ϊ����
		if(this.root == null)
		{
			this.root = new Node(data,RED);
			this.root.rchild = NIL;
			this.root.lchild = NIL;
			this.root.p = NIL;
			
			return this.root;
		}
		
		Node insertNode = searchInsertPos(data);
		if(insertNode == null)
			return null;
		else
		{
			Node n = new  Node(data,RED);
			n.lchild = this.NIL;
			n.rchild = this.NIL;
			if(data < insertNode.data) //����Ȳ����С,���뵽����
			{
				insertNode.lchild = n;
				n.p = insertNode;
			}
			else if(data > insertNode.data) //����Ȳ�����,���뵽�Һ���
			{
				insertNode.rchild = n;
				n.p = insertNode;
			}
			else  //����ʧ��
				return null;
			return n;
		}		
	}
	
	/**
	 * ��һ��������Сֵ
	 * @param node ���ڵ�
	 * @return
	 */
	public Node min(Node node)
	{
		while(node.lchild !=null)
			node = node.lchild;
		return node;
	}
	
	/**
	 * ��һ���������ֵ 
	 * @param node ���ڵ�
	 * @return
	 */
	public Node max(Node node)
	{
		while(node.rchild !=null)
			node = node.rchild;
		return node;
	}
	
	/**
	 * ��node�ĺ��(����)
	 */
	public Node successor(Node node)
	{
		if(node.rchild == null)
		{
			return null;
		}
		
		Node rchild = node.rchild;
		Node successor = this.min(rchild);
		return successor;
	}
	
	
	/**
	 * ��node��ǰ��(����)
	 */
	public Node predecessor(Node node)
	{
		if(node.lchild == null)
		{
			return null;
		}
		
		Node lchild = node.lchild;
		Node predecessor = this.max(lchild);
		return predecessor;
	}
	
	/**
	 * ɾ��һ���ڵ�
	 * @param data
	 * @return
	 */
	public boolean deleteNode(int data)
	{
		Node node = search(data);
		if(node == null)
			return false;
		
		return this.deleteNode(node);
	}
	

	/**
	 * ����ת
	 * @param node
	 */
	public void RRotate(Node node)
	{
		Node parent = node.p;	
		Node lchild_node = node.lchild;
		Node rlchild_node = lchild_node.rchild;
		
		node.lchild = rlchild_node;
		lchild_node.rchild = node;
		
		if(parent.rchild == node)
			parent.rchild = lchild_node;
		else parent.lchild = lchild_node;
	}
	
	/**
	 * ����ת
	 * @param node
	 */
	public void LRotate(Node node)
	{
		Node parent = node.p;
		Node rchild_node = node.rchild;
		Node lrchild_node = rchild_node.lchild;
		
		node.rchild = lrchild_node;
		rchild_node.lchild = node;
		
		if(parent.rchild == node)
			parent.rchild = rchild_node;
		else parent.lchild = rchild_node;
	}
	
	/**
	 * ɾ��һ���ڵ�
	 * ���������
	 * 1,�ýڵ������Ӷ��ǿ�:ֱ��ɾ��
	 * 2,һ������Ϊ��,һ�����Ӳ�Ϊ��:
	 * 3,�����Ӷ���Ϊ�գ�ɾ���ýڵ�ĺ�̣��ú��(�������)�滻Ҫɾ���Ľڵ�
	 * @param node
	 * @return
	 */
	private boolean deleteNode(Node node)
	{
		//�����Ϊ�� nodeֱ����Ϊ����
		if(this.root == null)
		{
			return false;
		}
		
		//�Ȳ����Ƿ�������
		if(node == null)
			return false;
		Node parent = node.p;

		//����������Ϊ�� Ҷ�ӽڵ�
		if(node.lchild == null && node.rchild == null)
		{
			//ֻ��һ��Ԫ�� ���Ǹ�
			if(parent == null)
				this.root = null;
			else
			{
				//����������
				if(node.data < parent.data)
				{
					parent.lchild = null;
				}
				else
				{
					parent.rchild = null;
				}
			}
			node.p = null;
		}
		else if(node.lchild != null && node.rchild == null) //���Ӳ�Ϊ�� ����Ϊ��
		{
			//���ɾ�����Ǹ�
			if(parent == null)
				this.root = node.lchild;
			else
			{
				//����������
				if(node.data < parent.data)
				{
					parent.lchild = node.lchild;
				}
				else
				{
					parent.rchild = node.lchild;
				}
			}
			node.p = null;
		}		
		else if(node.lchild == null && node.rchild != null) //����Ϊ�� ���Ӳ�Ϊ��
		{
			//���ɾ�����Ǹ�
			if(parent == null)
				this.root = node.rchild;
			else
			{
				//����������
				if(node.data < parent.data)
				{
					parent.lchild = node.rchild;
				}
				else
				{
					parent.rchild = node.rchild;
				}
			}
			node.p = null;
		}
		else  //�����Ӷ���Ϊ�� �ҵ�ǰnode�ĺ�̣�
		{
			//�ҵ� node�ĺ��
			Node successor = this.successor(node);
			//ɾ�����
			this.deleteNode(successor);
			//�ú�����滻��ǰ��ɾ����node
			node.data = successor.data;
		}
		
		return true;
				
	}
	
	/**
	 * �����ӡһ����
	 */
	public void print(Node node)
	{
		if(node == NIL)
			return;
		System.out.print (node.data+" ");
		print(node.lchild);
		print(node.rchild);
	}
	
	public static void main(String [] args)
	{	
		int [] test = {4,2,1,3,6,5,7};
		RBTree rbt = new RBTree();

		/*��ӡһ����
		 *                  4  
		 *               2		6
		 *             1   3  5   7
		 */
		rbt.createTree(test);
		rbt.print(rbt.root);
		
		//max and min
		System.out.println("max = "+rbt.max(rbt.root).data);
		System.out.println("min = "+rbt.min(rbt.root).data);
		
		//search
		Node node = rbt.search(4);
		System.out.println("search = "+ node.data);
		
		//����  
		System.out.println("����");
		rbt.LRotate(node);
		rbt.print(rbt.root);
		
		//����
		node = rbt.search(7);
		System.out.println("\n����");
		rbt.RRotate(node);
		rbt.print(rbt.root);

		
		
		
	}
}
