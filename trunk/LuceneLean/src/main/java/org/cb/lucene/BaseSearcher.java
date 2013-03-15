package org.cb.lucene;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class BaseSearcher extends Searcher
{

    private IndexSearcher searcher = null;
    private Analyzer analyzer = null;
    private String indexPath;
    
    // only one index reader 
    static private IndexReaderGenerator ReaderGenerator = null;
    
    public BaseSearcher(String indexPath)
	{
    	this.indexPath= indexPath;
    	
    	if(ReaderGenerator == null)
    		ReaderGenerator = new IndexReaderGenerator(indexPath);
		
    	analyzer = new IKAnalyzer();
	    searcher = new IndexSearcher(ReaderGenerator.getReader());
	    MultiFieldQueryParser m = null;
	    DefaultSimilarity ds = null;

	}
	
    
	/**
	 * query a filed with queryStr
	 * @param queryStr
	 * @param fieldName
	 * @return
	 */
	public List<Document> SearchField(String queryStr ,String fieldName)
	{
		if(queryStr == null || fieldName == null)
			return null;
		
		try
		{
			QueryParser qp = new QueryParser(Version.LUCENE_40,fieldName,this.analyzer);
			System.out.println(qp.toString());
			qp.setDefaultOperator(Operator.AND);
			
			Query q = qp.parse(queryStr);
			TopDocs results = null;

			results = searcher.search(q, 2);
		
			ScoreDoc[] hits = results.scoreDocs;
			List<Document> ret = new ArrayList<Document>();
			for(ScoreDoc sd: hits)
			{
				Document doc = this.searcher.doc(sd.doc);
				ret.add(doc);
				//System.out.println(doc.get(fieldName));
			}
			return ret;
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		return null;
	}
	
	/**
	 * query a filed with queryStr
	 * @param queryStr
	 * @param fieldName
	 * @return
	 */
	public List<Document> SearchField(String queryStr ,List<String> fieldName)
	{
		if(queryStr == null || fieldName == null || fieldName.size() <= 0)
			return null;
		
		try
		{
			String [] fields = new String[fieldName.size()];
			for(int i = 0; i<fieldName.size(); i++)
				fields[i] = fieldName.get(i);
			 
			Map<String,Float> termBoost = new HashMap<String,Float>();
			
			TokenStream tokenStream = this.analyzer.tokenStream("", new StringReader(queryStr));
			 BooleanQuery q = new BooleanQuery (); 
			 
			 tokenStream.addAttribute(CharTermAttribute.class);  
		        while (tokenStream.incrementToken()) {  
		            CharTermAttribute charTermAttribute = tokenStream  
		                    .getAttribute(CharTermAttribute.class);  
		            String term = charTermAttribute.toString();
		            TermQuery tq1 = new TermQuery(new Term("content",term));;
		            TermQuery tq2 = new TermQuery(new Term("title",term));;
		            if(Pattern.matches("[\u4E00-\u9FA5]+",term))
		            {
		            	System.out.println(term+" ÖÐÎÄ");  
		            	 termBoost.put(term, 1f);
		            	 tq1.setBoost(1.0f);
		            	 tq2.setBoost(1.0f);
		            }
		            else
		            {
		            	System.out.println(term+" Ó¢ÎÄ");
		            	termBoost.put(term, 1f);
		            	 tq1.setBoost(1.1f);
		            	 tq2.setBoost(1.1f);
		            }		  
		            q.add(tq1, Occur.SHOULD);
		            q.add(tq2, Occur.SHOULD);
		        }	       
		        
		       
//			BooleanClause.Occur[] flags = new BooleanClause.Occur[]{BooleanClause.Occur.SHOULD,BooleanClause.Occur.MUST};
//			MultiFieldQueryParser qp = new MultiFieldQueryParser(Version.LUCENE_40,fields,this.analyzer,termBoost);
//			Query q = qp.parse(Version.LUCENE_40,queryStr,fields,flags,this.analyzer);
//			
//			System.out.println(q.toString());
			TopDocs results = null;
			results = searcher.search(q, 10);
		
			ScoreDoc[] hits = results.scoreDocs;
			List<Document> ret = new ArrayList<Document>();
			for(ScoreDoc sd: hits)
			{
				Document doc = this.searcher.doc(sd.doc);
				ret.add(doc);
				//System.out.println(doc.get(fieldName));
			}
			return ret;
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		BaseSearcher bsearcher = new BaseSearcher("./index");
		bsearcher.SearchField("Å·ÖÞ","blogcontent");

	}

}
