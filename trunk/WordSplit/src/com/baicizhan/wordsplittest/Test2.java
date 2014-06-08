package com.baicizhan.wordsplittest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.baicizhan.wordsplit.Phonogram;
import com.baicizhan.wordsplit.PhonogramAnalyzer;
import com.baicizhan.wordsplit.Word;

public class Test2 {

	@Before
	public void setUp() throws Exception {
	}

	//@Test
	public void test() throws IOException {
		 List<Word> words = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
		 File file = new File("C:\\Users\\junjun\\Desktop\\result.txt");
		for(Word word: words)
		{
			if(word.getWord().equals("altitude"))
				System.out.println();
			
			List<String> ret = Phonogram.breakPhonograms(word.getPhonogram());
			FileUtils.writeStringToFile(file,word+" | "+ret.toString()+"\n","UTF-8",true);
		}
	}
	
	//@Test
	public void testsingleword() throws IOException {
		
			File file = new File("C:\\Users\\junjun\\Desktop\\result.txt");
			List<Word> words = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
			Map<String,Word> wordsmap = new HashMap<String,Word>();
		
			for(Word w :words)
			{
				wordsmap.put(w.getWord(), w);
			}
			
			Word word = wordsmap.get("abuse");
			List<String> ret = Phonogram.breakPhonograms(word.getPhonogram());
			FileUtils.writeStringToFile(file,word+" | "+ret.toString()+"\n","UTF-8",true);
		
	}
	
	//@Test
	public void testBreakWord() throws IOException {
		 List<Word> words = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
		 File file = new File("C:\\Users\\junjun\\Desktop\\result.txt");
		 File errorfile =  new File("C:\\Users\\junjun\\Desktop\\error.txt");
		 for(Word word: words)
		 {
			List<String> ret = Phonogram.breakPhonograms(word.getPhonogram());
			List<String> tmp = new ArrayList<String>(Arrays.asList(new String[ret.size()]));
			Boolean finished = false;
			List<String> wordparts = Phonogram.breakWord(ret, word.getWord(),tmp,0,0,finished);
			if(wordparts == null  || wordparts.toString().contains("-") || wordparts.toString().contains("null"))
				FileUtils.writeStringToFile(errorfile,word+" | "+ret.toString()+"|"+wordparts+"\n","UTF-8",true);
			else
				FileUtils.writeStringToFile(file,word+" | "+ret.toString()+"|"+wordparts+"\n","UTF-8",true);
		 }
	}
	

	//@Test
	public void testSingleBreakWord() throws IOException {
		File file = new File("C:\\Users\\junjun\\Desktop\\result.txt");
		List<Word> words = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
		Map<String,Word> wordsmap = new HashMap<String,Word>();
	
		for(Word w :words)
		{
			wordsmap.put(w.getWord(), w);
		}
	
		Word word = wordsmap.get("visible");
	 
		List<String> ret = Phonogram.breakPhonograms(word.getPhonogram());
		List<String> tmp = new ArrayList<String>(Arrays.asList(new String[ret.size()]));
		Boolean finished = false;
		
		List<String> wordparts = Phonogram.breakWord(ret, word.getWord(),tmp,0,0,finished);
		Phonogram.saving = null;
		FileUtils.writeStringToFile(file,word+" | "+ret.toString()+"|"+wordparts+"\n","UTF-8",true);
		 
	}
	
	
	@Test
	public void testAnalyze() throws IOException
	{
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\junjun\\Desktop\\result.txt");
		 File errorfile =  new File("C:\\Users\\junjun\\Desktop\\error.txt");
		//";
		//String [] words = "business,hello,busy,about,accurate,machine,master,objection,physician".split(",");
		 List<Word> words = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
		for(Word w:words)
		{
			Word word1 = PhonogramAnalyzer.analyze(w.getWord());
			if(word1!=null)
			{
				if(word1.testlength())
					FileUtils.writeStringToFile(file, word1.getWord()+"|"+word1.getPhonogram()+"|"+word1.getPhongrams()+"|"+word1.getLetters()+"\n","UTF-8",true);
				else
					FileUtils.writeStringToFile(errorfile, word1.getWord()+"|"+word1.getPhonogram()+"|"+word1.getPhongrams()+"|"+word1.getLetters()+"\n","UTF-8",true);			
			}
			else
				;//FileUtils.writeStringToFile(errorfile,w+"  null\n","UTF-8",true);
		}
	}
	
	
	//@Test
	public void testSingleAnalyze() throws IOException
	{
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\junjun\\Desktop\\result.txt");
		 File errorfile =  new File("C:\\Users\\junjun\\Desktop\\error.txt");
		//";
		//String [] words = "business,hello,busy,about,accurate,machine,master,objection,physician".split(",");
		 List<Word> words = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
		 Map<String,Word> wordsmap = new HashMap<String,Word>();
			
		for(Word w :words)
		{
			wordsmap.put(w.getWord(), w);
		}
		Word w = wordsmap.get("bright");
			Word word1 = PhonogramAnalyzer.analyze(w.getWord());
			if(word1!=null)
			{
				if(word1.testlength())
					FileUtils.writeStringToFile(file, word1.getWord()+"|"+word1.getPhonogram()+"|"+word1.getPhongrams()+"|"+word1.getLetters()+"\n","UTF-8",true);
				else
					FileUtils.writeStringToFile(errorfile, word1.getWord()+"|"+word1.getPhonogram()+"|"+word1.getPhongrams()+"|"+word1.getLetters()+"\n","UTF-8",true);			
			}
			else
				;//FileUtils.writeStringToFile(errorfile,w+"  null\n","UTF-8",true);
		
	}
	
	
	//@Test
	public void testParseWord()
	{
		List<Word> words = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
		Map<String,Word> wordsmap = new HashMap<String,Word>();
	
		for(Word w :words)
		{
			wordsmap.put(w.getWord(), w);
		}
		System.out.println(wordsmap.get("altitude"));
	}

}
