package org.weibo.dao;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.weibo.common.SearchResult;
import org.weibo.common.SearchResultWeibo;
import org.weibo.common.WeiboDetails;
import org.weibo.common.WeiboMysql;
import org.weibo.common.WeiboUtil;

public class WeiboDaoTxt implements WeiboDao{

	private Logger logger = Logger.getLogger(WeiboDaoTxt.class);
	WeiboMysql db =null;
	public WeiboDaoTxt() {
		db = new WeiboMysql();
	}
	public String save(SearchResultWeibo weibos) {
		// TODO Auto-generated method stub
		return null;
	}

	public String save(SearchResult weibos) {
		// TODO Auto-generated method stub
		if(weibos == null || weibos.getDetails() == null)
			return null;
		
		List<WeiboDetails> wds = weibos.getDetails();
		String keyword = weibos.getKeyword().trim();
		int type = weibos.getType();
		
		Date date = new Date();
		
		for(WeiboDetails wd:wds)
		{
			// update the flag in db
			String primaryKey = WeiboUtil.encode(weibos.getKeyword().trim())+wd.getId().trim();
		
			if(db.exist(primaryKey))
			{
				continue;
			}
			
			try 
			{
			
				this.save2file(keyword, date , type, wd);
				db.insert(keyword, type, wd);
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("save2file error"+e.toString());
			}
		}
		
		return null;
	}

	public SearchResult search(String keyword, int flag, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	public SearchResultWeibo search(String keyword, Date from, Date to, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save2file(String keyword,Date date,int type,WeiboDetails weibodetail) throws IOException
	{
		String file = WeiboUtil.getfileName(keyword, date, type);
		File f = new File(file);
		if(!f.exists())
		{
			f.createNewFile();
			f.setWritable(true);
		}
		
		String txt = weibodetail.toString();
		txt = txt.replaceAll("\r", "");
		txt = txt.replaceAll("\n", "");
		
		FileUtils.writeStringToFile(f,txt+"\r\n", "utf-8", true);
		
		logger.info("save to file:"+f.getAbsolutePath()+": "+weibodetail.toString());
	}
}
