package org.weibo.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.weibo.common.Constants;
import org.weibo.common.WeiboDetails;

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
public class WeiboDetailsGetTaskSina extends Thread implements  WeiboDetailsGetTask
{
	private int type = Constants.SINA;
	private String id = null;
	private static Logger logger = Logger.getLogger(WeiboDetailsGetTaskSina.class);
	
	WeiboDetailsGetTaskSina(int type,String id)
	{
		this.type = type;
		this.id = id;
	}
	
	private static String accessToken = null;
	
	/**
	 * get the accessToken
	 */
	public WeiboDetailsGetTaskSina() 
	{
		synchronized(WeiboDetailsGetTaskSina.class)
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
	}
	
	/**
	 * get the details of weibo from the weibo id.
	 * @param type : sina or Tecent
	 * @param id: weibo id
	 * @return WeiboDetails
	 */
	public WeiboDetails process(int type, String id) 
	{
		WeiboDetails weibodetails = new WeiboDetails();
		
		logger.info("start to getting weibo:  type="+(type==Constants.SINA?"Sina":"Tecent")+"id="+id);
		
		Timeline tm = new Timeline();
		tm.client.setToken(accessToken);
		try
		{
			// get the details of one weibo according to it's id
			Status status = tm.showStatus(id);			
			
			// convert Status to WeiboDetails
			weibodetails = Status2WeiboDetails(status);
			if(weibodetails!=null)
				logger.info("success to getting weibo: "+weibodetails.toString());
			
			return weibodetails;
		} catch (WeiboException e) {
			e.printStackTrace();
			logger.info("fail to getting weibo:  type="+(type==Constants.SINA?"Sina":"Tecent")+"id="+id+";"+e.toString());
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
	
	
	@Override
	public void run() {
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
