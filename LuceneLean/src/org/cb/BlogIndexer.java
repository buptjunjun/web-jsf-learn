package org.cb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.*;
public class BlogIndexer
{
	// path to store the index
	private String indexPath = ".";
	private Directory dir = null; 
	private Analyzer analyzer = null;
	private IndexWriterConfig iwc = null;
	private IndexWriter writer = null;
	private String fieldName = "blogcontent";
	public BlogIndexer(String indexPath)
	{
		this.indexPath = indexPath;
        System.out.println("Indexing to directory '" + indexPath + "'...");    
         try
		{
			dir = FSDirectory.open(new File(indexPath));
			analyzer = new IKAnalyzer();
			iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
			iwc.setRAMBufferSizeMB(1024.0);
			writer = new IndexWriter(dir, iwc);
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void IndexBlog(String text)
	{
		
		
		try{
              // make a new, empty document
              Document doc = new Document();
              Field content = new Field(fieldName,text,Field.Store.YES,Field.Index.ANALYZED);
              doc.add(content);
              this.writer.addDocument(doc);
              this.writer.commit();
              
            }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
    }
	
	public static  void main(String [] args)
	{
		BlogIndexer blogindexer = new BlogIndexer(".");
		blogindexer.IndexBlog("IKanalyzer ¥ µ‰ œ¬‘ÿ £¨2012≈∑÷ﬁ±≠ipad2 http://www.baidu.com/");
		
	}

}
