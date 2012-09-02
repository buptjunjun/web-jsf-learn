package charpter2;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.CompressionTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *  test addDocument 锛宒eleteDocument 锛�updateDocument of index
 * @author andyWebsense
 *
 */
public class ChangeIndex {

	private IndexWriter writer;
	protected String[] ids = {"1", "2"};
	protected String[] unindexed = {"Netherlands", "Italy"};
	protected String[] unstored = {"Amsterdam has lots of bridges","Venice has lots of canals"};
	protected String[] text = {"Amsterdam", "Venice"};

	Directory dir = null;
	public ChangeIndex(String indexDir) throws IOException
	{
	    dir = FSDirectory.open(new File(indexDir));
	    //dir.setLockFactory(lockFactory)
	    //the "create" variable of indexWriter constructor must be "false"
	    //IndexWriter(Directory d, Analyzer a, boolean create, IndexWriter.MaxFieldLength mfl) 
		this.writer = new IndexWriter(dir,new StandardAnalyzer(Version.LUCENE_36),IndexWriter.MaxFieldLength.UNLIMITED);
	    this.writer.setInfoStream(System.out);
		//IndexWriter anotherIndexWriter = new IndexWriter(dir,new StandardAnalyzer(Version.LUCENE_36),IndexWriter.MaxFieldLength.UNLIMITED);
		//IndexReader reader = writer.getReader();
	    
	}
	
	/**
	 *  test AddDocuments   the "create" variable of indexWriter constructor must be "false"
	 *  IndexWriter(Directory d, Analyzer a, boolean create, IndexWriter.MaxFieldLength mfl) 
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public void addDocuments() throws CorruptIndexException, IOException
	{
		for (int i = 0; i < ids.length; i++)
		{
			Document doc = new Document();
			doc.add(new Field("id", ids[i],
				Field.Store.YES,
				Field.Index.NOT_ANALYZED));
			doc.add(new Field("country", unindexed[i],
				Field.Store.YES,
				Field.Index.NO));
			doc.add(new Field("contents", unstored[i],
				Field.Store.NO,
				Field.Index.ANALYZED));	
			doc.add(new Field("city", text[i],
				Field.Store.YES,
				Field.Index.ANALYZED));
			writer.addDocument(doc);
			
		}
		
		System.out.println("docs = " + writer.numDocs());
		
	}
	
	 /*
	  *  test deleteDocuments   the "create" variable of indexWriter constructor must be "false"
	  *  IndexWriter(Directory d, Analyzer a, boolean create, IndexWriter.MaxFieldLength mfl) 
	  *  
	  * deleteDocuments(Term) deletes all documents containing the provided term.
	  * deleteDocuments(Term[])deletes all documents containing any of the terms in the provided array.
	  * deleteDocuments(Query) deletes all documents matching the provided query.
	  * deleteDocuments(Query[])deletes all documents matching any of the queries in the provided array.
	  *	deleteAll() deletes all documents in the index. This is exactly the same as closing the writer and opening a new writer with create=true, without having to close your writer.
	  */
	
	public void deleteDocuments(Term [] terms) throws CorruptIndexException, IOException
	{
		
		// deletes all documents containing any of the terms in the provided array.
		this.writer.deleteDocuments(terms);
		System.out.println("docs = " + writer.numDocs());
		
	}
	
	/**
	 * 	test updateDocuments   the "create" variable of indexWriter constructor must be "false"
	 *  IndexWriter(Directory d, Analyzer a, boolean create, IndexWriter.MaxFieldLength mfl) 
	 *  
	 * updateDocument(Term, Document) first deletes all documents containing the provided term and then adds the new document using the writer鈥檚 default analyzer.
	 * updateDocument(Term, Document, Analyzer) does the same but uses the provided analyzer instead of the writer鈥檚 default analyzer.
	 * 
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public void updateDocuments(Term term) throws CorruptIndexException, IOException
	{
		Document doc = new Document();
		doc.add(new Field("id", "1",
				Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("country", "Netherlands",
				Field.Store.YES,
				Field.Index.NO));
		doc.add(new Field("contents",
				"Den Haag has a lot of museums",
				Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("city", "Den Haag",
				Field.Store.YES,
				Field.Index.ANALYZED));
		writer.updateDocument(new Term("id", "1"),
				doc);
		Field f = new Field("city", "Den Haag",
				Field.Store.YES,
				Field.Index.ANALYZED,TermVector.WITH_POSITIONS);
		
		System.out.println("docs = " + writer.numDocs());

	}
	
	/**
	 * search a String
	 * @param fieldName
	 * @param searchString
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void search(String fieldName,String q) throws CorruptIndexException, IOException, ParseException
	{
		IndexSearcher searcher = new IndexSearcher(dir);
		QueryParser parser = new QueryParser(Version.LUCENE_36,"contents",new StandardAnalyzer(Version.LUCENE_36));
		Query query = parser.parse(q);
		TopDocs hits = searcher.search(query, 20);

	
		System.out.println("search result:");

		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 鍙栧緱鍛戒腑鐨勬枃妗�
			Document d = searcher.doc(doc.doc);
			System.out.println(d.get("contents"));
		}

		
	}
	
	
	public void commit() throws CorruptIndexException, IOException
	{
		this.writer.commit();
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		ChangeIndex ci = new ChangeIndex("charpter2-1");
		//ci.writer.optimize(3);
		//test add index
		//ci.addDocuments();
		//ci.commit();
		
		//test delete index
		// the term to delete
		//Term [] terms = {new Term("id","1"),new Term("id","10")};
		//ci.deleteDocuments(terms);
		
		//test update index
		System.out.println("before udpate");
		ci.search("contents", "Haag");
		ci.updateDocuments(new Term("id","1"));
		ci.commit();
		System.out.println("after udpate");
		ci.search("contents", "Haag");
	}

}
