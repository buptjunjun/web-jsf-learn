package com.junjun.algorithm.charpter14;
import java.util.*;
import java.util.Map.Entry;

import org.w3c.dom.Node;

import com.junjun.algorithm.charpter13.RBTree;
import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * 数据结构的扩展
 * 动态顺序统计
 *
 * 查找第i小的数 在logN的时间内找到：算法,给每一个节点加一个域---size，表示这个节点的子孙的个数
 * 
 * 
 */

public class BinarySearchTree
{
	public Node root = null;
	//二叉树的一个节点
	class Node
	{
		public int data = 0;       //数据
		public Node p = null;       //父亲
		public Node lchild = null;  //左孩子
		public Node rchild = null;  //右孩子
		
		public int size = 1;       //表示这个节点的子孙的个数 包括其自己
		public Node(int data)
		{
			// TODO Auto-generated constructor stub
			this.data = data;
		}
	}
	
	/**
	 * 创建一棵二叉查找树
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
	 * 返回树的高度
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
	 * 删除树
	 */
	public void clear()
	{
		while(this.root != null)
			this.deleteNode(root);
	}
	
	/**
	 * 一个机节点数为N的树的高度最高为N,最低为logN
	 * 我们使用随机选择的方法，使得生成的搜索树高度的 期望为logN
	 * @param datas
	 */
	public void createTreeRandom(int [] datas)
	{
		//我们将一个数组随机打乱
		RandomArray ra = new RandomArray();
		int [] data = ra.disturb2(datas);
		
		createTree(data);
	}
	
	private Node searchInsertPos(int data)
	{
		//如果树为空 node直接作为树根
		if(this.root == null)		
			return null;
		
		//查找插入点
		Node cur = this.root;
		Node pre = this.root;
		while(true)
		{
			pre = cur;
			
			//子孙个数加一
			pre.size++;
			if(data < cur.data)
				cur = cur.lchild;
			else if(data > cur.data)
				cur = cur.rchild;
			else  //每一个node都不能相同
				return null;
			
			//找到了插入位置
			if(cur == null)
			{
				return pre;
			}	
		}
	}
	
	/**
	 * 在一棵二叉查找树中寻找第i小的数
	 * 思想与random-select（快排分治的思想）一样
	 * @param node
	 * @param i
	 * @return
	 */
	public Node OS_select(Node node, int i)
	{
		//node左孩子有多少棵子树
		int sizeleft = 0;
		if(node.lchild == null)
			sizeleft = 0;
		else
			sizeleft = node.lchild.size;
			
		/**
		 * 思想跟random-select 找第i大的数有点像
		 */
		//找到
		if(sizeleft+1 == i)
			return node;
		else if(sizeleft+1<i) //在右子树中找第( i-sizeleft-1)的树
		{
			return OS_select(node.rchild, i-sizeleft-1);
		}
		else //在左子树中找 第i大的树
		{
			return OS_select(node.lchild, i);
		}
		
	}
	
	/**
	 *x在中序遍历二叉搜索树时候的位置，(就是排序后是第几小)
	 *
	 *                  4  
	 *               2		6
	 *             1   3  5   7
	 *  例如 3 是第3大的 os_rank(3)返回3
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
			 //node是p的做左孩子
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
	 * 查找值为data的节点
	 * @param data
	 * @return null 如果没有找到，否则返回这个节点
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
	 * 往一棵二叉查找树中插入一个节点
	 * @param node
	 * @return
	 */
	public boolean insertNode(int data)
	{
		//如果树为空 node直接作为树根
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
			if(data < insertNode.data) //如果比插入点小,插入到左孩子
			{
				insertNode.lchild = n;
				n.p = insertNode;
			}
			else if(data > insertNode.data) //如果比插入点大,插入到右孩子
			{
				insertNode.rchild = n;
				n.p = insertNode;
			}
			else  //插入失败
				return false;
		}
		return true;
		
	}
	
	/**
	 * 找一棵树的最小值
	 * @param node 根节点
	 * @return
	 */
	public Node min(Node node)
	{
		while(node.lchild !=null)
			node = node.lchild;
		return node;
	}
	
	/**
	 * 找一棵树的最大值 
	 * @param node 根节点
	 * @return
	 */
	public Node max(Node node)
	{
		while(node.rchild !=null)
			node = node.rchild;
		return node;
	}
	
	/**
	 * 找node的后继(中序)
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
	 * 找node的前驱(中序)
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
	 * 删除一个节点
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
	 * 删除一个节点
	 * 有三种情况
	 * 1,该节点左右子都是空:直接删除
	 * 2,一个孩子为空,一个孩子不为空:
	 * 3,左右子都不为空：删除该节点的后继，用后继(中序遍历)替换要删除的节点
	 * @param node
	 * @return
	 */
	private boolean deleteNode(Node node)
	{
		//如果树为空 node直接作为树根
		if(this.root == null)
		{
			return false;
		}
		
		//先查找是否在树中
		if(node == null)
			return false;
		Node parent = node.p;

		//左右子树都为空 叶子节点
		if(node.lchild == null && node.rchild == null)
		{
			//只有一个元素 且是根
			if(parent == null)
				this.root = null;
			else
			{
				//如果是左儿子
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
		else if(node.lchild != null && node.rchild == null) //左子不为空 右子为空
		{
			//如果删除的是根
			if(parent == null)
			{
				this.root = node.lchild;
				if(node.lchild!=null)
					node.lchild.p = null;
			}
			else
			{
				//如果是左儿子
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
		else if(node.lchild == null && node.rchild != null) //左子为空 右子不为空
		{
			//如果删除的是根
			if(parent == null)
			{
				this.root = node.rchild;
				if(node.rchild!=null)
					node.rchild.p = null;
			}
			else
			{
				//如果是左儿子
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
		else  //左右子都不为空 找当前node的后继，
		{
			//找到 node的后继
			Node successor = this.successor(node);
			//用后继来替换当前待删除的node
			node.data = successor.data;
			//递归:回到上面的三种情况
			deleteNode(successor);
		}
		
		return true;
				
	}
	
	/**
	 * 先序打印一棵树
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
	 * 打印一棵树有多少个节点
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
		
		//将数列打乱
		test = new  RandomArray().disturb2(test);*/
		RBTree rbt = new RBTree();
		/*打印一棵树
		 *                  4  
		 *               2		6
		 *             1   3  5   7
		 */
		bst.createTree(test);
		bst.print(bst.root);
		
		//max and min
		System.out.println("\nmax = "+bst.max(bst.root).data);
		System.out.println("min = "+bst.min(bst.root).data);
		
		//找第k小的数
		System.out.println("寻找第i小的数");
		for(int i = 1;i <= test.length;i++)
		{
			Node n = bst.OS_select(bst.root, i);
			System.out.print(i+ "th max= "+n.data+" ,");
		}
		
		//查看x是第几小的数
		System.out.println("\n查看x是第几小的数");
		for(int i = 0;i < test.length;i++)
		{
			int rank = bst.OS_rank(test[i]);
			System.out.print(test[i]+" ranks "+ rank+"| ");
		}
	}
}
