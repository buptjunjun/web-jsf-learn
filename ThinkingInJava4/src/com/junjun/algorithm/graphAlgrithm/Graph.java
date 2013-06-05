package com.junjun.algorithm.graphAlgrithm;
import java.util.*;
import java.util.Map.Entry;


/**
 * 
 * 邻接表 表示一个图
 * 图的操作:邻接表建图,BFS,DFS.
 * 
 */

public class Graph
{	
	//使用邻接表来存储
	private HashMap<String,Node> graph = null;
	
	/**
	 * 返回node n的邻接边
	 * @param n
	 * @return
	 */
	public List<Edge> getAdjEdge(Node n)
	{
		if(this.graph == null)
			return null;
		
		Node node = this.graph.get(n.getName());
		if(node == null)
			return null;
		else
		{
			return node.getEdges();
		}
	}	
	
	public Node getNode(String name)
	{
		if(this.graph == null)
			return null;
	
		Node node = this.graph.get(name);
		return node;
	}
	/**
	 * 初始化一个图格式如下
	 * 
	 * nodeAmount
	 * nodeName edgeName weight edgeName weight edgeName weight ...
	 * 
		5
		1 2 0 4 0
		2 5 0
		3 6 0 5 0
		4 
		5 4 0

	 */
	public void createGraph()
	{
		this.graph = new  HashMap<String,Node> ();
		Scanner scanner = new Scanner(System.in);
		int nodeAmount = scanner.nextInt();
		String line = null;
		scanner.nextLine();
		for(int i = 0;i < nodeAmount; i++)
		{
			line = scanner.nextLine();
			line = line.trim();
			
			String [] nums = line.split("\\s+");
			String nodeName = nums[0];
			Node from = null;
			if(this.graph.containsKey(nodeName))
				from = this.graph.get(nodeName);
			else
			{
				from = new Node(nodeName);
				this.graph.put(nodeName, from);
			}
			
			List<Edge> edges = new ArrayList<Edge>();
			for( int j = 1; j < nums.length; j+=2)
			{
				Node to = null;
				if(this.graph.containsKey(nums[j]))
					to = this.graph.get(nums[j]);
				else
				{
					to = new Node(nums[j]);
					this.graph.put(nums[j], to);
				}
				double weight = Double.valueOf(nums[j+1]);
				Edge edge = new Edge(weight,from,to);
				edges.add(edge);
			}
			
			from.setEdges(edges);
		}
		
		scanner.close();
	}
	
