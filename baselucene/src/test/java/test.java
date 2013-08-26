import org.junit.Test;
import org.junjun.lucene.BaseIndexer;
import org.junjun.lucene.BaseLucene;
import org.junjun.lucene.BaseSearcher;


public class test {
	@Test
	public void test()
	{
		BaseLucene base = new BaseLucene("/home/junjun/lucene");
		BaseSearcher br = new BaseSearcher(base);
		
		BaseIndexer bi = new BaseIndexer(base);
		
		
	}
}
