package org.weibo.analyzer;

import java.util.List;

import org.weibo.common.AnalyzeBean;
import org.weibo.common.SearchResultID;

public interface Analyzer 
{
	List<SearchResultID> analyze(AnalyzeBean ab);
}
