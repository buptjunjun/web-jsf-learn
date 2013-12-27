package org.weibo.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.weibo.common.AnalyzeBean;
import org.weibo.common.SearchResultID;

public class HtmlAnalyzer implements Analyzer{

	/**
	 * analyze the content of html and get the weibo ids concerning the keyword we'v specified.
	 * @return list of weibo id
	 */
	public List<SearchResultID> analyze(AnalyzeBean ab) 
	{
		List<SearchResultID> ret = new ArrayList<SearchResultID>();
		
		return null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
