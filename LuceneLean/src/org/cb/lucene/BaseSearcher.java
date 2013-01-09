package org.cb.lucene;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
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
			Query q = qp.parse(queryStr);
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
