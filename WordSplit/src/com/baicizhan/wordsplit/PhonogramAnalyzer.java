package com.baicizhan.wordsplit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhonogramAnalyzer {

	public static Map<String,Phonogram> map =  Phonogram.parsePhonogram("C:\\Users\\junjun\\Desktop\\yinbiao.txt");
	public static List<Word> words = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
	public static Map<String,Word> wordsmap = new HashMap<String,Word>();
	static 
	{
		for(Word w :words)
		{
			wordsmap.put(w.getWord(), w);
		}
	}
	static public Word analyze (String w)
	{
			Word word = wordsmap.get(w);
			if(word == null)
				return null;
			
			List<String> ps = Phonogram.breakPhonograms(word.getPhonogram());
			List<String> tmpletters = new ArrayList<String>(Arrays.asList(new String[ps.size()]));
			Boolean finished = false;
			List<String> letters = Phonogram.breakWord(ps, word.getWord(),tmpletters,0,0,finished);			
			if(letters.size()!=ps.size() || letters.toString().contains("-")||letters.toString().contains("null"))
			{
				System.out.println(w+" error or  size not match:"+ps+"|"+letters);
				return null;
			}
			List<String> phonogram = new ArrayList<String>();
			List<String> wordparts = new ArrayList<String>();
			
			int i = 0;
			while(i<ps.size())
			{
				// 取得打散后的第i i+1,i+2个音标
				Phonogram p1 = map.get(ps.get(i));
				Phonogram p2 =null;
				Phonogram p3 =null;
				
				if(i+1 < ps.size())
					p2 = map.get(ps.get(i+1));
				
				if(i+2 < ps.size())
					p3 = map.get(ps.get(i+2));
				
				//是分隔符号
				if(p1.getType() == Phonogram.seperator)
				{	i++;
					continue;
				}
				else if(p1.getType() == Phonogram.fuyin)//辅音音标
				{
					String tmp = p1.getPhonogram();
					String partword = letters.get(i);
					if(p2!=null && p2.getType() == Phonogram.yuanyin)//看看后面有没有元音音标
					{
						partword += letters.get(i+1);
						tmp+=p2.getPhonogram();
						i+=2;
					}
					else
					{
						i++;
					}
					phonogram.add(tmp);
					wordparts.add(partword);
				}
				else if(p1.getType() == Phonogram.yuanyin)
				{
					String partword =  letters.get(i);
					wordparts.add(partword);
					
					phonogram.add(p1.getPhonogram());
					i++;
				}
				
				
			}// end of while
			
			word.setPhongrams(phonogram);
			word.setLetters(wordparts);
			
			word = remerge(word);
			return word;
			
	}// end of main

	static public Word remerge(Word word)
	{
		StringBuffer w = new StringBuffer(word.getWord());
		List<String> p = word.getPhongrams();
		List<String> letters = word.getLetters();
		
		int i = 0;
		int pre = 0;
		while(i<letters.size())
		{
			try{
			String l1 = letters.get(i);
			String l2 = null;
			
			if(i+1<letters.size())
				l2 = letters.get(i+1);
			
			int index1 = w.indexOf(l1, pre);
			if(i == 0) index1 = 0;    //如果是第一次 从第一个元素开始
			
			int index2 = -1;
			if(l2!=null)
				index2 = w.indexOf(l2, pre);
			else
				index2 = w.length();
			
			String letteri = w.substring(index1, index2);
			letters.set(i, letteri);
			i++;
			pre = index2;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("###"+word.getWord()+"|"+word.getPhonogram()+"|"+word.getPhongrams()+"|"+word.getLetters()+"\n");
				return null;
			}	
		}
		
		word.setLetters(letters);
		
		return word;
	}
}
