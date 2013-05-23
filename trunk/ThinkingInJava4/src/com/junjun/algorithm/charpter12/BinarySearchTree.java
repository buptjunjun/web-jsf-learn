package com.junjun.algorithm.charpter12;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * 二叉查找树
 * 主要操作：查找，寻找最大值,最小值，插入，删除，前驱，后继，建树(随机)
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
		else if(node.lchild != null && node.rchild == null) //左子不为空 右子为空
		{
			//如果删除的是根
			if(parent == null)
				this.root = node.lchild;
			else
			{
				//如果是左儿子
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
		else if(node.lchild == null && node.rchild != null) //左子为空 右子不为空
		{
			//如果删除的是根
			if(parent == null)
				this.root = node.rchild;
			else
			{
				//如果是左儿子
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
		else  //左右子都不为空 找当前node的后继，
		{
			//找到 node的后继
			Node successor = this.successor(node);
			//删除后继
			this.deleteNode(successor);
			//用后继来替换当前待删除的node
			node.data = successor.data;
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
	
	public static void main(String [] args)
	{	
		int [] test = {4,2,1,3,6,5,7};
		BinarySearchTree bst = new BinarySearchTree();

		/*打印一棵树
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
		
		//树高
		System.out.println("树高:"+bst.height(bst.root));
		
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
		
		//随机建树
		System.out.println("\n随机建树");
		int total = 0;
		int i = 0;
		for( i = 0;i < 10;i++)
		{
			bst.createTreeRandom(test);
			int height = bst.height(bst.root);
			System.out.print("root= "+bst.root.data+" height is "+height +"| ");
			total+=height;
		}
		System.out.println("\n随机建树平均高度:"+ ((total+0.0f)/i));
		
	}
}
