package org.junjun.bean.part1;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Constant {
	
	static public final String newest = "newset"; 
	static public final String weekly = "weekly"; 
	static public final String monthly = "monthly"; 
	static public final String hottest = "hottest"; 
	
	static public final List<String> sortby = Arrays.asList(new String [] {newest ,weekly,monthly,hottest});
	static public final String default_sort = weekly;
	static public final int LIMIT = 20;
	public static final int REST_LIMIT = 20;
	public static final int COMMENT_LIMIT = 10;
	public static final int MAX_SKIP = 20;
}
