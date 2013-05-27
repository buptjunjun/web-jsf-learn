package com.junjun.algorithm.charpter13;
import java.util.*;
import java.util.Map.Entry;

import com.junjun.algorithm.charpter12.BinarySearchTree;
import com.junjun.algorithm.charpter5.RandomArray;
import com.junjun.algorithm.charpter8.QuickSort;

/**
 * 红黑树:
 * 红黑树是一种二叉查找树,不同点是，对于每一个节点都有一种颜色：红色或者黑色,通过对根到叶子节点的路径上的各个节点着色方式的限制，
 * 红黑树可以保证：每一一条路径比其他一条路径长2倍，所以树是接近平衡的。
 * 一棵二叉查找树满足下面性质叫做红黑树：
 * 1 每一个节点是红的或者黑的
 * 2 根节点是黑的
 * 3 叶节点是黑的
 * 4 如果一个节点是红的其两个儿子是黑的
 * 5 每个节点 到其每一个子孙节点路径上有相同数据的黑节点。
 * 
 * 平衡二叉树(AVL)与红黑树(RBT)
 * 平衡二叉树的追求的是全局均衡，如在做插入，删除操作时，需要调整整棵树，显然这是费时的，因此希望在做调整时，是局部调整，因此提出了红黑树，这样一种高效的数据结构(也是最变态的一种数据结构)
 */

public class RBTree
{
	public Node root = null;
	static public boolean RED = true;
	static public boolean BLACK = false;
	private Node NIL = new Node(Integer.MAX_VALUE,BLACK);
	{
		NIL.color = BLACK;
	}
	
	//二叉树的一个节点
	class Node
	{
		public boolean color = RED;
		public int data = 0;       //数据
		public Node p = null;       //父亲
		public Node lchild = null;  //左孩子
		public Node rchild = null;  //右孩子
		
		public Node(int data,boolean color)
		{
			// TODO Auto-generated constructor stub
			this.data = data;
		}
	}
	
