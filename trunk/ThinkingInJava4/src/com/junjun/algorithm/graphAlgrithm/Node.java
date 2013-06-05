package com.junjun.algorithm.graphAlgrithm;

import java.util.List;

//节点的数据结构
public  class Node implements Comparable
{
	//节点名字
	private String name = "";
	
	//节点临边
	private List<Edge> edges = null;
	
	private int flag = 0;
	
	// 用于拓扑排序
	private int visitTime = 0;
	private int beginTime = 0;
	
	public int getBeginTime()
	{
		return beginTime;
	}

	public void setBeginTime(int beginTime)
	{
		this.beginTime = beginTime;
	}

	public int getVisitTime()
	{
		return visitTime;
	}

	public void setVisitTime(int visitTime)
	{
		this.visitTime = visitTime;
	}

	public Node(String name)
	{
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		Node node = (Node)obj;
		if(name == null)
			return  node.name == name;
		else
			return name.equals(node.getName());
	}
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		if(name != null)
			return this.name.hashCode();
		else
			return this.hashCode();
	}
	
	@Override
	public int compareTo(Object arg0)
	{
		Node n = (Node)arg0;
		if(n.getVisitTime() > this.getVisitTime())
			return 1;
		else if(n.getVisitTime() < this.getVisitTime())
			return -1;
		return 0;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<Edge> getEdges()
	{
		return edges;
	}
	public void setEdges(List<Edge> edges)
	{
		this.edges = edges;
	}
	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag = flag;
	}

	
}