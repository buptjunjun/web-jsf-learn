package com.baicizhan.wordsplit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class Phonogram {
	
	public static final int yuanyin = 0;
	public static final int fuyin   = 1;
	public static final int seperator = 2;
	
	private String phonogram;
	private int type = -1;
	private List<String> letters = null;
	
	
	public Phonogram(String phonogram, int type, List<String> letters) {
		this.phonogram = phonogram;
		this.type = type;
		this.letters = letters;
	}
	static Map<String, Phonogram> map = parsePhonogram("C:\\Users\\junjun\\Desktop\\yinbiao.txt");

	static public List<String> breakPhonograms(String p)
	{		
		
		List<String> ret = new ArrayList<String>();
			
		int i = 0;
		while(i < p.length())
		{
			String p1 = p.substring(i,i+1);
			String p2 = null;
			String p3 = null;
			if(i+3 <= p.length())
				 p3 = p.substring(i,i+3);
			
			if(i+2 <= p.length())
				 p2 = p.substring(i,i+2);
			
			if(map.containsKey(p3))
			{
				i+=3;
				ret.add(p3);
			}
			else if(map.containsKey(p2))
			{
				i+=2;
				ret.add(p2);
			}
			else if(map.containsKey(p1))
			{
				i++;
				ret.add(p1);
			}
			else
			{
				System.out.println("no matches");
				ret.add("no matches");
				break;
			}
		}
		return ret;
	}
	
	static int trycount = 0; //如果没有匹配的 比如不发声的 try3次
	static public List<String> breakWord(List<String> phonograms,String word,List<String> ret ,int phonindex,int wordindex,Boolean finished)
	{	
		
		try{
			if(wordindex >= word.length()|| finished == true || phonindex >= phonograms.size())
			{
				return ret;
			}
				
			if (phonindex < phonograms.size())
			{
				String phonogram = phonograms.get(phonindex);
				Phonogram p = map.get(phonogram);
				if(p.getType() == Phonogram.seperator)
				{
					ret.set(phonindex,p.getPhonogram());
					phonindex++;
					breakWord(phonograms, word, ret,phonindex,wordindex ,finished);
				}
	
				String w1 = null;
				String w2 = null;
				String w3 = null;
				if(wordindex+1<=word.length())
					w1 = word.substring(wordindex,wordindex+1);	
				if(wordindex+2<=word.length())
					w2 = word.substring(wordindex,wordindex+2);
				if(wordindex+3<=word.length())
					w3 = word.substring(wordindex,wordindex+3);
				
				if(w3 !=null && p.getLetters().contains(w3))
				{
					
					ret.set(phonindex,w3);
					if(phonindex == phonograms.size()-1)
					{
						finished = true;
						return ret;
					}
					
					breakWord(phonograms, word, ret,phonindex+1,wordindex+3,finished);
				}

				if(wordindex >= word.length()|| finished == true || phonindex >= phonograms.size())
				{
					return ret;
				}
				if(w2 !=null && p.getLetters().contains(w2))
				{
					ret.set(phonindex,w2);
					if(phonindex == phonograms.size()-1)
					{
						finished = true;
						return ret;
					}
					
					breakWord(phonograms, word, ret,phonindex+1,wordindex+2,finished);
				}
				
				if(wordindex >= word.length()|| finished == true || phonindex >= phonograms.size())
				{
					return ret;
				}
				
				if(w1 !=null && p.getLetters().contains(w1))
				{
					ret.set(phonindex,w1);
					if(phonindex == phonograms.size()-1)
					{
						finished = true;
						return ret;
					}
					
					breakWord(phonograms, word, ret,phonindex+1,wordindex+1,finished);				
				}
				
				if(wordindex >= word.length()|| finished == true || phonindex >= phonograms.size())
				{
					return ret;
				}
				
				if(!p.getLetters().contains(w1) && !p.getLetters().contains(w2)&&!p.getLetters().contains(w3))
				{
					breakWord(phonograms, word, ret,phonindex,wordindex+1,finished);
				}
				
				if(wordindex >= word.length()|| finished == true || phonindex >= phonograms.size())
				{
					return ret;
				}
			}
			
			return ret;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ret.add("-");
			return ret;
		}
	}
	
	static public Map parsePhonogram(String file)
	{	
		Pattern p = Pattern.compile(" +");		
		BufferedReader br = null;
		Map<String, Phonogram> ret = new HashMap<String, Phonogram>();
				
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			
			int count = 1;
			
			String line = br.readLine();
			int type = Phonogram.yuanyin;
			
			while (line != null)
			{
				System.out.println(line);
				
				if (line.startsWith("fuyin"))
				{
					type = Phonogram.fuyin;
					line = br.readLine();
					continue;
				}
				else if (line.startsWith("seperator"))
				{
					type = Phonogram.seperator;
					line = br.readLine();
					continue;
				}
					
				String [] splits = line.split("/");
				String phonogram = splits[0].trim();
				List<String> letters = 	Arrays.asList(p.split(splits[1].trim()));
				ret.put(phonogram, new Phonogram(phonogram,type,letters));				
				count++;
				line = br.readLine();				
			}
			return ret;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		return null;
	}
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		if (this.phonogram == null) return 1;
		else return this.phonogram.hashCode();
	}
	
	@Override
	public boolean equals(Object arg0) {
		
		if (arg0 == null)
		{
			System.out.println("arg0 == null");
			return false;
		}
		
		if (String.class.isInstance(arg0) && this.phonogram.equals(arg0))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		
		return this.phonogram +"/"+this.type+"/"+this.letters.toString();
	}
	static public void main(String [] args)
	{
		List<Word> lword = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
		File file = new File("C:\\Users\\junjun\\Desktop\\result.txt");
		int i = 0;
		for(Word word: lword)
		{
			List<String> l = Phonogram.breakPhonograms(word.getPhonogram());
			List<String> tmpletters = new ArrayList<String>();
			Boolean finished = false;
			List<String> letters = breakWord(l,word.getWord(),tmpletters,0,0,finished);
			try {
				FileUtils.writeStringToFile(file, word.getWord()+"| " +word.getPhonogram()+" | "+l+"|"+letters+"\n", "UTF-8", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			i++;
			if(i==600)
				break;
		}
		
	}
	
	public void main1(String [] args)
	{
		String file = "C:\\Users\\junjun\\Desktop\\yinbiao.txt";
		String file1 = "C:\\Users\\junjun\\Desktop\\yinbiao1.txt";
		Pattern p = Pattern.compile(" +");
		
		BufferedReader br = null;
		OutputStreamWriter osw = null;
		
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			osw = new OutputStreamWriter(new FileOutputStream(file1, true),"UTF-8");
			
			int count = 0;
			
			String line = br.readLine();
			int type = Phonogram.yuanyin;
			
			while (line != null)
			{
				System.out.println(line);
				
				if (count > 20)
					type = Phonogram.fuyin;
				
				String [] splits = line.split("/");
				String phonogram = splits[0].trim();
				List<String> letters = 	Arrays.asList(p.split(splits[1].trim()));
				osw.write(new Phonogram(phonogram,type,letters).toString()+"\n");
				osw.flush();
				
				count++;
				line = br.readLine();
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				br.close();
				osw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	public String getPhonogram() {
		return phonogram;
	}

	public void setPhonogram(String phonogram) {
		this.phonogram = phonogram;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<String> getLetters() {
		return letters;
	}

	public void setLetters(List<String> letters) {
		this.letters = letters;
	}
	
}
