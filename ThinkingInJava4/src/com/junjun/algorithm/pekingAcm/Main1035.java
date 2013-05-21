package com.junjun.algorithm.pekingAcm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
* 
*  关于串的题:暴力搜索
*  例如 ore 
*  _o_r_e_
*  0123456
*  我们有7个位置需要操作(02,4,6只能插入其余可以插入可以删除可以替换)
*  我们可以暴力地处理所有情况  
*  http://poj.org/problem?id=1035
* 
*/

class Word implements Comparable
{
	public String word="";
	public int number = 0;
	public Word(int num,String word)
	{
		this.number = num;
		this.word = word;
	}
	@Override
	public int compareTo(Object arg0)
	{
		// TODO Auto-generated method stub
		Word w = (Word)arg0;
		if(this.number == w.number)
			return 0;
		else if(this.number > w.number)
			return 1;
		else return -1;
	}
	
	
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return  this.word.hashCode();
	}
	
	@Override
	public boolean equals(Object arg0)
	{
		// TODO Auto-generated method stub
		Word w = (Word)arg0;
		return this.word.equals(w.word);
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return this.word;
	}
	
}

public class Main1035
{
	//建立一个set判断处理过后是否在set中
	static public HashMap<String,Integer> hash = new HashMap<String,Integer>();
	
	/**
	 * 初始化set
	 * @param scan
	 */
	static void initSet(Scanner scan)
	{
		String line = scan.nextLine();
		int i = 0;
		while(!line.startsWith("#"))
		{
			hash.put(line,i++);
			line = scan.nextLine();
		}
	}
	
	/**
	 * 
	 * 处理每一个待correct的单词
	 * @param input
	 * @return 返回corrected的后的單詞 返回set是爲了去除重複
	 */
	static public Set<Word> work(String input)
	{
		Set<Word> r = new HashSet<Word>();
		// 如果在set中有 直接打印出来返回
		if(hash.containsKey(input))
		{
			return null;
		}
				
		/*
		 *    
		 *    例如 ore
		 *    _o_r_e_
		 *    0123456
		 *    
		 *    "_"总是出现在偶数位置,只能选择插入
		 */
		StringBuffer ret = new StringBuffer( input+":");
		for(int i = 0;i <=2*input.length();i++)
		{
			int pos = i/2;
			//偶数位置只能插入或者放弃
			if(i%2 == 0)
			{			
				StringBuffer tmpsb = new StringBuffer(input);
				
				for(char c= 'a';c <='z';c++)
				{									
					tmpsb.insert(pos, c+"");
					if(hash.containsKey(tmpsb.toString()))
					{
						int number = hash.get(tmpsb.toString());
						r.add(new Word(number,tmpsb.toString()));
					}
					//插入了之后记得删除
					tmpsb.delete(pos,pos+1);
				}
			}
			else//奇数位置 可以删除和替换
			{
				//删除pos上的值
				StringBuffer tmpsb = new StringBuffer(input);
				tmpsb.delete(pos, pos+1);
				if(hash.containsKey(tmpsb.toString()))
				{
					int number = hash.get(tmpsb.toString());
					r.add(new Word(number,tmpsb.toString()));
				}
				
				//替换
				tmpsb = new StringBuffer(input);
				for(char c= 'a';c <='z';c++)
				{					
					tmpsb.replace(pos, pos+1, c+"");
					if(hash.containsKey(tmpsb.toString()))
					{
						int number = hash.get(tmpsb.toString());
						r.add(new Word(number,tmpsb.toString()));
					}
					
				}
				
			}
				
		}
		
		return r;
	}
	
	public static void main(String[] args)
	{
		
		Scanner scan = new Scanner(System.in);
		initSet(scan);
		
		String line = scan.nextLine();
		while(!line.startsWith("#"))
		{
			Set<Word> ret = work(line.trim());
			//去重	
			if(ret == null)
				System.out.println(line+" is correct");
			else
			{
				StringBuffer sb = new StringBuffer(line+":");
				Object []  retArray = ret.toArray();
				Arrays.sort(retArray);
				for(Object o:retArray)
				{
					sb.append(" "+o);
				}
				
				System.out.println(sb);
			}
			
			line = scan.nextLine();
		}
		scan.close();
	
		
		/*String s = "abc";
		StringBuffer sb = new StringBuffer(s);
		sb.delete(1, 2);
		System.out.println(sb);*/
	}
}