package com.baicizhan.wordsplit;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\junjun\\Desktop\\result.txt");
		//";
		String [] words = "business,hello,busy,about,accurate,machine,master,objection,physician".split(",");
		
		for(String w:words)
		{
			Word word1 = PhonogramAnalyzer.analyze(w);
			if(word1!=null)
				FileUtils.writeStringToFile(file, word1.getWord()+"|"+word1.getPhonogram()+"|"+word1.getPhongrams()+"|"+word1.getLetters()+"\n","UTF-8",true);
			else
				FileUtils.writeStringToFile(file,w+"  null\n","UTF-8",true);
		}
	}

}
