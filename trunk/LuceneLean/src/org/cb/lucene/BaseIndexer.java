package org.cb.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import org.cb.data.Blog;
import org.cb.util.converter;
import org.wltea.analyzer.lucene.*;


public class BaseIndexer
{
	// path to store the index
	private String indexPath = ".";
	private Directory dir = null; 
	private Analyzer analyzer = null;
	private IndexWriterConfig iwc = null;
	private IndexWriter writer = null;
	private String fieldName = "blogcontent";
	
	public BaseIndexer(String indexPath)
	{
		this.indexPath = indexPath;
        System.out.println("Indexing to directory '" + indexPath + "'...");    
        try
		{
			dir = FSDirectory.open(new File(indexPath));
			analyzer = new IKAnalyzer();
			iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
			iwc.setRAMBufferSizeMB(1024.0);
			iwc.setMaxBufferedDocs(1000);
			iwc.setOpenMode(OpenMode.CREATE);
			writer = new IndexWriter(dir, iwc);			
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	/**
	 * add a list of field
	 * @param fields
	 */
	public void Index (List<Document> docs)
	{
		if(docs == null)  return;
		
		try
		{
			for(Document doc:docs)
              this.writer.addDocument(doc);            
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
    }
	
	public void commit()
	{
		synchronized(this.writer)
		{
			if(this.writer!=null)
			{
				try
				{
					this.writer.commit();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static  void main(String [] args)
	{
		BaseIndexer blogindexer = new BaseIndexer("./index");
		
		List<Document> docs = getDocs();
		blogindexer.Index(docs);
		blogindexer.commit();
	}
	
	static public List<Document> getDocs()
	{

		Blog b = new Blog();
		b.setUrl("badu.com");
		b.setBlogerURL("baidu.com");
		b.setContent("hello�����");
		b.setPostDate(new Date());
		b.setComment(100);
		b.setVisit(10);
		b.setCrawledDate(new Date());
		b.setId("aabcdef");
		b.setHost("baidu.com");
		b.setPictures(4);
		String [] contents = {"��һ��������ɣ����˺ܶ�������ķ��ˣ����ܰ���Ҳף������µ�һ�����彡������෢�ƣ������������Ů�Ҹ����֡�",
							  "�̳߳ع�������ThreadPoolManager��:���ڴ�����java�����̳߳ع����̣߳�WorkThread��: �̳߳����߳�",
							   "Solr4.0��tomcat����Solrj�ļ�ʹ�� | IT����¼"							
								};
		List<Document> ldoc = new ArrayList<Document>();
		for(int i = 0; i < contents.length;i++)
		{
			System.out.println("create a doc from :" + b.toString());
			b.setId("id"+i);
			b.setContent(contents[i]);
			b.setTags(Arrays.asList("java  c++ ����"));
			Document doc = converter.Object2Doc(b);
			ldoc.add(doc);
		}
		
		return ldoc;
	}

}
