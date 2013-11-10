package org.junjun.twitter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junjun.controller.logic.PicServicesMongo;
import org.junjun.mongo.DAOMongo;
import org.junjun.twitter.bean.ImprovedStatus;
import org.junjun.twitter.bean.TwitStatus;

public class Update 
{
	
	public List<ImprovedStatus> compareOldStatus(List<TwitStatus> ltnew)
	{
		List<ImprovedStatus> ret = new ArrayList<ImprovedStatus>();
				
		for(TwitStatus newts :ltnew)
		{
			TwitStatus old = (TwitStatus) TwitterUtils.get(newts.getId()+"", TwitterUtils.class);
			
			if(old == null)
				continue;
			
			long timespan = (new Date().getTime() - old.getDate().getTime())/1000; //seconds
			
			int countNow = newts.getStatus().getRetweetCount()+newts.getStatus().getFavoriteCount();
			int countOld = old.getStatus().getRetweetCount()+old.getStatus().getFavoriteCount();
			int improve = countNow - countOld;			
			ImprovedStatus is = new ImprovedStatus();
			is.setId(newts.getId()+"");
			is.setFavouriteCountNow(newts.getStatus().getFavoriteCount());
			is.setRetwitCountNow(newts.getStatus().getRetweetCount());
			
			is.setFavouriteCountOld(old.getStatus().getFavoriteCount());
			is.setRetwitCountOld(old.getStatus().getRetweetCount());
			
			is.setTimespan(timespan);
			is.setStatus(old);
			is.caculateRate();
			
			ret.add(is);
			
		}
		return ret;
	}
}
