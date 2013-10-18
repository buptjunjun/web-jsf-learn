package org.junjun.twitter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junjun.twitter.bean.Tag2User;
import org.junjun.twitter.bean.TwitUser;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

public class main {

	static public void main(String [] args) throws MalformedURLException, IOException
	{
		List<Tag2User> lt = new ArrayList<Tag2User>();
		lt = TwitterUtils.init("D:\\\\movie\\\\twitterBee\\\\taguser.json");		 
		 System.out.println();
		 
		 File file = new File("C:\\Users\\andyWebsense\\Desktop\\aaa.png");
		 FileUtils.copyURLToFile(new URL("https://pbs.twimg.com/media/BW1BAQaIUAAbejD.jpg"), file);
	}
}
