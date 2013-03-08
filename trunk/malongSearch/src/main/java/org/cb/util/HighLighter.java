package org.cb.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import java.util.Date;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.apache.lucene.analysis.Analyzer;

public class HighLighter
{

	// 声明一个Query对象
	private  Analyzer analyzer = new IKAnalyzer();
	private Highlighter highlighter = null;
	private SimpleHTMLFormatter simpleHTMLFormatter = null;
	QueryParser qp = null;
	
	public static void main(String[] args) throws Exception
	{
		HighLighter test = new HighLighter();
		String queryStr = "hello";
		String content = "here we go,hello my doand here we go,hello my doandhere we go,hello my doand";
		String res = test.getHightLight(queryStr, content,100);
		System.out.println(res);

	}

	public HighLighter()
	{
		simpleHTMLFormatter = new SimpleHTMLFormatter("<em>", "</em>");
		qp = new QueryParser(Version.LUCENE_40,"content",this.analyzer);
	}


	public String getHightLight(String queryStr,String content,int size) throws IOException, InvalidTokenOffsetsException, ParseException
	{	
		Query query  = qp.parse(queryStr);
		highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(size));// 这个100是指定关键字字符串的context的长度，你可以自己设定，因为不可能返回整篇正文内容
		
		// 高亮出显示
		TokenStream tokenStream = analyzer.tokenStream("content",new StringReader(content));
		return highlighter.getBestFragment(tokenStream,content);
			
	}
}
