package com.junjun.algorithm.graphAlgrithm;

import java.util.List;

//节点的数据结构
public  class Node
{
	//节点名字
	private String name = "";
	
	//节点临边
	private List<Edge> edges = null;
	
	private int flag = 0;
	
	public Node(String name)
	{
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(name == null)
			return "null".equals(obj);
		else
			return name.equals(obj);
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