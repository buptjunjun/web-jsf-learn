package com.junjun.algorithm.graphAlgrithm;
import java.util.*;


/**
 * 
 * �ڽӱ� ��ʾһ��ͼ
 * ͼ�Ĳ���:�ڽӱ�ͼ,BFS,DFS.
 * 
 */

public class Graph
{	
	//ʹ���ڽӱ����洢
	private HashMap<String,Node> graph = null;
	
	/**
	 * ����node n���ڽӱ�
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
	 * ��ʼ��һ��ͼ��ʽ����
	 * 
	 * nodeAmount
	 * nodeName edgeName weight edgeName weight edgeName weight ...
	 * 
		5
		1 2 0 4 0
		2 5 0
		3 6 0 5 0
		4 2 0
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
	 * ��ӡ�ڽӱ�
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
	 * �����������
	 * @param node
	 */
	public void BFS(String name)
	{
		System.out.println("\n----BFS-----");
		if(this.graph == null)
			return;	
		
		//�ҵ���ʼ�ڵ�
		Node node = this.graph.get(name);
		if(node == null)
			return;
		//����
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(node);
		System.out.print(node.getName()+" ");
		
		//��α���
		while(!queue.isEmpty())
		{	
			node = queue.poll();
			//node�����ڵı�
			List<Edge> edges = node.getEdges();		
			
			//������node���ڵ����нڵ�
			for(Edge edge: edges)
			{
				Node to = edge.getTo();
				//����Ѿ����ʹ� ���ٷ���
				if(to.getFlag() == 1)
					continue;
				
				//����
				System.out.print(to.getName()+" ");
				//����Ϊ�Ѿ����ʹ���
				to.setFlag(1);
				queue.add(to);
			}
		}
	}
	
	/**
	 * �����������
	 * @param node
	 */
	public void DFS(String name)
	{	System.out.println("\n----DFS-----");
		Node node = this.graph.get(name);
		dfs(node);
	}
	
	public void dfs(Node node)
	{	
		//�Ѿ������ʹ�
		if(node == null || node.getFlag() ==2 )
			return;
		
		//����node
		System.out.print(node.getName() +" ");
		//����Ϊ�Ѿ����ʹ���
		node.setFlag(2);
		
		//���������ڽӽڵ�
		List<Edge> edges = node.getEdges();		
		for(Edge edge: edges)
		{
			Node to = edge.getTo();
			dfs(to);
		}
		
	}
	
	
	public static void main(String [] args)
	{
		Graph g = new Graph();
		g.createGraph();
		g.print();
		g.BFS("1");
		g.DFS("1");
	}
}
