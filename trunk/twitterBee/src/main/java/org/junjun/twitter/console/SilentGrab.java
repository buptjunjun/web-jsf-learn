package org.junjun.twitter.console;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * silent grab and caculate the increased twits
 * @author junjun
 *
 */
public class SilentGrab{

	private long period = 1000*60*60*24*2;

	// timertask for grabing twits
	private TimerTask grab = new GrabTimerTask();
	
	// timertask for updating twits
	private TimerTask update = new UpdateTimerTask();
	
	// when to start the grab action
	private Date grabStartTime = null;
	
	// when to start the grab action
	private Date updateStartTime = null;
	
	static private final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss"); 
	
	public void setTimerAndStart()
	{
		grabStartTime = new Date();
	/*	grabStartTime.setHours(1);
		grabStartTime.setMinutes(0);*/
		
		updateStartTime = new Date();
		/*updateStartTime.setHours(23);
		updateStartTime.setMinutes(0);*/
		
		/*Timer timerGrab = new Timer();
		timerGrab.scheduleAtFixedRate(this.grab, grabStartTime, period);*/
		
		Timer timerUpdate = new Timer();
		timerUpdate.scheduleAtFixedRate(this.update, updateStartTime, period);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		new SilentGrab().setTimerAndStart();
	}

}
