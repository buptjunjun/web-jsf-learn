package org.weibo.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.weibo.common.Constants;
import org.weibo.common.KeywordInfo;
import org.weibo.common.KeywordInfoManager;
import org.weibo.common.SearchResultID;
import org.weibo.common.WeiboDetails;
import org.weibo.common.WeiboMysql;
import org.weibo.common.WeiboUtil;

import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

/**
 * get details of sina weibo
 * @author junjun
 *
 */
public class WeiboDetailsGetTaskSina implements  WeiboDetailsGetTask
{
	
	private static Logger logger = Logger.getLogger(WeiboDetailsGetTaskSina.class);
	private boolean flag=true;
	private static String accessToken = null;
	private WeiboMysql database=null;
	
	/**
	 * get the accessToken
	 */
	public WeiboDetailsGetTaskSina() 
	{
		this.database = new WeiboMysql(); 
		login();
	}
	

	private void login()
	{
		if(StringUtils.isEmpty(accessToken))
		{
			Oauth oauth = new Oauth();
		
			try 
			{
				BareBonesBrowserLaunch.openURL(oauth.authorize("code",null,null));
				
				//the code  when direct your specified url,when you create the app
				String code = JOptionPane.showInputDialog("code");
				Log.logInfo("code: " + code);
				try
				{
					accessToken = oauth.getAccessTokenByCode(code).getAccessToken();
					logger.info("Successfully get access token:"+accessToken);
				} 
				catch (WeiboException e) 
				{
					if(401 == e.getStatusCode()){
						Log.logInfo("Unable to get the access token.");
						logger.info("Unable to get the access token");
						accessToken = null;
					}else{
						logger.info(e.toString());
						accessToken = null;
					}
				}
			} catch (WeiboException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.info(e1.toString());
			}
		}
	}
	
	
	public void run() 
	{	
		while(flag)
		{
			KeywordInfo ki = KeywordInfoManager.getInstance().getNextKeywordInfo();
			String k = ki.getKeyword();
			try {
				SearchResultID sri =this.database.search(k, Constants.UNFETCH, Constants.SINA);
				List<String> ids = sri.getIds();
				String keyword = sri.getKeyword();
				int type = sri.getType();
				if(ids!=null)
				{
					for(String id:ids)
					{
						// update the flag in db
						String primaryKey = WeiboUtil.encode(keyword)+id;
						this.database.update(type, primaryKey, Constants.FETCH);
						
						WeiboDetails wd = this.process(keyword, type, id);
						if(wd==null)
						{							
							// update the flag in db
							primaryKey = WeiboUtil.encode(keyword)+id;
							this.database.update(type, primaryKey, Constants.UNFETCH);
							continue;
						}
						else
						{
							// save to file
							this.save2file(keyword, new Date(), type, wd);							
						}
						
						TimeUnit.SECONDS.sleep(36);
					}
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * get the details of weibo from the weibo id.
	 * @param type : sina or Tecent
	 * @param id: weibo id
	 * @return WeiboDetails
	 */
	public WeiboDetails process(String keyword,int type, String id) 
	{
		WeiboDetails weibodetails = new WeiboDetails();
		
		logger.info("start to getting weibo:keyword="+keyword+"  type="+(type==Constants.SINA?"Sina":"Tecent")+"id="+id);
		
		Timeline tm = new Timeline();
		tm.client.setToken(accessToken);
		try
		{
			// get the details of one weibo according to it's id
			Status status = tm.showStatus(id);
			
			// convert Status to WeiboDetails
			weibodetails = Status2WeiboDetails(status);
			if(weibodetails!=null)
				logger.info("success to getting weibo:keyword="+keyword+ "; "+weibodetails.toString());
			
			return weibodetails;
		}
		catch (WeiboException e) 
		{
			e.printStackTrace();
			
			if(e.toString().contains("not exist"))
			{
				String primaryKey = WeiboUtil.encode(keyword)+id;
				this.database.delete(primaryKey);
			}
				
			// sleep 3 minutes if weiboException occurs
			try 
			{
				TimeUnit.MINUTES.sleep(1);
			}
			catch (InterruptedException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info("fail to getting weibo::keyword="+ keyword+ "; "+ "  type="+(type==Constants.SINA?"Sina":"Tecent")+"id="+id+";"+e.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * convert the Status to WeiboDetails
	 * @param status
	 * @return
	 */
	public static WeiboDetails Status2WeiboDetails(Status status)
	{
		if(status == null)
			return null;
		
		WeiboDetails weibodetails = new WeiboDetails();
		weibodetails.setId(status.getId());
		weibodetails.setCreateAt(status.getCreatedAt());
		weibodetails.setContent(status.getText());
		weibodetails.setCommentCount(status.getCommentsCount());
		weibodetails.setRettwitCount(status.getRepostsCount());		
		
		User user = status.getUser();
		
		if(user!=null)
		{
			weibodetails.setUserID(user.getId());
			weibodetails.setUserLocation(user.getLocation());
			weibodetails.setUserName(user.getName());
			weibodetails.setUserDescription(user.getDescription());
		}
		return weibodetails;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new WeiboDetailsGetTaskSina().run();
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
		FileUtils.writeStringToFile(f, weibodetail.toString()+"\r\n", "utf-8", true);
		
		logger.info("save to file:"+f.getAbsolutePath()+": "+weibodetail.toString());
	}
	

}
