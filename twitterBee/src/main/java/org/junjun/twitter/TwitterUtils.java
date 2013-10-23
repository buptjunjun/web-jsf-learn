package org.junjun.twitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junjun.twitter.bean.Tag2User;
import org.junjun.twitter.bean.TwitStatus;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TwitterUtils {

	
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
	

	static public void downloadFile(String url,String path) throws MalformedURLException, IOException
	{/*
		source - the URL to copy bytes from, must not be null
		destination - the non-directory File to write bytes to (possibly overwriting), must not be null
		connectionTimeout - the number of milliseconds until this method will timeout if no connection could be established to the source
		readTimeout - the number of milliseconds until this method will timeout if no data could be read from the source*/
		 FileUtils.copyURLToFile(new URL(url), new File(path),1000*3,1000*5);
		
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
	}

}
