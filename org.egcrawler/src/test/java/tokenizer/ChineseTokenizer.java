package tokenizer;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.*;
public class ChineseTokenizer
{

	static public void Test(String str)
	{
        String keyWord = "IKAnalyzer的分词效果到底怎么样呢，我们来看一下吧";  
        //创建IKAnalyzer中文分词对象  
        IKAnalyzer analyzer = new IKAnalyzer();  
        // 使用智能分词  
        analyzer.setUseSmart(true);  
        // 打印分词结果  
        try {  
            printAnalysisResult(analyzer, str);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  

	}
	 /** 
     * 打印出给定分词器的分词结果 
     *  
     * @param analyzer 
     *            分词器 
     * @param keyWord 
     *            关键词 
     * @throws Exception 
     */  
    private static void printAnalysisResult(Analyzer analyzer, String keyWord)  
            throws Exception {  
        System.out.println("["+keyWord+"]分词效果如下");  
        TokenStream tokenStream = analyzer.tokenStream("content",  
                new StringReader(keyWord));  
        tokenStream.addAttribute(CharTermAttribute.class);  
        while (tokenStream.incrementToken()) {  
            CharTermAttribute charTermAttribute = tokenStream  
                    .getAttribute(CharTermAttribute.class);  
            System.out.println(charTermAttribute.toString());  
  
        }  
    }  
    /** 
     * 打印出给定分词器的分词结果 
     *  
     * @param analyzer 
     *            分词器 
     * @param keyWord 
     *            关键词 
     * @throws Exception 
     */  
    public static List<String> getWords( String keyWord)  
            throws Exception {  
    	List<String> ret = new ArrayList<String>();
    	IKAnalyzer analyzer = new IKAnalyzer();  
        // 使用智能分词  
        analyzer.setUseSmart(true); 
        System.out.println("["+keyWord+"]分词效果如下");  
        TokenStream tokenStream = analyzer.tokenStream("content",  
                new StringReader(keyWord));  
        tokenStream.addAttribute(CharTermAttribute.class);  
        while (tokenStream.incrementToken()) {  
            CharTermAttribute charTermAttribute = tokenStream  
                    .getAttribute(CharTermAttribute.class);  
            ret.add(charTermAttribute.toString());  
  
        }  
        return ret;
    }  

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Test("IKanalyzer 词典 下载 ，2012欧洲杯ipad2 http://www.baidu.com/");
	}

}
