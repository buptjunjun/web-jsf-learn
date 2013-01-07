package tokenizer;
import java.io.IOException;
import java.io.StringReader;

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
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Test("IKanalyzer �ʵ� ���� ��2012ŷ�ޱ�ipad2 http://www.baidu.com/");
	}

}
