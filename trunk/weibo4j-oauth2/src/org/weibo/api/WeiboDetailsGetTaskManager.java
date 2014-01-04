package org.weibo.api;

import javax.swing.JOptionPane;

public class WeiboDetailsGetTaskManager {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String accountStr = JOptionPane.showInputDialog("Please input the account you have got:");
		try
		{
			int account = Integer.parseInt(accountStr);
			for(int i = 0; i<account&&i<15;i++)
			{
				Thread thread = new Thread(new WeiboDetailsGetTaskSina());
				thread.setDaemon(true);
				thread.start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		
	}

}
