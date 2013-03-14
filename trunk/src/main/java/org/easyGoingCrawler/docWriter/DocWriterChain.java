package org.easyGoingCrawler.docWriter;

import java.util.List;

import org.easyGoingCrawler.framwork.CrawlURI;
import org.easyGoingCrawler.framwork.DocWriter;

public class DocWriterChain extends DocWriter
{

	List<DocWriter> writerList = null;
	
	public DocWriterChain(List<DocWriter> writerList)
	{
		this.writerList = writerList;
	}
	
	@Override
	public void write(CrawlURI curl)
	{
		// TODO Auto-generated method stub
		if(this.writerList != null)
		{
			for(DocWriter writer : this.writerList)
				writer.write(curl);
		}
	}
	
 	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}