	/**
	 * 创建一棵红黑树
	 * @param datas
	 * @return
	 */
	public void createRBTree(int [] datas)
	{
		this.root = null;
		for(int i = 0;i < datas.length;i++)
		{		
			this.insert(datas[i]);
		}
	}
	/**
	 * 创建一棵二叉查找树
	 * @param datas
	 * @return
	 */
	public void createBSTree(int [] datas)
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
		if(node == this.NIL)
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
			if(cur == this.NIL)
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
		Node pre = this.root;
		while(cur!=NIL)
		{	pre = cur;
			if(data < cur.data)
				cur = cur.lchild;
			else if(data > cur.data)
				cur = cur.rchild;
			else  
				break;		
		}
		return pre;
	}
	
	/**
	 * 向红黑树中插入一个节点并且调整为新的红黑树
	 * @param data
	 */
	public void insert(int data)
	{
		Node node= insertNode(data);
		if(node == null)
			return;
		this.insertFixup(node);
	}
	
	
	/**
	 * 往一棵二叉查找树中插入一个节点
	 * @param node
	 * @return
	 */
	private Node insertNode(int data)
	{
		//如果树为空 node直接作为树根
		if(this.root == null)
		{
			this.root = new Node(data,RED);
			this.root.rchild = NIL;
			this.root.lchild = NIL;
			this.root.p = null;
			NIL.lchild = this.root;
			NIL.rchild =this.root;
			return this.root;
		}
		
		//找到要插入的位置
		Node insertNode = searchInsertPos(data);
		//如果为空 表示已经有data存在了
		if(insertNode == null)
			return null;
		else
		{
			Node n = new  Node(data,RED);
			n.lchild = this.NIL;
			n.rchild = this.NIL;
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
				return null;
			return n;
		}		
	}
	
	/**
	 * 找一棵树的最小值
	 * @param node 根节点
	 * @return
	 */
	public Node min(Node node)
	{
		while(node.lchild !=NIL)
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
		while(node.rchild !=NIL)
			node = node.rchild;
		return node;
	}
	
	/**
	 * 找node的后继(中序)
	 */
	public Node successor(Node node)
	{
		if(node.rchild == NIL)
		{
			return null;
		}
		
		Node rchild = node.rchild;
		Node successor = this.min(rchild);
		return successor;
	}
	
	/**
	 *  插入一个节点后调整为新的红黑树
	 */
	private void insertFixup(Node n)
	{
		Node node = n;
		//如果插入的是 根节点

		if(node.p == null)
		{
			node.color = BLACK;
			return;
		}
		
		while(node.p!=null && node.p.color == RED&&node.p.p!=null )
		{  		
			//父节点
			Node p = node.p;
			//如果父节点是黑色 不会破坏红黑树的性质 直接返回
			if(p.color==BLACK)
				return;
			
			//如果父节点为红色违反了 规则"如果一个节点是红的其两个儿子是黑的"
			//祖父节点 ,祖父节点一定存在的，因为而且不可能为NIL
			Node pp =p.p;
			 
			/*下面按照父节点是祖父节点的左孩子还是右孩子分为2种情况,这两种情况是对称的*/
			//如果父节点是祖父节点的左孩子
			if(p == pp.lchild)
			{
				//祖父的右孩子,node的叔父节点
				Node ppr =  pp.rchild;
				
				//按照叔父节点是红色还是黑色分为2种情况
				
				
				if(ppr.color == RED) //叔父节点是红色
				{
					/**
					 *            B -->pp
					 *      p<--R   R -->ppr
					 * node<--R  
					 * 
					 * 将 父亲和叔父的颜色修改为黑色 将祖父的颜色修改为红色  将祖父节点设为新的node 
					 * 得到下面的图
					 * 
					 *  		  R -->node(新的node)
					 *          B   B
					 * 	      R  
					 */
					
					//将 父亲和叔父的颜色修改为黑色
					p.color = BLACK;
					ppr.color = BLACK;
					//将祖父的颜色修改为红色
					pp.color = RED;
					
					//node变为其祖父节点
					node = pp;
				}
				else//叔父节点是黑色
				{
					/**
					 *  如果是node是p的右孩子左旋得到第二种情况
					 *  		  B->pp
					 *       p<-R   B->ppr
					 * 	   		  R->node  
					 * 
					 *   在p处左旋 将node设置为p 划归到下面这一种情况
					 *   
					 *            B->pp
					 *       p<-R   B->ppr
					 *  node<-R  
					 * 
					 * 在pp处右旋转 将p设为黑色 pp设为红色 变为下面的形式
					 *  		 B
					 *        R     R
					 *                B
					 *  
					 */
					//按照node是其父节点的左孩子还是右孩子分为2种情况 
					//这两种情况中，如果node是右孩子，可以通过做旋转划归为第二种情况---node是左孩子
					if(node == p.rchild) 
					{
						//node是右孩子，左旋变为左孩子
						this.LRotate(p);
						node = p;
					}
					else //node是左孩子
					{
						pp.color = RED;
						p.color = BLACK;
						this.RRotate(pp);
						
					}
				}
			}
			else//如果父节点是祖父节点的右孩子(对称的) 将上面的 if(p == pp.lchild)情况下，l r 交换位置即可
			{			
				Node ppl =  pp.lchild;
							
				if(ppl.color == RED)
				{			
					p.color = BLACK;
					ppl.color = BLACK;				
					pp.color = RED;			
					node = pp;
				}
				else
				{				
					if(node == p.lchild) 
					{			
						this.RRotate(p);
						node = p;
					}
					else 
					{
						pp.color = RED;
						p.color = BLACK;
						this.LRotate(pp);
					}
				}
			}
		
		}
		
		this.root.color = BLACK;
	}
	
	/**
	 * 找node的前驱(中序)
	 */
	public Node predecessor(Node node)
	{
		if(node.lchild == NIL)
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
	 * 右旋转
	 * @param node
	 */
	public void RRotate(Node node)
	{
		if(node == null)
			return;
		Node parent = node.p;	
		Node lchild_node = node.lchild;
		Node rlchild_node = lchild_node.rchild;
		
		node.lchild = rlchild_node;
		node.p = lchild_node;
		
		lchild_node.p = parent;
		lchild_node.rchild = node;
		
		if(rlchild_node != NIL)
		{
			rlchild_node.p = node;
		}
		
		//如果是root特殊处理 设置新的root
		if(parent == null)
		{
			this.root = lchild_node;
			this.root.p = null;
			NIL.lchild = this.root;
			NIL.rchild =this.root;		
		}
		else
		{
			if(parent.lchild == node)
				parent.lchild = lchild_node;
			else
				parent.rchild = lchild_node;
		}
	}
	
	/**
	 * 左旋转
	 * @param node
	 */
	public void LRotate(Node node)
	{
		if(node == null)
			return;
		
		Node parent = node.p;	
		Node rchild_node = node.rchild;
		Node lrchild_node = rchild_node.lchild;
		
		node.rchild = lrchild_node;
		node.p = rchild_node;
		
		rchild_node.p = parent;
		rchild_node.lchild = node;
		
		if(lrchild_node != NIL)
		{
			lrchild_node.p = node;
		}
		
		//如果是root特殊处理 设置新的root
		if(parent == null)
		{
			this.root = rchild_node;
			this.root.p = null;
			NIL.lchild = this.root;
			NIL.rchild =this.root;		
		}
		else
		{
			if(parent.lchild == node)
				parent.lchild = rchild_node;
			else
				parent.rchild = rchild_node;
		}
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
		if(node == NIL)
			return false;
		Node parent = node.p;

		//左右子树都为空 叶子节点
		if(node.lchild == NIL && node.rchild == NIL)
		{
			//只有一个元素 且是根
			if(parent == null)
				this.root = null;
			else
			{
				//如果是左儿子
				if(node == parent.lchild)
				{
					parent.lchild = NIL;
				}
				else
				{
					parent.rchild = NIL;
				}
			}
			node.p = null;
		}
		else if(node.lchild != NIL && node.rchild == NIL) //左子不为空 右子为空
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
					if(node.lchild != NIL)
						node.lchild.p = parent;
					
				}
				else
				{
					parent.rchild = node.lchild;
					if(node.lchild != NIL)
						node.lchild.p = parent;
				}
				if(node.color == BLACK)
				   deleteFix(node.lchild);
			}
			node.p = null;
		}		
		else if(node.lchild == NIL && node.rchild != NIL) //左子为空 右子不为空
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
					if(node.rchild != NIL)
						node.rchild.p = parent;
				}
				else
				{
					parent.rchild = node.rchild;
					if(node.rchild != NIL)
						node.rchild.p = parent;
				}
				if(node.color == BLACK)
					deleteFix(node.rchild);
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
	 * 将一个节点删除后的节点y(y一定是黑色)的颜色放到"替换的节点"(就是参数x)上,
	 * 如果x是红色,则x是"红黑色",如果x是黑色,x就是"黑黑色".
	 * @param x
	 */
	private void deleteFix(Node x)
	{
		//while x就是"黑黑色"
		while(x.color == BLACK && x != this.root && x!=null && x.p!=null)
		{
			Node p = x.p;     //x的父亲
				
			//x是左孩子
			if(x == p.lchild)
			{
				Node w = p.rchild;//w是x的兄弟
				//"第一大种"情况如果w的颜色是红色
				if (w.color == RED) //第一种情况 根据下面调整变成"第二大"种情况
				{
					w.color = BLACK;
					p.color = RED;
					this.LRotate(p); 
				}
				else //"第二大"种情况 --W为黑色
				{
					Node wl = w.lchild;
					Node wr = w.rchild;
					
					
					if(wl.color == BLACK && wr.color == BLACK)//第二种情况  w及其左右孩子都为黑色
					{
						w.color = RED;
						x=p;  //将x移到p
					}
					else if(wr.color == BLACK) //第三种情况 w为黑色,w的左孩子为红色右孩子为黑色,可以调整为第四种情况
					{
						wl.color = BLACK;
						w.color = RED;
						this.RRotate(w);
					}
					else//第四种情况 w为黑色,w的右孩子为红色
					{
						w.color=p.color;
						p.color=BLACK;
						wr.color = BLACK;
						this.LRotate(p);
						x = this.root;	
					}
						
				}
			}
			else //x是右孩子,与前面的情况一模一样,把左右调换一下即可
			{

				Node w = p.lchild;//w是x的兄弟
				//"第一大种"情况如果w的颜色是红色
				if (w.color == RED) //第一种情况 根据下面调整变成"第二大"种情况
				{
					w.color = BLACK;
					p.color = RED;
					this.RRotate(p); 
				}
				else //"第二大"种情况 --W为黑色
				{
					Node wl = w.lchild;
					Node wr = w.rchild;
					
					
					if(wr.color == BLACK && wl.color == BLACK)//第二种情况  w及其左右孩子都为黑色
					{
						w.color = RED;
						x=p;  //将x移到p
					}
					else if(wl.color == BLACK) //第三种情况 w为黑色,w的左孩子为红色右孩子为黑色,可以调整为第四种情况
					{
						wr.color = BLACK;
						w.color = RED;
						this.LRotate(w);
					}
					else//第四种情况 w为黑色,w的右孩子为红色
					{
						w.color=p.color;
						p.color=BLACK;
						wl.color = BLACK;
						this.RRotate(p);
						x = this.root;
						this.root.color = BLACK;
						return;
					}
						
				}
			}
		}	
		//如果x是红色,x就是"红黑色"
		if(x!=null&&x!=NIL)
			x.color = BLACK;
	}
	
	static int count = 0;
	/**
	 * 先序打印一棵树
	 */
	public void print(Node node)
	{
		if(node == NIL)
			return;
		System.out.print ("("+node.data+","+node.color+")"+(count++)+" ");
		print(node.lchild);
		print(node.rchild);
	}
	
	public int countNode(Node node)
	{
		if(node == NIL)
			return 0;
		return 1+countNode(node.lchild)+countNode(node.rchild);
		
	}
	
	public static void main(String [] args)
	{
		int length = 40;
		int [] test = new int[length];
		int i = 0;
		for(i = 0;i < length;i++)
			test[i] = i;	
		
		//将数列打乱
		test = new  RandomArray().disturb2(test);
		RBTree rbt = new RBTree();
		
		//建造红黑树
		rbt.createRBTree(test);
		//rbt.print(rbt.root);
		
		//树高
		System.out.println("\n树高="+rbt.height(rbt.root));
		
		//搜索test中每一个,没有搜索到某一个，则这棵树有问题
		for(i = 0;i < length;i++)
		{
			Node n = rbt.search(test[i]);
			if(n==null)
			{
				System.out.println(test[i]+" is not in the tree");
				return;
			}
			
			//随机抽查
			if(i%2000 == 0)
				System.out.println(i+" "+test[i]+"=="+n.data+":"+(test[i]==n.data)+",");
		}
		System.out.println("ok");
		
		//删除所有的元素
		System.out.println("delete test:");
		System.out.println(0+":"+test[0]+",countNode="+rbt.countNode(rbt.root)+", height="+rbt.height(rbt.root));
		for(i = 1;i < length/2;i++)
		{
			rbt.deleteNode(rbt.root.data);
			Node n =rbt.search(test[i]);
			if(n==null)
			{
				System.out.println(test[i]+" is not in the tree");
			}		
	
			System.out.println(i+":"+test[i]+",countNode="+rbt.countNode(rbt.root)+", height="+rbt.height(rbt.root));
		}
		System.out.println("ok");
		
		//插入测试
		System.out.println("insert test:");
		test = new int[50];
		for(i = test.length;i < test.length*2;i++)
			test[i-test.length] = i;	
		//将数列打乱
		test = new  RandomArray().disturb2(test);
		for(i = 0;i < test.length;i++)
		{
			rbt.insert(test[i]);
			System.out.println(i+":"+test[i]+",countNode="+rbt.countNode(rbt.root)+", height="+rbt.height(rbt.root));
		}
	}
}
