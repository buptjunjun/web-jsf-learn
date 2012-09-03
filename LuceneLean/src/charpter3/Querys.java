package charpter3;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class Querys 
{
	private IndexWriter writer;
	protected String[] ids = {"1", "2","3"};
	protected String[] unindexed = {"Netherlands", "Italy","China"};
	protected String[] unstored = {"Amsterdam has a lot of bridges","Venice has lots of canals","Amsterdam' bridges are a lot"};
	protected String[] text = {"Amsterdam", "Venice","Aeijing"};

	Directory dir = null;
	public Querys(String indexDir) throws IOException
	{
	    dir = FSDirectory.open(new File(indexDir));
		this.writer = new IndexWriter(dir,new StandardAnalyzer(Version.LUCENE_36),true,IndexWriter.MaxFieldLength.UNLIMITED);
	    this.writer.setInfoStream(System.out);  
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
			
			NumericField nfield = new NumericField("intID", 10);
			nfield.setIntValue(i);
			doc.add(nfield);		
			
			doc.add(new Field("id", ids[i],
				Field.Store.YES,
				Field.Index.NOT_ANALYZED));
			doc.add(new Field("country", unindexed[i],
				Field.Store.YES,
				Field.Index.NO));
			doc.add(new Field("contents", unstored[i],
				Field.Store.YES,
				Field.Index.ANALYZED));	
			doc.add(new Field("city", text[i],
				Field.Store.YES,
				Field.Index.ANALYZED));
			writer.addDocument(doc);
			
		}
		
		System.out.println("docs = " + writer.numDocs());
		
	}
	
	public void index() throws CorruptIndexException, IOException
	{
		this.addDocuments();
		this.commit();
	}

	/**
	 * search a String
	 * @param fieldName
	 * @param searchString
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void termQuery(String fieldName,String q) throws CorruptIndexException, IOException, ParseException
	{
		IndexSearcher searcher = new IndexSearcher(dir);
		Term t = new Term(fieldName,q.toLowerCase());
		Query query = new TermQuery(t);
		TopDocs hits = searcher.search(query, 20);

	
		System.out.println("search result:");

		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 閸欐牕绶遍崨鎴掕厬閻ㄥ嫭鏋冨锟�
			Document d = searcher.doc(doc.doc);
			System.out.println(d.get("contents"));
		}

		
	}
	
	/**
	 * search a String within a range
	 * @param fieldName
	 * @param searchString
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void termRangeQuery(String fieldName,String q) throws CorruptIndexException, IOException, ParseException
	{
		IndexSearcher searcher = new IndexSearcher(dir);
		
		Query query = new TermRangeQuery("city","aa","am",true,true);
		TopDocs hits = searcher.search(query, 20);

	
		System.out.println("search result:");

		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 閸欐牕绶遍崨鎴掕厬閻ㄥ嫭鏋冨锟�
			Document d = searcher.doc(doc.doc);
			System.out.println(d.get("contents"));
		}

		
	}
	
	/**
	 * search a doc within a numeric range
	 * @param fieldName
	 * @param searchString
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void numericRangeQuery(int from,int to) throws CorruptIndexException, IOException, ParseException
	{
		IndexSearcher searcher = new IndexSearcher(dir);
		
		Query query = NumericRangeQuery.newIntRange("intID",from,to,true,true);
		TopDocs hits = searcher.search(query, 20);

	
		System.out.println("search result:");

		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 閸欐牕绶遍崨鎴掕厬閻ㄥ嫭鏋冨锟�
			Document d = searcher.doc(doc.doc);
			System.out.println(d.get("contents"));
		}

		
	}
	
	
	/**
	 * search a doc containing terms beginning with a specific string
	 * @param fieldName
	 * @param searchString
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void prefixQuery(String field, String prefix) throws CorruptIndexException, IOException, ParseException
	{
		IndexSearcher searcher = new IndexSearcher(dir);
		
		Term t = new Term(field,prefix);
		Query query = new  PrefixQuery(t);
		TopDocs hits = searcher.search(query, 20);

	
		System.out.println("search result:");

		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 閸欐牕绶遍崨鎴掕厬閻ㄥ嫭鏋冨锟�
			Document d = searcher.doc(doc.doc);
			System.out.println(d.get("contents"));
		}

		
	}
	
	/**
	 * search a doc containing terms beginning with a specific string
	 * @param fieldName
	 * @param searchString
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void booleanQuery() throws CorruptIndexException, IOException, ParseException
	{
		IndexSearcher searcher = new IndexSearcher(dir);
		
		Term t = new Term("contents","bri" );
		Query query1 = new  PrefixQuery(t);
		
		Query query2 = NumericRangeQuery.newIntRange("intID",1,3,true,true);
		
		// create a boolean query 
		BooleanQuery query = new BooleanQuery();
		query.add(query1, BooleanClause.Occur.SHOULD);
		query.add(query2, BooleanClause.Occur.MUST);
		
		TopDocs hits = searcher.search(query, 20);

	
		System.out.println("search result:");

		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 閸欐牕绶遍崨鎴掕厬閻ㄥ嫭鏋冨锟�
			Document d = searcher.doc(doc.doc);
			System.out.println(d.get("contents"));
		}

		
	}
	
	
	/**
	 * phrase query
	 * @param fieldName
	 * @param searchString
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void phraseQuery() throws CorruptIndexException, IOException, ParseException
	{
		IndexSearcher searcher = new IndexSearcher(dir);
		PhraseQuery query = new PhraseQuery();
		
		// set max slop to 10
		query.setSlop(10);
		query.add(new Term("contents","lot"));
		query.add(new Term("contents","bridges"));
		TopDocs hits = searcher.search(query, 20);

	
		System.out.println("search result:");

		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 閸欐牕绶遍崨鎴掕厬閻ㄥ嫭鏋冨锟�
			Document d = searcher.doc(doc.doc);
			System.out.println(d.get("contents"));
		}		
	}
	
	
	/**
	 * wildCard query
	 * @param fieldName
	 * @param searchString
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void wildCardQuery() throws CorruptIndexException, IOException, ParseException
	{
		IndexSearcher searcher = new IndexSearcher(dir);
		
		// use wildchard "?ridg*"
		WildcardQuery query = new WildcardQuery(new Term("contents","?ridg*"));
		TopDocs hits = searcher.search(query, 20);

	
		System.out.println("search result:");

		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 閸欐牕绶遍崨鎴掕厬閻ㄥ嫭鏋冨锟�
			Document d = searcher.doc(doc.doc);
			System.out.println(d.get("contents"));
		}		
	}
	
	/**
	 * wildCard query
	 * @param fieldName
	 * @param searchString
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException 
	 */
	public void fuzzyQuery() throws CorruptIndexException, IOException, ParseException
	{
		IndexSearcher searcher = new IndexSearcher(dir);
		
		//"Amsterdam" is similar to "Amsteedam"
		FuzzyQuery query = new FuzzyQuery(new Term("contents","Amsteedam"));
		TopDocs hits = searcher.search(query, 20);

	
		System.out.println("search result:");

		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 閸欐牕绶遍崨鎴掕厬閻ㄥ嫭鏋冨锟�
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
		Querys ci = new Querys("charpter2-1");
		ci.index();
		System.out.println("----------termQuery--------------");
		ci.termQuery("city", "Venice");
		
		System.out.println("----------termRangeQuery--------------");
		ci.termRangeQuery(null,null);
		
		System.out.println("----------numericRangeQuery--------------");
		ci.numericRangeQuery(1, 5);
		
		System.out.println("----------prefixQuery--------------");
		ci.prefixQuery("contents","bri" );
		
		System.out.println("----------booleanQuery--------------");
		ci.booleanQuery();
		
		System.out.println("----------phraseQuery--------------");
		ci.phraseQuery();
		
		System.out.println("----------wildCardQuery--------------");
		ci.wildCardQuery();
		
		System.out.println("----------fuzzyQuery--------------");
		ci.fuzzyQuery();
	}

}
