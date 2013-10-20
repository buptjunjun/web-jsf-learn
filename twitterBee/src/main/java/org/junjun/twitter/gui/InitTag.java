package org.junjun.twitter.gui;

import java.util.ArrayList;
import java.util.List;

import org.junjun.mongo.DAOMongo;
import org.junjun.twitter.TwitterUtils;
import org.junjun.twitter.bean.Tag;
import org.junjun.twitter.bean.Tag2User;

public class InitTag 
{
	DAOMongo daomongo=new DAOMongo("twitterBee");

	public void initTag()
	{
		List<Tag2User> lt = new ArrayList<Tag2User>();
		lt = TwitterUtils.init("taguser.json");
		System.out.println(lt);
		for(int i=0;i < lt.size();i ++)
		{	
			Tag2User tu = (Tag2User)lt.get(i);
			Tag tag=new Tag();
			tag.setId(tu.getTag());
			tag.setTag(tu.getTag());
			this.daomongo.insert(tag);
		}
		
	}
	public static void main(String [] args)
	{
		new InitTag().initTag();
		 
	}
}
