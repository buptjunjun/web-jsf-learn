package byr.web.util;

public class WebConfig 
{
	static public int taskThreads = 20;   // how many SearchTask are allowed simutaneously
	static public int waitLimit =   2;      // wati limit for SearchTask
	static public String solr_server_url = "http://localhost:8983/solr/collection1";
	
}
