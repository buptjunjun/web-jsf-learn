package charpter1;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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

public class TxtSearcher 
{
	
	public static void main(String [] args) throws IOException, ParseException
	{
		String indexDir = "."; // path of index file
		String queryString = "cpu";
		
		search(indexDir,queryString);
		
	}
	
	/**
	 * 根据 搜索字符串, 进行搜索
	 * 
	 * @param indexDir
	 * @param q
	 * @throws IOException
	 * @throws ParseException
	 */
	
	
	
	static public void search(String indexDir,String q) throws IOException, ParseException
	{
		Directory dir = FSDirectory.open(new File(indexDir));
		
		IndexSearcher is = new IndexSearcher(dir);
		
		//contents是要进行search的field名字,在index时候建立的 使用QueryParser可以对query的文本进行分词
		QueryParser parser = new QueryParser(Version.LUCENE_36,"contents",new StandardAnalyzer(Version.LUCENE_36));
		//Query query = parser.parse(q);
		//不对Term的value进行分词。
		Query query = new TermQuery(new Term("fullpath","D:\\work\\myself\\java books\\lucene\\charpter1\\testTxtFile\\name2 - 副本 (10).txt"));
		
		
		long start = System.currentTimeMillis();

		//search files
		TopDocs hits = is.search(query, 20);
		
		long end = System.currentTimeMillis();
		
		System.err.println("Found " + hits.totalHits +
				" document(s) (in " + (end - start) +
				" milliseconds) that matched query '" +
				q + "':");
		
		for(ScoreDoc doc : hits.scoreDocs)
		{
			// 取得命中的文档
			Document d = is.doc(doc.doc);
			System.out.println(d.get("fullpath"));
		}
	}
}
