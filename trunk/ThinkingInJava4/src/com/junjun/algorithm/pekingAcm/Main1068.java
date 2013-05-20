package com.junjun.algorithm.pekingAcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
* 
*  模拟法:先通过P-sequence 重建括号串，再根据括号串来得到 W-sequence
*  http://poj.org/problem?id=1068
* 
*/



public class Main1068
{
	/**
	 * 根据 括号串来得到W-sequence
	 * 例如(((()()()))) 的W-sequence 为111456
	 * 算法:求表达式值的典型算法：用堆栈
	 * @param input
	 * @return
	 */
	static String  getWsequence(String input)
	{
		Stack<String> stack = new Stack<String>();
		input = input.trim();
		List<Integer> ws = new ArrayList<Integer>();
		int count = 0;
		int preStackSize = ws.size();
		int begin = 0;
		for(char c: input.toCharArray())
		{
			String cur = c+"";
			if("(".equals(cur))
			{
				stack.push(cur);
				preStackSize = stack.size();
			}
			else
			{
				String top = stack.peek();
				if(stack.size() < preStackSize)
				{
					int total = 0;
					for(int i = begin;i < ws.size();i++)
						total += ws.get(i);
					total++;
					
					ws.add(total);
					begin = ws.size()-1;
				}
				else 
				{
					ws.add(1);
					
				}
				stack.pop();
				
			}
			
		}
		
		return ws.toString();
			
			
	}
	
	/**
	 * 先通过P-sequence 重建括号串
	 * 例如    4 5 6666对应的括号串为(((()()())))
	 * @param ps
	 * @return 括号串
	 */
	static public String reconstruct(String ps)
	{
		ps = ps.trim();
		String ret = "";
		StringBuffer sb = new StringBuffer();
		int currentLeft = 0;
		for(char c:ps.toCharArray())
		{
			int countleft = Integer.valueOf(c+"");
			int newLeftParenthesis  = countleft-currentLeft;
			for(int i = 0;i<newLeftParenthesis;i++)
			{
				sb.append("(");
				currentLeft++;
				
			}
			sb.append(")");
			
		}
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		
		String str = reconstruct("466668999");
		System.out.println(str);
		str = getWsequence(str);
		System.out.println(str);
	}
}