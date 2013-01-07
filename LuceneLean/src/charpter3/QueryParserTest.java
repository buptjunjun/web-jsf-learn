package charpter3;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;

public class QueryParserTest {

	public static void testToString() throws Exception {
		BooleanQuery query = new BooleanQuery();
		query.add(new FuzzyQuery(new Term("field", "kountry")),
		BooleanClause.Occur.MUST);
		query.add(new TermQuery(new Term("title", "western")),
		BooleanClause.Occur.SHOULD);
		System.out.println(query.toString());
		}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("---------testToString--------------");
		testToString();
	}

}
