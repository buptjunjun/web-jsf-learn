package org.junjun.lucene;

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

import org.wltea.analyzer.lucene.*;


public class BaseIndexer implements Indexer
{
	
	private IndexWriter writer = null;	
	private BaseLucene baselucene = null;
  
	public BaseIndexer(BaseLucene baselucene)
	{
		this.baselucene = baselucene;
		this.writer = this.baselucene.getWriter();
	}
	
	

	/**
	 * add a list of field
	 * @param fields
	 */
	public void index (List<Document> docs)
	{
		if(docs == null)  return;		
		synchronized(writer)
		{
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
		
    }
	
	
	/**
	 * add a list of field
	 * @param fields
	 */
	public void index (Document doc)
	{
		if(doc == null)  return;
		synchronized(writer)
		{
			try
			{			
	           this.writer.addDocument(doc);            
	        }
			catch(Exception e)
			{
				e.printStackTrace();
			}
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

	public void clearIndex()
	{
		 synchronized(this.writer)
			{
				if(this.writer!=null)
				{
					try
					{
						this.writer.deleteAll();
						this.writer.commit();
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}



	public void refresh() {
		// TODO Auto-generated method stub
		commit();
		
	}
	

}
