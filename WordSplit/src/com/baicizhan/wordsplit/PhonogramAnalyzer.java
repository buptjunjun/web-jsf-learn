package com.baicizhan.wordsplit;

import java.util.ArrayList;
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
			List<String> letters = Phonogram.breakWord(ps, word.getWord());
			
			if(letters.size()!=ps.size())
			{
				System.out.println(w+"  size not match:"+ps+"|"+letters);
				return null;
			}
			List<String> phonogram = new ArrayList<String>();
			List<String> wordparts = new ArrayList<String>();
			
			int i = 0;
			while(i<ps.size())
			{
				Phonogram p1 = map.get(ps.get(i));
				Phonogram p2 =null;
				Phonogram p3 =null;
				
				if(i+1 < ps.size())
					p2 = map.get(ps.get(i+1));
				
				if(i+2 < ps.size())
					p3 = map.get(ps.get(i+2));
				
				if(p1.getType() == Phonogram.seperator)
				{	i++;
					continue;
				}
				else if(p1.getType() == Phonogram.fuyin)
				{
					String tmp = p1.getPhonogram();
					String partword = letters.get(i);
					if(p2!=null && p2.getType() == Phonogram.yuanyin)
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
			
			return word;
			
	}// end of main

}
