package org.junjun.twitter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junjun.twitter.bean.TwiConstant;
import org.junjun.twitter.bean.TwitResources;
import org.junjun.twitter.bean.TwitStatus;
import twitter4j.MediaEntity;


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
		 
		 if(mes != null&& mes.length>0)
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
					continue;
				}
			 TwitResources tr = new TwitResources();
			 tr.setTag(ts.getTag());
			 tr.setId(id);
			 tr.setUrl(urlstr);
			 tr.setTxt(txt);
			 tr.setType(type);
			 tr.setUserName(username);
			 tr.setDate(createDat);
			 tr.setProcessDate(new Date());
			 tr.setPath(path);
			 tr.setStatusID(ts.getId());
			 tr.setScore(ts.getScore());
			 ret.add(tr);	
			 }
		 }
		 else
		 {
			 TwitResources tr = new TwitResources();
			 tr.setTag(ts.getTag());
			 tr.setId(ts.getId());
			 tr.setUrl(null);
			 tr.setTxt(txt);
			 tr.setType(TwiConstant.TypeText);
			 tr.setUserName(username);
			 tr.setDate(createDat);
			 tr.setProcessDate(new Date());
			 tr.setPath(null);
			 tr.setStatusID(ts.getId());
			 tr.setScore(ts.getScore());
			 ret.add(tr);			
		 }
		 
		 return ret;
	 }
	 
	 public List<TwitResources> process(List<TwitStatus> lts)
	 {
		 List<TwitResources> ret = new ArrayList<TwitResources>();
		 
		 if(lts == null)
			 return ret;
		 
		 for(TwitStatus ts: lts)
		 {
			 List<TwitResources> tmp = this.process(ts);
			 if(tmp!=null)
				 ret.addAll(tmp);
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
