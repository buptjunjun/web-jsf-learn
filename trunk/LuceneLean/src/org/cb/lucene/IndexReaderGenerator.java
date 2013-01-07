package org.cb.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexReaderContext;
import org.apache.lucene.index.StoredFieldVisitor;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexReaderGenerator 
{
	// path to store the index
	private String indexPath = ".";
	private Directory dir = null; 
	private int readerNumer = 10;
	private IndexReader reader = null;
	
	public IndexReaderGenerator(String indexPath)
	{
		this.indexPath = indexPath;
	}
	
	public IndexReader getReader()
	{
		if(this.reader == null)
		{
			try
			{
				Directory dir = FSDirectory.open(new File(indexPath));			
				reader = DirectoryReader.open(dir);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.reader;
	}
	
	public String getIndexPath()
	{
		return indexPath;
	}

	public void setIndexPath(String indexPath)
	{
		this.indexPath = indexPath;
	}
	
}
