package com.junjun.algorithm.pekingAcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
* 
*  ģ�ⷨ:��ͨ��P-sequence �ؽ����Ŵ����ٸ������Ŵ����õ� W-sequence
*  http://poj.org/problem?id=1068
* 
*/



public class Main1068
{
	/**
	 * ���� ���Ŵ����õ�W-sequence
	 * ����(((()()()))) ��W-sequence Ϊ111456
	 * �㷨:����ʽֵ�ĵ����㷨���ö�ջ
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
	 * ��ͨ��P-sequence �ؽ����Ŵ�
	 * ����    4 5 6666��Ӧ�����Ŵ�Ϊ(((()()())))
	 * @param ps
	 * @return ���Ŵ�
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