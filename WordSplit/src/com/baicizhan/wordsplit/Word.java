package com.baicizhan.wordsplit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word {
	private String word;
	private String phonogram;
	private String meaning;
	
	private List<String> phongrams;
	private List<String> letters;
	
	public Word(String word, String phonogram,String meaning)
	{
		this.word = word;
		this.phonogram = phonogram;
		this.meaning = meaning;
	}


	static public List<Word> parseWord(String file)
	{
		
		List<Word> ret = new ArrayList<Word>();
		Pattern p = Pattern.compile("(.*)/(.*)/(.*)");
		Pattern p1 = Pattern.compile("\\(.*\\)");
		
		BufferedReader br = null;
		OutputStreamWriter osw = null;
		
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			
			String line = br.readLine();
			while (line != null)
			{
				System.out.println(line);
				Matcher m = p.matcher(line);
				if(m.matches())
				{
					String [] splits = line.split("/");
					String word = splits[0];
					String phonogram = splits[1];	
					String meaning = splits[2];		
					
					String str = new String(",".getBytes(),"UTF-8");
					phonogram = phonogram.split(str)[0];
					Matcher m1 = p1.matcher(phonogram);
					phonogram = m1.replaceAll("");
					ret.add(new Word(word.trim(),phonogram.trim(),meaning.trim()));
					
				}				
				line = br.readLine();
				m = null;
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
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPhonogram() {
		return phonogram;
	}

	public void setPhonogram(String phonogram) {
		this.phonogram = phonogram;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	
	@Override
	public String toString() 
	{
		return this.word+"/"+this.phonogram+"/"+this.meaning;
	}
	
	static public void main(String [] args)
	{
		String file = "C:\\Users\\junjun\\Desktop\\cet4word.txt";
		String file1 = "C:\\Users\\junjun\\Desktop\\cet4word1.txt";
		Pattern p = Pattern.compile("(.*)/(.*)/(.*)");
		
		BufferedReader br = null;
		OutputStreamWriter osw = null;
		
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			osw = new OutputStreamWriter(new FileOutputStream(file1, true),"UTF-8");
			
			
			String line = br.readLine();
			while (line != null)
			{
				System.out.println(line);
				Matcher m = p.matcher(line);
				if(m.matches())
				{
					String [] splits = line.split("/");
					String word = splits[0];
					String phonogram = splits[1];	
					String meaning = splits[2];		
					osw.write(new Word(word.trim(),phonogram.trim(),meaning.trim()).toString()+"\n");
					osw.flush();
				}				
				line = br.readLine();
				m = null;
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


	public List<String> getPhongrams() {
		return phongrams;
	}


	public void setPhongrams(List<String> phongrams) {
		this.phongrams = phongrams;
	}


	public List<String> getLetters() {
		return letters;
	}


	public void setLetters(List<String> letters) {
		this.letters = letters;
	}
	
}
