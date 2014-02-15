package byr.analyzer.analyzer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.solr.common.SolrInputDocument;

import byr.crawler.framework.Html;

public class AnalyzerUtil {

	static private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMdd'T'HH:mm:ssZ");
	static String toxml(HtmlStructuredData html)
	{
		
		String ret = null;
		ret= "<doc>\n"
			+"<field name=\"id\">"+html.getId()+"</field>\n"
			+"<field name=\"url\">"+html.getUrl()+"</field>\n"
			+"<field name=\"title\">"+html.getTitle()+"</field>\n"
			+"<field name=\"content\">"+html.getContent()+"</field>\n"
			+"<field name=\"imgs\">"+html.getImgs()+"</field>\n"
			+"<field name=\"videos\">"+html.getVideos()+"</field>\n"
			+"<field name=\"date\">"+(html.getCreatedDate()==null?sdf.format(new Date()):sdf.format(html.getCreatedDate()))+"/HOUR</field>\n"
			+"</doc>\n";
		return ret;
	}
	
	static SolrInputDocument toDoc(HtmlStructuredData html)
	{
		SolrInputDocument  doc = new SolrInputDocument();
	
		doc.setField("id",html.getId());
		doc.setField("url",html.getUrl());
		doc.setField("title",html.getTitle());
		doc.setField("content",html.getContent());
		doc.setField("imgs",html.getImgs());
		doc.setField("videos",html.getVideos());
		doc.setField("date",(html.getCreatedDate()==null?sdf.format(new Date()):sdf.format(html.getCreatedDate()))+"/HOUR");
		
		return doc;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
