package byr.analyzer.analyzer;

import java.text.SimpleDateFormat;
import java.util.Date;

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
			+"<field name=\"id\">"+(html.getCreatedDate()==null?sdf.format(new Date()):sdf.format(html.getCreatedDate()))+"/HOUR</field>\n"
			+"</doc>\n";
		return ret;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
