package com.baicizhan.wordsplittest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.baicizhan.wordsplit.Phonogram;
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
	
	@Test
	public void testBreakWord() throws IOException {
		 List<Word> words = Word.parseWord("C:\\Users\\junjun\\Desktop\\cet4word.txt");
		 File file = new File("C:\\Users\\junjun\\Desktop\\result.txt");
		 File errorfile =  new File("C:\\Users\\junjun\\Desktop\\error.txt");
		 for(Word word: words)
		 {
			List<String> ret = Phonogram.breakPhonograms(word.getPhonogram());
			List<String> wordparts = Phonogram.breakWord(ret, word.getWord());
			if(wordparts == null  || wordparts.toString().contains("-"))
				FileUtils.writeStringToFile(errorfile,word+" | "+ret.toString()+"|"+wordparts+"\n","UTF-8",true);
			else
				FileUtils.writeStringToFile(file,word+" | "+ret.toString()+"|"+wordparts+"\n","UTF-8",true);
		 }
	}

}
