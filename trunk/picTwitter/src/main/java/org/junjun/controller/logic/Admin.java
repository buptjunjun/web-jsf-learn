package org.junjun.controller.logic;

import java.util.concurrent.TimeUnit;

import org.junjun.bean.part1.Item;
import org.junjun.bean.part1.Tag;

public class Admin 
{
	
	static PicServices  picservice = new PicServicesMongo(); 
	static String types [] = {"pictures","animals"};
	
	static String urls [] =
		{"http://p0.pstatp.com/medium/320/6704568639",
		"http://p0.pstatp.com/medium/307/94968627",
		"http://p0.pstatp.com/medium/320/6701267986",
		"http://p0.pstatp.com/medium/308/1583047316",
		"http://p0.pstatp.com/medium/320/6433395308",
		"http://p0.pstatp.com/medium/320/6472989105",
		"http://p0.pstatp.com/medium/307/4085342887",
		"http://p0.pstatp.com/medium/307/4085342887"
		};
	static String urls1 [] =
		{"http://ww1.sinaimg.cn/large/6b269d7dtw1e8t9zzybxhj20e820zqcq.jpg",
		"http://p0.pstatp.com/large/307/94968627",
		"http://ww4.sinaimg.cn/large/5cfc088egw1e89atr6d1nj20eg4qo1j6.jpg",
		"http://p0.pstatp.com/large/308/1583047316",
		"http://ww1.sinaimg.cn/large/3ef7ef0ejw1e8n0ytzlk6j20dc0hstbh.jpg",
		"http://ww3.sinaimg.cn/large/67574fcbjw1e8bzjq6t69j20c81by45k.jpg",
		"http://p0.pstatp.com/large/307/4085342887",
		"http://ww1.sinaimg.cn/large/643ea05cjw1e80z5fq9iij20c80uegp4.jpg",
		};
	static String descs [] =
		{"test",
		" it is good ",
		"it is good",
		"it is bad",
		"it is good",
		"it is bad",
		"it is good",
		"it is good",
		};
	
	static public void insertTag()
	{
		for(String type:types)
		{
			Tag tag = new Tag();
			tag.setId(type);
			tag.setType(type);
			tag.setName(type);
			picservice.insert(tag);
		}
	}
	
	static public void insertItems()
	{
		
		for(int i = 0; i< urls.length; i++)
		{
			String url = urls[i];
			String url1 = urls1[i];
			
			Item item = new Item();
			item.setUrl(url);
			item.setUrl1(url1);		
			item.setType("pictures");
			item.setId(PicUtil.urlEncode(url1));
			item.setDesc(descs[i]);
			
			picservice.insertItem(item);
			
		}
		
		
	}
	
	public static void main(String [] args)
	{
		insertTag();
		insertItems();
	}

}
