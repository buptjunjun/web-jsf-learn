package org.weibo.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.weibo.common.WeiboDetails;

import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

/**
 * get details of sina weibo
 * @author junjun
 *
 */
public class WeiboDetailsGetterSina 
{

	private static String accessToken = null;
	
	/**
	 * get the accessToken
	 */
	public WeiboDetailsGetterSina() 
	{
		synchronized(WeiboDetailsGetterSina.class)
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
					} 
					catch (WeiboException e) 
					{
						if(401 == e.getStatusCode()){
							Log.logInfo("Unable to get the access token.");
							accessToken = null;
						}else{
							e.printStackTrace();
						}
					}
				} catch (WeiboException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		// TODO Auto-generated method stub
		Timeline tm = new Timeline();
		tm.client.setToken(accessToken);
		try
		{
			// get the details of one weibo according to it's id
			Status status = tm.showStatus(id);			
		
			// convert Status to WeiboDetails
			weibodetails = Status2WeiboDetails(status);
			return weibodetails;
		} catch (WeiboException e) {
			e.printStackTrace();
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
		WeiboDetails weibodetails = new WeiboDetails();
		
		return weibodetails;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
