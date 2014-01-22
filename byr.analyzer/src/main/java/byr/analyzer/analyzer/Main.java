package byr.analyzer.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

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
	
	public static void main(String[] args) 
	{
		fieldNames.add("magicNum");
		DAOMongo<Html> daoHtml = new DAOMongo<Html>(ip,port,dbname);
		DAOMongo<HtmlStructuredData> daoStructuredData = new DAOMongo<HtmlStructuredData>(ip1,port1,dbname1);
		
		Map htmlConstrain = new HashMap();
		htmlConstrain.put("magicNum", 0);
		
		Map<String,String> htmlConstrain1 = new HashMap<String,String>();
		htmlConstrain1.put("id", "");
		
		ByrHtmlAnalyzer analyzer = new ByrHtmlAnalyzer();
		
		List<Html> lh = daoHtml.search(htmlConstrain, 100, Html.class);		
		while(lh!=null || lh.size() > 0)
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
				}
				
				logger.info("analyze:"+html.getId()+" "+html.getUrl());
	
				html.setMagicNum(1);
				htmlConstrain1.put("id", html.getId());
				daoHtml.update(html, htmlConstrain1,fieldNames);
				
				try {
					TimeUnit.SECONDS.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