	/**
	 * 打印邻接表
	 */
	public void print()
	{
		if(this.graph == null)
			return;
		
		for(Map.Entry entry:this.graph.entrySet())
		{
			Node node = (Node) entry.getValue();
			System.out.print(node.getName()+"->");
			List<Edge> ledge = node.getEdges();
			if(ledge!=null)
			{	
				for(Edge edge:ledge)
				{
					System.out.print(" " +edge.getTo().getName() +"(" + edge.getWeight()+")");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * 广度优先搜索
	 * @param node
	 */
	public void BFS(String name)
	{
		System.out.println("\n----BFS-----");
		if(this.graph == null)
			return;	
		
		//找到起始节点
		Node node = this.graph.get(name);
		if(node == null)
			return;
		//队列
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(node);
		System.out.print(node.getName()+" ");
		
		//层次遍历
		while(!queue.isEmpty())
		{	
			node = queue.poll();
			//node的相邻的边
			List<Edge> edges = node.getEdges();		
			
			//访问与node相邻的所有节点
			for(Edge edge: edges)
			{
				Node to = edge.getTo();
				//如果已经访问过 不再访问
				if(to.getFlag() == 1)
					continue;
				
				//访问
				System.out.print(to.getName()+" ");
				//设置为已经访问过了
				to.setFlag(1);
				queue.add(to);
			}
		}
	}
	
	/**
	 * 广度优先搜索
	 * @param node
	 */
	public void DFS(String name)
	{	System.out.println("\n----DFS-----");
		Node node = this.graph.get(name);
		dfs(node);
	}
	
	//用于拓扑排序
	static int finishtime = 0;
	static int begintime = 0;
	public void dfs(Node node)
	{	
		//已经被访问过
		if(node == null || node.getFlag() ==2 )
			return;
		
		//访问node
		System.out.print(node.getName() +" ");
		//设置为已经访问过了
		node.setFlag(2);
		begintime++;
		node.setBeginTime(begintime);
		
		//访问它的邻接节点
		List<Edge> edges = node.getEdges();		
		if(edges!=null)
			for(Edge edge: edges)
			{
				Node to = edge.getTo();
				dfs(to);
			}
		
		finishtime++;
		node.setVisitTime(finishtime);
	}
	
	
	/**
	 * 拓扑排序,使用dfs
	 * 其中 
	 * begintime 表示开始访问的时间
	 * finishtime 表示这个节点及其以后的节点都访问完的时间
	 * 最后节点以finishtime排序 finishtime 按从大到小排序
	 */
	public void toplogic_sort()
	{
		System.out.println("\n----toplogic sort-----");
		List<Node> l = new LinkedList<Node>();

		for(Entry entry:this.graph.entrySet())
		{
			Node node = (Node) entry.getValue();
			if(node.getFlag()!=2)
			{
				this.dfs(node);
			}
			l.add(node);
		}
		
		Collections.sort(l);
		for(Node node:l)
		{
			System.out.print("("+node.getName()+":"+node.getBeginTime()+","+node.getVisitTime()+")  ");
		}
		
	}
	
	/**
	 * 寻找最大联通分量
	 * @param name
	 */
	//low(u) 用来存储u或者u的子树能够追溯到的子树根节点号。
	Map<Node,Integer> low = new HashMap<Node,Integer>();
	
	//dfn(u) 点u搜索的次序编号,时间戳
	Map<Node,Integer> dfn = new HashMap<Node,Integer>();
	Stack<Node> stack = new Stack<Node>();
	int index = 0;
	public void tarjan(Node u)
	{
		stack.add(u);                      //新节点压入栈
		++index;
		//确定新节点的编号和low值
		low.put(u, index);
		dfn.put(u, index);
		//表示访问过的
		u.setFlag(3);
		
		//遍历每一条边
		List<Edge> edges = u.getEdges();
		if(edges!=null)
		{
			for(Edge edge:edges)
			{
				Node v = edge.getTo();
				//如果v是访问过的(flag == 3)
				if(v.getFlag() != 3)
				{
					tarjan(v);
					
					int lowu = low.get(u);
					int lowv = low.get(v);
					int lower = lowu < lowv ? lowu:lowv;
					low.put(u, lower);
					
				}
				else if(stack.contains(v)) 
				{
					int lowu = low.get(u);
					int dfnv = dfn.get(v);
					int lower = lowu < dfnv ? lowu:dfnv;
					low.put(u, lower);
				}
			}
		}
		
		int lowu = low.get(u);
		int dfnu = dfn.get(u);
		if(dfnu == lowu)
		{
			
		
			System.out.print("(");
			while(true)
			{			
				Node top = stack.pop();
				System.out.print(top.getName()+" ");
				if(top.equals(u))
					break;
			}
			System.out.print(") \n");
		}
	}
	
	
	/**
	 * 最大强连通分量 Strongly connected component
	 */
	public void SCC()
	{
		System.out.println("\n----scc-----");
		List<Node> l = new LinkedList<Node>();

		for(Entry entry:this.graph.entrySet())
		{
			Node node = (Node) entry.getValue();
			if(node.getFlag()!=3)
			{
				tarjan(node);
			}
		}
		
	}
	public static void main(String [] args)
	{
		Graph g = new Graph();
		g.createGraph();
		g.print();
		g.BFS("1");
		//g.DFS("1");
		//g.toplogic_sort();
		g.SCC();
	}
}
