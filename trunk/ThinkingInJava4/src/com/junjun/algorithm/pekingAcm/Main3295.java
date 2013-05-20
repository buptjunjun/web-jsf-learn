package com.junjun.algorithm.pekingAcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
* 
* 这是一个典型的求表达式值问题,问题是"判断一个表达式是否恒为真"
* 解题方法 求一个表达式的值,第一个个想到的就是堆栈,这里从表达式的最后一个元素往前进行：如果是变量，将变量入栈，如果是运算符，计算栈顶元素与运算符的值，然后入栈，直到得到最后结果。
* 由于只有pqrst五个变量一共32(2^5)种情况，遍历32种情况如果都为真则是tautology
* 
* http://poj.org/problem?id=3295
*/



public class Main3295
{
	/**
	 * 计算 first operator second 的值 比如 1K0 结果为 0
	 * @param operator 运算符号 
	 * @param first    第一个变量
	 * @param second   第二个变量 (operator 为not的时候, 不使用second)
	 * @return
	 */
	static public String caculate3(String operator,String first,String second)
	{
		String ret = 1+"";
		if("K".equals(operator)) //and 
		{
			if(first.equals("0") || second.equals("0"))
				ret="0";
			else ret="1";
		}
		else if("A".equals(operator))//or 
		{
			if(first.equals("1") || second.equals("1"))
				ret="1";
			else ret="0";
		}
		else if("N".equals(operator))//not
		{
			//非运算 只需要first
			if("0".equals(first)) ret = "1";
			else ret = "0";
		}
		else if("C".equals(operator))//imply
		{
			if(first.equals(second) || first.equals("1"))
				ret = "1";
			else ret="0";
		}
		else if("E".equals(operator))//equal
		{
			if (first.equals(second))
				ret = "1";
			else ret = "0";
		}
		
		return ret;
	}
	
	/**
	 * 计算一个逻辑表达式的值
	 * @param input
	 * @return
	 */
	static public boolean caculate(String input)
	{
		Stack<String> stack = new Stack<String>();
		input = input.trim();
		//将input从最后一个字符开始加入堆栈中
		for(int i = input.length()-1; i >= 0; i--)
		{
			char c = input.charAt(i);
			String current = c+"";
			
			//如果是变量pqrst直接入栈
			if("01".contains(current))
			{				
				stack.push(current);
			}
			else//如果是逻辑运算符
			{
				// not 的优先级较高 一元运算
				if("N".equals(current))
				{
					String top = stack.pop();
					//计算当前结果压入栈中
					String ret = caculate3(current,top,"");
					stack.push(ret);
				}
				else  //二元运算
				{
					String top1 = stack.pop();
					String top2 = stack.pop();
					//计算当前结果压入栈中
					String ret = caculate3(current,top1,top2);
					stack.push(ret);
				}
			}
			
		}
		
		String result = stack.pop();
		if(result.equals("1"))
			return true;
		return false;
	}
	
	
	/**
	 * 判断一个式子是否是恒为真(tautology) 一共32中情况,遍历一遍
	 * @param input
	 * @return
	 */
	static public boolean iftautology (String input)
	{
		for(int p = 0;p <=1; p++)
			for(int q = 0;q <=1; q++)
				for(int r = 0;r <=1; r++)
					for(int s = 0;s <=1; s++)
						for(int t = 0;t <=1; t++)
						{	
							//将pqrst的值替换为当前pqrst的值
							String copy = input;
							copy = copy.replaceAll("p", p+"");
							copy = copy.replaceAll("q", q+"");
							copy = copy.replaceAll("r", r+"");
							copy = copy.replaceAll("s", s+"");
							copy = copy.replaceAll("t", t+"");
							//当前表达式的值
							boolean result = caculate(copy);
							if(result == false)
								return false;
						}
		return true;
	}
	
	public static void main(String[] args)
	{
		
		List<String> datas = new ArrayList<String>();
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		while(!line.startsWith("0"))
		{
			datas.add(line.trim());
			line = scan.nextLine();
		}
		scan.close();
		for(String input:datas)
		{
			if(iftautology(input))
				System.out.println("tautology");
			else
				System.out.println("not");
		}
	}
}