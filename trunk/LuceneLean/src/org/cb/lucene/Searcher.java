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
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public abstract class Searcher
{
	
	/**
	 * query a filed with queryStr
	 * @param queryStr
	 * @param fieldName
	 * @return
	 */
	public List<Document> SearchField(String queryStr ,String fieldName)
	{
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

	}

}
