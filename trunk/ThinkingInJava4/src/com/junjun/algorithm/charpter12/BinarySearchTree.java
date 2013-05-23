package com.junjun.algorithm.charpter12;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * ���������
 * ��Ҫ���������ң�Ѱ�����ֵ,��Сֵ�����룬ɾ����ǰ������̣�����(���)
 * 
 */

public class BinarySearchTree
{
	public Node root = null;
	//��������һ���ڵ�
	class Node
	{
		public int data = 0;       //����
		public Node p = null;       //����
		public Node lchild = null;  //����
		public Node rchild = null;  //�Һ���
		
		public Node(int data)
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
	
	/**
	 * һ�����ڵ���ΪN�����ĸ߶����ΪN,���ΪlogN
	 * ����ʹ�����ѡ��ķ�����ʹ�����ɵ��������߶ȵ� ����ΪlogN
	 * @param datas
	 */
	public void createTreeRandom(int [] datas)
	{
		//���ǽ�һ�������������
		RandomArray ra = new RandomArray();
		int [] data = ra.disturb2(datas);
		
		createTree(data);
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
			if(cur == null)
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
	public boolean insertNode(int data)
	{
		//�����Ϊ�� nodeֱ����Ϊ����
		if(this.root == null)
		{
			this.root = new Node(data);
			return true;
		}
		
		Node insertNode = searchInsertPos(data);
		if(insertNode == null)
			return false;
		else
		{
			Node n = new Node(data);
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
				return false;
		}
		return true;
		
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
		if(node == null)
			return;
		System.out.print (node.data+" ");
		print(node.lchild);
		print(node.rchild);
	}
	
	public static void main(String [] args)
	{	
		int [] test = {4,2,1,3,6,5,7};
		BinarySearchTree bst = new BinarySearchTree();

		/*��ӡһ����
		 *                  4  
		 *               2		6
		 *             1   3  5   7
		 */
		bst.createTree(test);
		bst.print(bst.root);
		
		//max and min
		System.out.println("max = "+bst.max(bst.root).data);
		System.out.println("min = "+bst.min(bst.root).data);
		
		//search
		Node node = bst.search(3);
		System.out.println("search = "+ node.data);
		
		//����
		System.out.println("����:"+bst.height(bst.root));
		
		//delete
		System.out.println("delete 3");
		bst.deleteNode(3);
		bst.print(bst.root);
		
		System.out.println("\ndelete 2");
		bst.deleteNode(2);
		bst.print(bst.root);
		
		System.out.println("\ndelete 4");
		bst.deleteNode(4);
		bst.print(bst.root);
		
		//�������
		System.out.println("\n�������");
		int total = 0;
		int i = 0;
		for( i = 0;i < 10;i++)
		{
			bst.createTreeRandom(test);
			int height = bst.height(bst.root);
			System.out.print("root= "+bst.root.data+" height is "+height +"| ");
			total+=height;
		}
		System.out.println("\n�������ƽ���߶�:"+ ((total+0.0f)/i));
		
	}
}
