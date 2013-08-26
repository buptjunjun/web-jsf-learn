package org.junjun.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class BaseLucene 
{
	// path to store the index
	private String indexPath = ".";
	private Directory dir = null; 
	

	private Analyzer analyzer = null;
	private IndexWriterConfig iwc = null;


	private IndexWriter writer = null;
	private IndexSearcher searcher = null;
	
	  // only one index reader 
    static private IndexReaderGenerator ReaderGenerator = null;
    
	public BaseLucene(String indexPath)
	{
		this.indexPath = indexPath;
        System.out.println("Indexing to directory '" + indexPath + "'...");    
        try
		{
        	// reader
			dir = FSDirectory.open(new File(indexPath));
			analyzer = new IKAnalyzer();
			iwc = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
			iwc.setRAMBufferSizeMB(1024.0);
			iwc.setMaxBufferedDocs(1000);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(dir, iwc);		
			writer.commit();
			
			//searcher
			if(ReaderGenerator == null)
	    		ReaderGenerator = new IndexReaderGenerator(indexPath);
			
			searcher = new IndexSearcher(ReaderGenerator.getReader());
		    MultiFieldQueryParser m = null;
		    DefaultSimilarity ds = null;
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public IndexWriter getWriter() {
		return writer;
	}

	public void setWriter(IndexWriter writer) {
		this.writer = writer;
	}

	public IndexSearcher getSearcher() {
		return searcher;
	}

	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}
	public Directory getDir() {
		return dir;
	}



	public void setDir(Directory dir) {
		this.dir = dir;
	}
	
	public Analyzer getAnalyzer() {
		return analyzer;
	}



	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}
}
