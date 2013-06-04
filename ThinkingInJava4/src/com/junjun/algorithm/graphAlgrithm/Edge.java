package com.junjun.algorithm.graphAlgrithm;


//边的数据结构
public class Edge
{
	//边的起始点
	Node from;
	//边的终止点
	Node to ;	
	//权值
	double weight = 0d;
	
	public Edge(double weight, Node from ,Node to)
	{
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public Node getFrom()
	{
		return from;
	}

	public void setFrom(Node from)
	{
		this.from = from;
	}

	public Node getTo()
	{
		return to;
	}

	public void setTo(Node to)
	{
		this.to = to;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

}
