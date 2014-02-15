package byr.analyzer.analyzer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import byr.crawler.framework.Html;
import byr.crawler.persist.DAOMongo;

public class Main {

	static String ip = "127.0.0.1";
	static int port = 27017;
	static String dbname = "byr";
	
	static String ip1 = "127.0.0.1";
	static int port1 = 27017;
	static String dbname1 = "byr";
	static HashSet<String> fieldNames = new HashSet<String>();
	static private Logger logger = Logger.getLogger(Main.class);
	static private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	public static void main(String[] args) throws IOException 
	{
		fieldNames.add("magicNum");
		DAOMongo<Html> daoHtml = new DAOMongo<Html>(ip,port,dbname);
		DAOMongo<HtmlStructuredData> daoStructuredData = new DAOMongo<HtmlStructuredData>(ip1,port1,dbname1);
		
		Map htmlConstrain = new HashMap();
		htmlConstrain.put("magicNum", 1);
		
		Map<String,String> htmlConstrain1 = new HashMap<String,String>();
		htmlConstrain1.put("id", "");
		
		ByrHtmlAnalyzer analyzer = new ByrHtmlAnalyzer();
		List<Html> lh = daoHtml.search(htmlConstrain, 100, Html.class);
		
		String fileName = sdf.format(new Date())+".xml";
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		
		FileUtils.writeStringToFile(new File(fileName), "<add>", "UTF-8", true);
		while(lh!=null && lh.size() > 0)
		{
			for(Html html:lh)
			{
				/*try {
					FileUtils.writeStringToFile(new File("byr.html"), html.getHtml(),"GBK");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				HtmlStructuredData hsd= analyzer.analyse(html);
			
				if(hsd != null )
				{
					daoStructuredData.insert(hsd);
					FileUtils.writeStringToFile(new File(fileName), AnalyzerUtil.toxml(hsd), "UTF-8", true);
					docs.add(AnalyzerUtil.toDoc(hsd));
				}
				
				logger.info("analyze:"+html.getId()+" "+html.getUrl());
	
				html.setMagicNum(2);
				htmlConstrain1.put("id", html.getId());
				daoHtml.update(html, htmlConstrain1,fieldNames);
				
				try {
					TimeUnit.SECONDS.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			lh = daoHtml.search(htmlConstrain, 100, Html.class);
		}
		FileUtils.writeStringToFile(new File(fileName), "</add>", "UTF-8", true);
		
		indexSolr(docs);
	}
	
	public static void indexSolr(List<SolrInputDocument> docs)
	{
		String serverUrl = "http://localhost:8983/solr/collection1";
		SolrServer solr = new HttpSolrServer(serverUrl);
		try {
			solr.add(docs);
			//hard commit
			solr.commit(true, false, false);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			solr.shutdown();
		}
		
	}

}
