package com.junjun.algorithm.pekingACM;

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

/**
 * �������
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
	 * ���� ���Ŵ����õ�W-sequence
	 * ����(((()()()))) ��W-sequence Ϊ111456
	 * �㷨:����ʽֵ�ĵ����㷨���ö�ջ
	 * 	         ��ջ��ֻ����������(Leftbrace),count�д�����������������Ե�����,����((())())����ڶ���������Ӧ�ô��2--(())
	 *      ÿ������һ��")"������ջ�е�"(",�����ջ�л���"(",����ǰ������"("�е�count���뵽ջ��
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
			//�����"("ֱ��ѹջ
			if('(' == cur)
			{
				stack.push(new Leftbrace());		
			}
			else //�����")"
			{
				//��ջ
				Leftbrace curlb = stack.pop();	
				//�����ջ�л���"(",����ǰ������"("�е�count���뵽ջ��
				if(stack.size()>0)
					stack.peek().count+=curlb.count;
				//����һ��������
				ws.add(curlb.count);
			}
			
		}
		
		//ͳ�ƽ��
		StringBuffer sb = new StringBuffer();
		for(int i:ws)
		{
			sb.append(i+" ");
		}
		
		return sb.toString();			
	}
	
	/**
	 * ��ͨ��P-sequence �ؽ����Ŵ�
	 * ����    4 5 6666��Ӧ�����Ŵ�Ϊ(((()()())))
	 * @param ps
	 * @return ���Ŵ�
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