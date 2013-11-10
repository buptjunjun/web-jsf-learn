package org.junjun.twitter.console;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JCheckBox;

import org.apache.commons.io.FileUtils;
import org.junjun.controller.logic.PicUtil;
import org.junjun.twitter.StatusFetcher;
import org.junjun.twitter.TwitterUtils;
import org.junjun.twitter.Update;
import org.junjun.twitter.bean.ImprovedStatus;
import org.junjun.twitter.bean.Tag2User;
import org.junjun.twitter.bean.TwitStatus;
import org.junjun.twitter.bean.TwitUser;

import com.google.gson.Gson;

public class Main {

	private String host = "http://www.picfalls.com";
	private  boolean flag = true;
	private int datespan = 3;
	List<Tag2User> lt = null;
	StatusFetcher sf = new StatusFetcher();
	Update update = new Update();
	private int limitPerUser = -1;
	Gson gson= new Gson();
	
	public Main() 
	{
		lt = new ArrayList<Tag2User>();
		lt = TwitterUtils.init("taguser.json");
	}
	
	public void doit()
	{
		while(flag)
		{
			List<ImprovedStatus>  today = new ArrayList<ImprovedStatus>();
			lt = TwitterUtils.init("taguser.json");
			Date date = PicUtil.getDateBefore(new Date(),datespan );
			date.setHours(1);
			for(Tag2User t2u : lt)
			{
				String tag = t2u.getTag();
				Set<TwitUser> lus = t2u.getTwuser();
				
				// per user
				for(TwitUser tu:lus)
				{					
					String userName = tu.getName();
					List<TwitStatus> ltr = TwitterUtils.getTwitStatus(tu.getId()+"", date, 100);
					List<ImprovedStatus>  lis = update.compareOldStatus(ltr); 	
					Collections.sort(lis);					
					int size = lis.size()<this.limitPerUser?lis.size():this.limitPerUser;
					if(size > 0 )
						today.addAll(lis.subList(0, size));
				}
			}
			
			String selectedStatus = gson.toJson(today);
			String filename = TwitterUtils.fileNameFromDate(date);
			
			try {
				FileUtils.writeStringToFile(new File(filename), selectedStatus);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	public static void main(String[] args) 
	{
		new Main().doit();
	}

}
