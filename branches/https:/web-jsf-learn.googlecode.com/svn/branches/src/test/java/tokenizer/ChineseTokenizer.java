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
        String keyWord = "IKAnalyzer�ķִ�Ч��������ô���أ���������һ�°�";  
        //����IKAnalyzer���ķִʶ���  
        IKAnalyzer analyzer = new IKAnalyzer();  
        // ʹ�����ִܷ�  
        analyzer.setUseSmart(true);  
        // ��ӡ�ִʽ��  
        try {  
            printAnalysisResult(analyzer, str);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  

	}
	 /** 
     * ��ӡ�������ִ����ķִʽ�� 
     *  
     * @param analyzer 
     *            �ִ��� 
     * @param keyWord 
     *            �ؼ��� 
     * @throws Exception 
     */  
    private static void printAnalysisResult(Analyzer analyzer, String keyWord)  
            throws Exception {  
        System.out.println("["+keyWord+"]�ִ�Ч������");  
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
     * ��ӡ�������ִ����ķִʽ�� 
     *  
     * @param analyzer 
     *            �ִ��� 
     * @param keyWord 
     *            �ؼ��� 
     * @throws Exception 
     */  
    public static List<String> getWords( String keyWord)  
            throws Exception {  
    	List<String> ret = new ArrayList<String>();
    	IKAnalyzer analyzer = new IKAnalyzer();  
        // ʹ�����ִܷ�  
        analyzer.setUseSmart(true); 
        System.out.println("["+keyWord+"]�ִ�Ч������");  
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
		Test("IKanalyzer �ʵ� ���� ��2012ŷ�ޱ�ipad2 http://www.baidu.com/");
	}

}
