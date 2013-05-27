package com.junjun.algorithm.charpter14;
import java.util.*;
import java.util.Map.Entry;

import org.w3c.dom.Node;

import com.junjun.algorithm.charpter13.RBTree;
import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * ���ݽṹ����չ
 * ��̬˳��ͳ��
 *
 * ���ҵ�iС���� ��logN��ʱ�����ҵ����㷨,��ÿһ���ڵ��һ����---size����ʾ����ڵ������ĸ���
 * 
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
		
		public int size = 1;       //��ʾ����ڵ������ĸ��� �������Լ�
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
			
			//���������һ
			pre.size++;
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
	 * ��һ�ö����������Ѱ�ҵ�iС����
	 * ˼����random-select�����ŷ��ε�˼�룩һ��
	 * @param node
	 * @param i
	 * @return
	 */
	public Node OS_select(Node node, int i)
	{
		//node�����ж��ٿ�����
		int sizeleft = 0;
		if(node.lchild == null)
			sizeleft = 0;
		else
			sizeleft = node.lchild.size;
			
		/**
		 * ˼���random-select �ҵ�i������е���
		 */
		//�ҵ�
		if(sizeleft+1 == i)
			return node;
		else if(sizeleft+1<i) //�����������ҵ�( i-sizeleft-1)����
		{
			return OS_select(node.rchild, i-sizeleft-1);
		}
		else //������������ ��i�����
		{
			return OS_select(node.lchild, i);
		}
		
	}
	
	/**
	 *x�������������������ʱ���λ�ã�(����������ǵڼ�С)
	 *
	 *                  4  
	 *               2		6
	 *             1   3  5   7
	 *  ���� 3 �ǵ�3��� os_rank(3)����3
	 *
	 * @param x
	 * @return
	 */
	public int OS_rank(int x)
	{
		int ret = 1;
		Node node = this.search(x);
		
		if(node ==null)
			return ret;
		
		
		if(node.lchild !=null)
			ret = 1+ node.lchild.size;
		
		while(node != root)
		{
			Node p = node.p;
			 //node��p��������
			if(node == p.rchild)
			{
				if(node.lchild !=null)
					ret += node.lchild.size+1;
			}
			node = node.p;
		}
		return ret;
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
				if(node == parent.lchild)
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
			{
				this.root = node.lchild;
				if(node.lchild!=null)
					node.lchild.p = null;
			}
			else
			{
				//����������
				if(node.data < parent.data)
				{
					parent.lchild = node.lchild;
					if(node.lchild != null)
						node.lchild.p = parent;
				}
				else
				{
					parent.rchild = node.lchild;
					if(node.lchild != null)
						node.lchild.p = parent;
				}
			}
			node.p = null;
		}		
		else if(node.lchild == null && node.rchild != null) //����Ϊ�� ���Ӳ�Ϊ��
		{
			//���ɾ�����Ǹ�
			if(parent == null)
			{
				this.root = node.rchild;
				if(node.rchild!=null)
					node.rchild.p = null;
			}
			else
			{
				//����������
				if(node.data < parent.data)
				{
					parent.lchild = node.rchild;
					if(node.rchild != null)
						node.rchild.p = parent;
				}
				else
				{
					parent.rchild = node.rchild;
					if(node.rchild != null)
						node.rchild.p = parent;
				}
			}
			node.p = null;
		}
		else  //�����Ӷ���Ϊ�� �ҵ�ǰnode�ĺ�̣�
		{
			//�ҵ� node�ĺ��
			Node successor = this.successor(node);
			//�ú�����滻��ǰ��ɾ����node
			node.data = successor.data;
			//�ݹ�:�ص�������������
			deleteNode(successor);
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
	
	/**
	 * ��ӡһ�����ж��ٸ��ڵ�
	 * @param node
	 * @return
	 */
	public int countNode(Node node)
	{
		if(node == null) return 0;
		else return 1+countNode(node.lchild) + countNode(node.rchild);
	}
	
	public static void main(String [] args)
	{	
		int [] test = {4,2,1,3,6,5,7};
		BinarySearchTree bst = new BinarySearchTree();
/*		int length = 20;
		test = new int[length];
		for(int i = 0;i < length;i++)
			test[i] = i;	
		
		//�����д���
		test = new  RandomArray().disturb2(test);*/
		RBTree rbt = new RBTree();
		/*��ӡһ����
		 *                  4  
		 *               2		6
		 *             1   3  5   7
		 */
		bst.createTree(test);
		bst.print(bst.root);
		
		//max and min
		System.out.println("\nmax = "+bst.max(bst.root).data);
		System.out.println("min = "+bst.min(bst.root).data);
		
		//�ҵ�kС����
		System.out.println("Ѱ�ҵ�iС����");
		for(int i = 1;i <= test.length;i++)
		{
			Node n = bst.OS_select(bst.root, i);
			System.out.print(i+ "th max= "+n.data+" ,");
		}
		
		//�鿴x�ǵڼ�С����
		System.out.println("\n�鿴x�ǵڼ�С����");
		for(int i = 0;i < test.length;i++)
		{
			int rank = bst.OS_rank(test[i]);
			System.out.print(test[i]+" ranks "+ rank+"| ");
		}
	}
}
