package org.junjun.lucene;

import java.util.List;

import org.apache.lucene.document.Document;

public interface Indexer
{
	public void index (List<Document> docs);
	public void index (Document doc);

	public void refresh();
}
