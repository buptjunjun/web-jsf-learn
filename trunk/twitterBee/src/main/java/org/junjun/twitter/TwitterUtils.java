package org.junjun.twitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;


import org.junjun.bean.part1.Item;
import org.junjun.twitter.bean.Tag2User;
import org.junjun.twitter.bean.TwitResources;
import org.junjun.twitter.bean.TwitStatus;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

public class TwitterUtils {

	
	private static  MongoOperations mongoOps = null;
	private static String host = "42.96.143.59";
	private static int port = 27017;
	private static String name = "twitter";
	private static String password="1234abcd1";
	
	static {

		try
		{
			Mongo mongo = new Mongo(host,port);
			mongo.setWriteConcern(WriteConcern.NONE);
			mongoOps = new MongoTemplate(mongo, name);
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  
	static public List<Tag2User> init(String filepath)
	{
		Gson gson = new Gson();
		
		Reader reader = null;
		try {
			reader = new  FileReader(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Tag2User> ltu =  gson.fromJson(reader,new TypeToken<List<Tag2User>>() {  
        }.getType());
		return ltu;
	}
	
	static public List<TwitStatus> getTwitStatusFromJson(String filepath)
	{
		Gson gson = new Gson();
		
		Reader reader = null;
		try {
			reader = new  FileReader(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<TwitStatus> ltu =  gson.fromJson(reader,new TypeToken<List<TwitStatus>>() {  
        }.getType());
		return ltu;
	}
	
	static public void downloadFile(String url,String path) throws MalformedURLException, IOException
	{/*
		source - the URL to copy bytes from, must not be null
		destination - the non-directory File to write bytes to (possibly overwriting), must not be null
		connectionTimeout - the number of milliseconds until this method will timeout if no connection could be established to the source
		readTimeout - the number of milliseconds until this method will timeout if no data could be read from the source*/
		 FileUtils.copyURLToFile(new URL(url), new File(path),1000*3,1000*5);
		
	}
	
	
	public static  String postItems2Server(String url,List li)
	{
		try
		{
			Gson gson = new Gson();
			String jsonObject = gson.toJson(li);
			System.out.println(jsonObject);
			DefaultHttpClient httpClient = new DefaultHttpClient(); 

	        HttpResponse response; 
	        HttpPost post=new HttpPost(); 
	        HttpEntity httpEntity; 
	        StringEntity stringEntity=new StringEntity(jsonObject.toString()); 
	        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json")); 
	        httpEntity=stringEntity; 
	        post.setEntity(httpEntity); 
	        post.setURI(new URI(url)); 
	        post.setHeader("Content-type", "application/json"); 
	        response=httpClient.execute(post); 
	        return parseHttpResponse(response);
		}
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		
		return null;


	}
	
	  private  static String parseHttpResponse(HttpResponse response) throws Exception { 
	        String jsonString=""; 
	        int status = response.getStatusLine().getStatusCode(); 
	        if (status == 200) { 
	            BufferedReader bReader = new BufferedReader(new InputStreamReader( 
	                    response.getEntity().getContent())); 
	            StringBuffer sb = new StringBuffer(""); 
	            String line = ""; 
	            String NL = System.getProperty("line.separator"); 
	            while ((line = bReader.readLine()) != null) { 
	                sb.append(line + NL); 
	            } 
	            jsonString = sb.toString(); 
	            bReader.close(); 
	        }  
	        return jsonString; 
	          
	    } 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 URL url = null;
		try {
			url = new URL("http://pbs.twimg.com/media/BW1onTUIcA");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(url.getPath());
		 
		 List<Item> li = new ArrayList<Item>();
		 Item i1 = new Item();
		 i1.setId("111");
		 Item i2 = new Item();
		 i2.setId("222");
		 
		 li.add(i1);
		 li.add(i2);
		 
		 String str = postItems2Server("http://localhost:8888/api/addResources",li);
		 System.out.print(str);
	}
	
	public static void save(Object obj)
	{
		mongoOps.save(obj);
	}
	
	public static Object get(String id ,Class cls)
	{
		return mongoOps.findById(id, cls);
	}

	public static List<TwitStatus> getTwitStatus(String userid,Date date, int limit)
	{
		List<TwitStatus> ret = null;
		Criteria c = Criteria.where("userid").is(userid).and("date").lte(date);
		Query q = new Query(c);
		q.sort().on("date",Order.DESCENDING);
		ret = mongoOps.find(q, TwitStatus.class);	
		return ret;
	}
	
	public static String fileNameFromDate(Date date)
	{
		String filename =""+date.getYear()+date.getMonth()+date.getDay()+".json";
		return filename;
	}
}

