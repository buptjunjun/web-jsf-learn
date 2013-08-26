package org.junjun.lucene;

import java.util.List;
import org.apache.lucene.document.Document;


public interface Searcher
{
	
	/**
	 * query a filed with queryStr
	 * @param queryStr
	 * @param fieldName
	 * @return
	 */
	public List<Document> SearchField(String queryStr ,String fieldName);
	
	/**
	 * query a filed with queryStr
	 * @param queryStr
	 * @param fieldName
	 * @return
	 */
	public List<Document> SearchField(String queryStr ,List<String> fieldName);
	

	public void refresh();

}
