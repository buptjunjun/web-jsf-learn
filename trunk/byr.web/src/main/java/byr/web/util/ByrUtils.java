package byr.web.util;

import byr.analyzer.analyzer.HtmlStructuredData;
import byr.web.bean.ResultItem;

public class ByrUtils {

	static public ResultItem convert(HtmlStructuredData hsd)
	{
		ResultItem ret = new ResultItem();
		ret.setId(hsd.getId());
		ret.setDate(hsd.getCreatedDate());
		ret.setTitle(hsd.getTitle());
		ret.setUrl(hsd.getUrl());
		ret.setContent(hsd.getContent());
		
		return ret;
	}

}
