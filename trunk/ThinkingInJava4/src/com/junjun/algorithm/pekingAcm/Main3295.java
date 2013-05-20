package com.junjun.algorithm.pekingAcm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
* 
* ����һ�����͵�����ʽֵ����,������"�ж�һ�����ʽ�Ƿ��Ϊ��"
* ���ⷽ�� ��һ�����ʽ��ֵ,��һ�����뵽�ľ��Ƕ�ջ,����ӱ��ʽ�����һ��Ԫ����ǰ���У�����Ǳ�������������ջ������������������ջ��Ԫ�����������ֵ��Ȼ����ջ��ֱ���õ��������
* ����ֻ��pqrst�������һ��32(2^5)�����������32����������Ϊ������tautology
* 
* http://poj.org/problem?id=3295
*/



public class Main3295
{
	/**
	 * ���� first operator second ��ֵ ���� 1K0 ���Ϊ 0
	 * @param operator ������� 
	 * @param first    ��һ������
	 * @param second   �ڶ������� (operator Ϊnot��ʱ��, ��ʹ��second)
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
			//������ ֻ��Ҫfirst
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
	 * ����һ���߼����ʽ��ֵ
	 * @param input
	 * @return
	 */
	static public boolean caculate(String input)
	{
		Stack<String> stack = new Stack<String>();
		input = input.trim();
		//��input�����һ���ַ���ʼ�����ջ��
		for(int i = input.length()-1; i >= 0; i--)
		{
			char c = input.charAt(i);
			String current = c+"";
			
			//����Ǳ���pqrstֱ����ջ
			if("01".contains(current))
			{				
				stack.push(current);
			}
			else//������߼������
			{
				// not �����ȼ��ϸ� һԪ����
				if("N".equals(current))
				{
					String top = stack.pop();
					//���㵱ǰ���ѹ��ջ��
					String ret = caculate3(current,top,"");
					stack.push(ret);
				}
				else  //��Ԫ����
				{
					String top1 = stack.pop();
					String top2 = stack.pop();
					//���㵱ǰ���ѹ��ջ��
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
	 * �ж�һ��ʽ���Ƿ��Ǻ�Ϊ��(tautology) һ��32�����,����һ��
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
							//��pqrst��ֵ�滻Ϊ��ǰpqrst��ֵ
							String copy = input;
							copy = copy.replaceAll("p", p+"");
							copy = copy.replaceAll("q", q+"");
							copy = copy.replaceAll("r", r+"");
							copy = copy.replaceAll("s", s+"");
							copy = copy.replaceAll("t", t+"");
							//��ǰ���ʽ��ֵ
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