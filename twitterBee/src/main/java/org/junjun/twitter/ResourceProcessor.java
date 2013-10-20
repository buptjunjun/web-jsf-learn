package org.junjun.twitter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junjun.twitter.bean.TwitResources;
import org.junjun.twitter.bean.TwitStatus;
import org.junjun.twitter.bean.TwitUser;

import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class ResourceProcessor extends Thread 
{
	 public String base = null;
	 
	 public List<TwitResources> process(TwitStatus ts)
	 {
		 List<TwitResources> ret = new ArrayList<TwitResources>();		 
		 String tag = ts.getTag();
		 String username = ts.getStatus().getUser().getScreenName();	 
		 String txt = ts.getStatus().getText();
		 Date createDat = ts.getStatus().getCreatedAt();
		 MediaEntity []mes = ts.getStatus().getMediaEntities();
		 
		 if(mes != null)
		 {
			 for(MediaEntity me:mes)
			 {
				 String urlstr = me.getMediaURL();
				 String type = me.getType();
				 long id = me.getId();
				 
				 URL url;
				 String path = null;
				try {
					url = new URL(urlstr);		
					path = tag+"/"+username+"/"+url.getPath();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					path = tag+"/"+username+"/"+id+".jpg";
				}
			 TwitResources tr = new TwitResources();
			 tr.setId(id);
			 tr.setUrl(urlstr);
			 tr.setTxt(txt);
			 tr.setType(type);
			 tr.setUserName(username);
			 tr.setDate(createDat);
			 tr.setDate(new Date());
			 tr.setPath(path);
			 tr.setStatusID(ts.getId());
			 ret.add(tr);	
			 }
		 }
		 else
		 {
			 TwitResources tr = new TwitResources();
			 tr.setId(ts.getId());
			 tr.setUrl(null);
			 tr.setTxt(txt);
			 tr.setType("text");
			 tr.setUserName(username);
			 tr.setDate(createDat);
			 tr.setDate(new Date());
			 tr.setPath(null);
			 tr.setStatusID(ts.getId());
			 ret.add(tr);			
		 }
		 
		 return ret;
	 }
	 
	 public void downloadPhoto( List<TwitResources> ltr)
	 {
		if(ltr!=null)
		{
			for(TwitResources tr:ltr)
			{
				if(!StringUtils.isEmpty(tr.getType()) && tr.getType().equals("photo"))
				{
					String path = this.base+"/"+tr.getPath();
					System.out.println();
					try {
						TwitterUtils.downloadFile(tr.getUrl(), path);
					} catch (Exception e) {
						// TODO Auto-generated catch block
					
						System.out.println("! fiail to download "+tr.getTag()+">"+tr.getUserName()+">"+tr.getStatusID()+">"+tr.getUrl());
						e.printStackTrace();
						continue;
					}
					System.out.println("@ success to download "+tr.getTag()+">"+tr.getUserName()+">"+tr.getStatusID()+">"+tr.getUrl());
				}
			}
		}
	 }
	  public static void main(String[] args) {
		  
		  
	  }

}