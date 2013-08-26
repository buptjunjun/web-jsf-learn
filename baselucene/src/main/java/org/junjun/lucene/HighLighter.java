package org.junjun.lucene;

import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;


import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;

import org.apache.lucene.search.Query;


import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class HighLighter
{

	
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

	private HighLighter()
	{
		simpleHTMLFormatter = new SimpleHTMLFormatter("<read>", "</read>");
		qp = new QueryParser(Version.LUCENE_40,"content",this.analyzer);
	}


	public String getHightLight(String queryStr,String content,int size) throws IOException, InvalidTokenOffsetsException, ParseException
	{	
		Query query  = qp.parse(queryStr);
		highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(size));
	
		TokenStream tokenStream = analyzer.tokenStream("content",new StringReader(content));
		return highlighter.getBestFragment(tokenStream,content);
			
	}
}
