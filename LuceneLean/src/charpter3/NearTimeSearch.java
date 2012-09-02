package charpter3;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class NearTimeSearch 
{
	public static void main(String [] args) throws IOException, ParseException
	{
		Directory dir = new RAMDirectory();
		IndexWriter writer = new IndexWriter(dir,new StandardAnalyzer(Version.LUCENE_30),IndexWriter.MaxFieldLength.UNLIMITED);
		
		
		// add ten docs
		for(int i = 0; i < 10; i++)
		{
			Document doc = new Document();
			doc.add(new Field("id",""+i,Field.Store.NO,Field.Index.NOT_ANALYZED_NO_NORMS));
			doc.add(new Field("text","aaa",Field.Store.NO,Field.Index.ANALYZED));
			writer.addDocument(doc);
		}
		
		
		IndexReader reader =  IndexReader.open(dir);
		reader = reader.open(writer, true);
		
		
	}

}
