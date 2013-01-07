package org.cb;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class BlogSearcher
{
	// path to store the index
	private String indexPath = ".";
	private Directory dir = null; 
	private IndexReader reader = null;
    private IndexSearcher searcher = null;
    private Analyzer analyzer = null;
    private  QueryParser qp = null;
    private String fieldname =  "blogcontent";
    public BlogSearcher(String index)
	{
		try
		{
			dir = FSDirectory.open(new File(indexPath));
			reader = DirectoryReader.open(dir);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   		
		analyzer = new IKAnalyzer();
	    searcher = new IndexSearcher(reader);
	    qp = new QueryParser(Version.LUCENE_40,fieldname,this.analyzer);
	}
	
	public String SearchBlog(String queryStr)
	{
		try
		{
			Query q = this.qp.parse(queryStr);
			TopDocs results = null;

			results = searcher.search(q, 10);
		
			ScoreDoc[] hits = results.scoreDocs;
			for(ScoreDoc doc: hits)
			{
				Document d = this.searcher.doc(doc.doc);
				System.out.println(d.get(fieldname));
			}
			
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
		BlogSearcher bsearcher = new BlogSearcher(".");
		bsearcher.SearchBlog("Å·ÖÞ");

	}

}
