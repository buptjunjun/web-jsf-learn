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
*  ���ڴ�����:��������
*  ���� ore 
*  _o_r_e_
*  0123456
*  ������7��λ����Ҫ����(02,4,6ֻ�ܲ���������Բ������ɾ�������滻)
*  ���ǿ��Ա����ش����������  
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
	//����һ��set�жϴ�������Ƿ���set��
	static public HashMap<String,Integer> hash = new HashMap<String,Integer>();
	
	/**
	 * ��ʼ��set
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
	 * ����ÿһ����correct�ĵ���
	 * @param input
	 * @return ����corrected�ĺ�Ć��~ ����set�Ǡ���ȥ�����}
	 */
	static public Set<Word> work(String input)
	{
		Set<Word> r = new HashSet<Word>();
		// �����set���� ֱ�Ӵ�ӡ��������
		if(hash.containsKey(input))
		{
			return null;
		}
				
		/*
		 *    
		 *    ���� ore
		 *    _o_r_e_
		 *    0123456
		 *    
		 *    "_"���ǳ�����ż��λ��,ֻ��ѡ�����
		 */
		StringBuffer ret = new StringBuffer( input+":");
		for(int i = 0;i <=2*input.length();i++)
		{
			int pos = i/2;
			//ż��λ��ֻ�ܲ�����߷���
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
					//������֮��ǵ�ɾ��
					tmpsb.delete(pos,pos+1);
				}
			}
			else//����λ�� ����ɾ�����滻
			{
				//ɾ��pos�ϵ�ֵ
				StringBuffer tmpsb = new StringBuffer(input);
				tmpsb.delete(pos, pos+1);
				if(hash.containsKey(tmpsb.toString()))
				{
					int number = hash.get(tmpsb.toString());
					r.add(new Word(number,tmpsb.toString()));
				}
				
				//�滻
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
			//ȥ��	
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