package com.junjun.algorithm.pekingACM;

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

/**
 * 左大括号
 */
class Leftbrace 
{
	public int count = 1;
}
public class Main1068
{
	static Stack<Leftbrace> stack = new Stack<Leftbrace>();
	static  List<Integer> ws = new ArrayList<Integer>();
	
	/**
	 * 根据 括号串来得到W-sequence
	 * 例如(((()()()))) 的W-sequence 为111456
	 * 算法:求表达式值的典型算法：用堆栈
	 * 	         堆栈中只存放左大括号(Leftbrace),count中存放这个括号中所有配对的括号,例如((())())例如第二个左括号应该存放2--(())
	 *      每当遇到一个")"弹出堆栈中的"(",如果堆栈中还有"(",将当前弹出的"("中的count加入到栈顶
	 * @param input
	 * @return
	 */
	static String  getWsequence(StringBuffer input)
	{
		stack.clear();
		ws.clear();
		for(int i = 0;i < input.length();i++)
		{
			
			char cur = input.charAt(i);
			//如果是"("直接压栈
			if('(' == cur)
			{
				stack.push(new Leftbrace());		
			}
			else //如果是")"
			{
				//弹栈
				Leftbrace curlb = stack.pop();	
				//如果堆栈中还有"(",将当前弹出的"("中的count加入到栈顶
				if(stack.size()>0)
					stack.peek().count+=curlb.count;
				//加入一个到队列
				ws.add(curlb.count);
			}
			
		}
		
		//统计结果
		StringBuffer sb = new StringBuffer();
		for(int i:ws)
		{
			sb.append(i+" ");
		}
		
		return sb.toString();			
	}
	
	/**
	 * 先通过P-sequence 重建括号串
	 * 例如    4 5 6666对应的括号串为(((()()())))
	 * @param ps
	 * @return 括号串
	 */
	static public StringBuffer reconstruct(int [] ps)
	{
		StringBuffer sb = new StringBuffer();
		int currentLeft = 0;
		for(int countleft:ps)
		{
			int newLeftParenthesis  = countleft-currentLeft;
			for(int i = 0;i<newLeftParenthesis;i++)
			{
				sb.append("(");
				currentLeft++;
				
			}
			sb.append(")");
			
		}
		return sb;
	}
	
	public static void main(String[] args)
	{
		StringBuffer sb = null;
		Scanner scan = new Scanner(System.in);
		List<String> datas = new ArrayList<String>();
		String line = scan.nextLine();
		int n = Integer.valueOf(line);
		for(int i = 0; i < n; i++)
		{
			int numcounts = scan.nextInt();
			int [] nums = new int[numcounts];
			for(int j = 0;j < numcounts;j++)
			{
				int cur = scan.nextInt();
				nums[j] = cur;
			}
			sb= reconstruct(nums);
			System.out.println(getWsequence(sb));
		}
		scan.close();
		/*String str = null;
		for(String data:datas)
		{
			str = reconstruct(data);
			System.out.println(getWsequence(str));
			str = null;
		}*/
	/*//	String str = reconstruct("466668999");
		String str = "(()(()))(()())()";
		System.out.println(str);
		str = getWsequence(str);
		System.out.println(str);*/
	}
}