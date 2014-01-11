package org.weibo.analyzer;

import java.util.List;

import org.weibo.common.AnalyzeBean;
import org.weibo.common.SearchResult;

public interface Analyzer 
{
	SearchResult analyze(AnalyzeBean ab);
}
