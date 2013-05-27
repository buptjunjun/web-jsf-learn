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
	{
		NIL.color = BLACK;
	}
	
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
	 * ����һ�ú����
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
	 * ����һ�ö��������
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
	 * �������ĸ߶�
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
	 * �������в���һ���ڵ㲢�ҵ���Ϊ�µĺ����
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
	 * ��һ�ö���������в���һ���ڵ�
	 * @param node
	 * @return
	 */
	private Node insertNode(int data)
	{
		//�����Ϊ�� nodeֱ����Ϊ����
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
		
		//�ҵ�Ҫ�����λ��
		Node insertNode = searchInsertPos(data);
		//���Ϊ�� ��ʾ�Ѿ���data������
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
		while(node.lchild !=NIL)
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
		while(node.rchild !=NIL)
			node = node.rchild;
		return node;
	}
	
	/**
	 * ��node�ĺ��(����)
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
	 *  ����һ���ڵ�����Ϊ�µĺ����
	 */
	private void insertFixup(Node n)
	{
		Node node = n;
		//���������� ���ڵ�

		if(node.p == null)
		{
			node.color = BLACK;
			return;
		}
		
		while(node.p!=null && node.p.color == RED&&node.p.p!=null )
		{  		
			//���ڵ�
			Node p = node.p;
			//������ڵ��Ǻ�ɫ �����ƻ������������ ֱ�ӷ���
			if(p.color==BLACK)
				return;
			
			//������ڵ�Ϊ��ɫΥ���� ����"���һ���ڵ��Ǻ�������������Ǻڵ�"
			//�游�ڵ� ,�游�ڵ�һ�����ڵģ���Ϊ���Ҳ�����ΪNIL
			Node pp =p.p;
			 
			/*���水�ո��ڵ����游�ڵ�����ӻ����Һ��ӷ�Ϊ2�����,����������ǶԳƵ�*/
			//������ڵ����游�ڵ������
			if(p == pp.lchild)
			{
				//�游���Һ���,node���常�ڵ�
				Node ppr =  pp.rchild;
				
				//�����常�ڵ��Ǻ�ɫ���Ǻ�ɫ��Ϊ2�����
				
				
				if(ppr.color == RED) //�常�ڵ��Ǻ�ɫ
				{
					/**
					 *            B -->pp
					 *      p<--R   R -->ppr
					 * node<--R  
					 * 
					 * �� ���׺��常����ɫ�޸�Ϊ��ɫ ���游����ɫ�޸�Ϊ��ɫ  ���游�ڵ���Ϊ�µ�node 
					 * �õ������ͼ
					 * 
					 *  		  R -->node(�µ�node)
					 *          B   B
					 * 	      R  
					 */
					
					//�� ���׺��常����ɫ�޸�Ϊ��ɫ
					p.color = BLACK;
					ppr.color = BLACK;
					//���游����ɫ�޸�Ϊ��ɫ
					pp.color = RED;
					
					//node��Ϊ���游�ڵ�
					node = pp;
				}
				else//�常�ڵ��Ǻ�ɫ
				{
					/**
					 *  �����node��p���Һ��������õ��ڶ������
					 *  		  B->pp
					 *       p<-R   B->ppr
					 * 	   		  R->node  
					 * 
					 *   ��p������ ��node����Ϊp ���鵽������һ�����
					 *   
					 *            B->pp
					 *       p<-R   B->ppr
					 *  node<-R  
					 * 
					 * ��pp������ת ��p��Ϊ��ɫ pp��Ϊ��ɫ ��Ϊ�������ʽ
					 *  		 B
					 *        R     R
					 *                B
					 *  
					 */
					//����node���丸�ڵ�����ӻ����Һ��ӷ�Ϊ2����� 
					//����������У����node���Һ��ӣ�����ͨ������ת����Ϊ�ڶ������---node������
					if(node == p.rchild) 
					{
						//node���Һ��ӣ�������Ϊ����
						this.LRotate(p);
						node = p;
					}
					else //node������
					{
						pp.color = RED;
						p.color = BLACK;
						this.RRotate(pp);
						
					}
				}
			}
			else//������ڵ����游�ڵ���Һ���(�ԳƵ�) ������� if(p == pp.lchild)����£�l r ����λ�ü���
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
	 * ��node��ǰ��(����)
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
		
		//�����root���⴦�� �����µ�root
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
	 * ����ת
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
		
		//�����root���⴦�� �����µ�root
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
		if(node == NIL)
			return false;
		Node parent = node.p;

		//����������Ϊ�� Ҷ�ӽڵ�
		if(node.lchild == NIL && node.rchild == NIL)
		{
			//ֻ��һ��Ԫ�� ���Ǹ�
			if(parent == null)
				this.root = null;
			else
			{
				//����������
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
		else if(node.lchild != NIL && node.rchild == NIL) //���Ӳ�Ϊ�� ����Ϊ��
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
		else if(node.lchild == NIL && node.rchild != NIL) //����Ϊ�� ���Ӳ�Ϊ��
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
	 * ��һ���ڵ�ɾ����Ľڵ�y(yһ���Ǻ�ɫ)����ɫ�ŵ�"�滻�Ľڵ�"(���ǲ���x)��,
	 * ���x�Ǻ�ɫ,��x��"���ɫ",���x�Ǻ�ɫ,x����"�ں�ɫ".
	 * @param x
	 */
	private void deleteFix(Node x)
	{
		//while x����"�ں�ɫ"
		while(x.color == BLACK && x != this.root && x!=null && x.p!=null)
		{
			Node p = x.p;     //x�ĸ���
				
			//x������
			if(x == p.lchild)
			{
				Node w = p.rchild;//w��x���ֵ�
				//"��һ����"������w����ɫ�Ǻ�ɫ
				if (w.color == RED) //��һ����� ��������������"�ڶ���"�����
				{
					w.color = BLACK;
					p.color = RED;
					this.LRotate(p); 
				}
				else //"�ڶ���"����� --WΪ��ɫ
				{
					Node wl = w.lchild;
					Node wr = w.rchild;
					
					
					if(wl.color == BLACK && wr.color == BLACK)//�ڶ������  w�������Һ��Ӷ�Ϊ��ɫ
					{
						w.color = RED;
						x=p;  //��x�Ƶ�p
					}
					else if(wr.color == BLACK) //��������� wΪ��ɫ,w������Ϊ��ɫ�Һ���Ϊ��ɫ,���Ե���Ϊ���������
					{
						wl.color = BLACK;
						w.color = RED;
						this.RRotate(w);
					}
					else//��������� wΪ��ɫ,w���Һ���Ϊ��ɫ
					{
						w.color=p.color;
						p.color=BLACK;
						wr.color = BLACK;
						this.LRotate(p);
						x = this.root;	
					}
						
				}
			}
			else //x���Һ���,��ǰ������һģһ��,�����ҵ���һ�¼���
			{

				Node w = p.lchild;//w��x���ֵ�
				//"��һ����"������w����ɫ�Ǻ�ɫ
				if (w.color == RED) //��һ����� ��������������"�ڶ���"�����
				{
					w.color = BLACK;
					p.color = RED;
					this.RRotate(p); 
				}
				else //"�ڶ���"����� --WΪ��ɫ
				{
					Node wl = w.lchild;
					Node wr = w.rchild;
					
					
					if(wr.color == BLACK && wl.color == BLACK)//�ڶ������  w�������Һ��Ӷ�Ϊ��ɫ
					{
						w.color = RED;
						x=p;  //��x�Ƶ�p
					}
					else if(wl.color == BLACK) //��������� wΪ��ɫ,w������Ϊ��ɫ�Һ���Ϊ��ɫ,���Ե���Ϊ���������
					{
						wr.color = BLACK;
						w.color = RED;
						this.LRotate(w);
					}
					else//��������� wΪ��ɫ,w���Һ���Ϊ��ɫ
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
		//���x�Ǻ�ɫ,x����"���ɫ"
		if(x!=null&&x!=NIL)
			x.color = BLACK;
	}
	
	static int count = 0;
	/**
	 * �����ӡһ����
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
		
		//�����д���
		test = new  RandomArray().disturb2(test);
		RBTree rbt = new RBTree();
		
		//��������
		rbt.createRBTree(test);
		//rbt.print(rbt.root);
		
		//����
		System.out.println("\n����="+rbt.height(rbt.root));
		
		//����test��ÿһ��,û��������ĳһ�����������������
		for(i = 0;i < length;i++)
		{
			Node n = rbt.search(test[i]);
			if(n==null)
			{
				System.out.println(test[i]+" is not in the tree");
				return;
			}
			
			//������
			if(i%2000 == 0)
				System.out.println(i+" "+test[i]+"=="+n.data+":"+(test[i]==n.data)+",");
		}
		System.out.println("ok");
		
		//ɾ�����е�Ԫ��
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
		
		//�������
		System.out.println("insert test:");
		test = new int[50];
		for(i = test.length;i < test.length*2;i++)
			test[i-test.length] = i;	
		//�����д���
		test = new  RandomArray().disturb2(test);
		for(i = 0;i < test.length;i++)
		{
			rbt.insert(test[i]);
			System.out.println(i+":"+test[i]+",countNode="+rbt.countNode(rbt.root)+", height="+rbt.height(rbt.root));
		}
	}
}
