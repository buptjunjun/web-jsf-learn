package charpter1;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class TxtIndexer {

	private IndexWriter writer;

	public TxtIndexer(String indexDir) throws IOException
	{
		Directory dir = FSDirectory.open(new File(indexDir));
		this.writer = new IndexWriter(dir,new StandardAnalyzer(Version.LUCENE_36),false,IndexWriter.MaxFieldLength.UNLIMITED);
		
		
	}

	public void close() throws CorruptIndexException, IOException
	{
		this.writer.close();
	}
	
	/**
	 *  ��������
	 * @param dir  ������Ŀ¼��·��
	 * @param filter �ļ�������
	 * @return
	 * @throws Exception
	 */
	public int index(String dir, FileFilter filter) throws Exception
	{
		File [] files = new File(dir).listFiles();
		for( File f: files)
		{
			if(!f.isDirectory() && !f.isHidden() &&  f.canRead() && f.exists() && (filter == null || filter.accept(f)))
			{
				indexFile(f);
			}
		}
		this.writer.commit();
		
		return this.writer.numDocs();
	}
	
	
	/**
	 * ����һ�������������ļ���Document
	 * @param f
	 * @return
	 * @throws Exception
	 */
	protected Document getDocument(File f) throws Exception
	{
		Document doc = new Document();
		// Ϊÿһ��document����һ��Fieled,�Ա���searchʱ��ѡ��Ҫsearch��һ��field
		doc.add(new Field("contents",new FileReader(f)));
		// Field.Index.ANALYZED �� NOT_ANALYZED �����Ƿ���Ҫ�������ִ�)
		doc.add(new Field("filename",f.getName(),Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("fullpath",f.getPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		return doc;
	}
	
	
	/**
	 * file filter for txt 
	 * @author andyWebsense
	 *
	 */
	private static class TxtFileFilter implements FileFilter
	{

		@Override
		public boolean accept(File arg0) {
			// TODO Auto-generated method stub
			return arg0.getName().toLowerCase().endsWith(".txt");
		}
		
	}
	
	/**
	 * �����ļ�f
	 * @param f
	 * @throws Exception
	 */
	private void indexFile(File f) throws Exception
	{ 
		System.out.println("indexing " + f.getCanonicalPath());
		Document doc = this.getDocument(f);
		
		// ����
		this.writer.addDocument(doc);
	}
	
	/**
	 *  ��index�м���һ��doc
	 * @param fileName
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	public void addAdocument(String fileName) throws CorruptIndexException, IOException
	{
		File f = new File(fileName);
		Document doc = new Document();
		doc.add(new Field("contents",new FileReader(f)));
		// Field.Index.ANALYZED �� NOT_ANALYZED �����Ƿ���Ҫ�������ִ�)
		doc.add(new Field("filename",f.getName(),Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("fullpath",f.getPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		this.writer.addDocument(doc);
		this.writer.commit();
	}
	
	/**
	 *  ��index�м���һ��doc
	 * @param fileName
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	public void updateAdocument(String fileName) throws CorruptIndexException, IOException
	{
		File f = new File(fileName);
		Document doc = new Document();
		doc.add(new Field("contents",new FileReader(f)));
		// Field.Index.ANALYZED �� NOT_ANALYZED �����Ƿ���Ҫ�������ִ�)
		doc.add(new Field("filename",f.getName(),Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("fullpath",f.getPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		this.writer.updateDocument(new Term("filename","-"),doc);
		this.writer.commit();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dataDir = "D:\\work\\myself\\java books\\lucene\\charpter1\\testTxtFile"; // path of a directory
		String indexDir = "."; // path of index file

		long start = System.currentTimeMillis();
		int numIndexed = 0;
		
		TxtIndexer indexer = null;
		
		try
		{
			indexer =  new TxtIndexer(indexDir);
			numIndexed = indexer.index(dataDir, new TxtFileFilter());
			// optimize all segment to one
			//indexer.writer.optimize();
			//indexer.addAdocument("D:\\work\\myself\\java books\\lucene\\charpter1\\testTxtFile\\wan xiao lan.txt");
			//indexer.updateAdocument("D:\\work\\myself\\java books\\lucene\\charpter1\\testTxtFile\\wan xiao lan.txt");
			//System.out.println("docs = " + indexer.writer.numDocs());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				indexer.close();
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Indexing " + numIndexed + " files took " + (end - start) + " milliseconds");
		
		
	}

}
