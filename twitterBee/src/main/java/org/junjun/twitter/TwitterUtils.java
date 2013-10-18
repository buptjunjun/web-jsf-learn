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
		List<Tag2User> ltu =  gson.fromJson(reader, new ArrayList<Tag2User>().getClass());
		return ltu;
	}
	

	static public void downloadFile(String url,String path) throws MalformedURLException, IOException
	{
		 FileUtils.copyURLToFile(new URL("url"), new File(path));
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
