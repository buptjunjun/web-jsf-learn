package org.cb.util;

import java.util.ArrayList;
import java.util.List;

import org.cb.data.Blog;
import org.cb.data.DAOMongo;
import org.junit.Test;

public class testSearch
{
	@Test
	public void test()
	{
		String id = converter.urlEncode("http://blog.chinaunix.net/uid-600330-id-2088810.html");
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		List<Blog> lb = DAOMongo.getInstance().searchBlog("_id",ids);
		
		System.out.print("");
	}
}
