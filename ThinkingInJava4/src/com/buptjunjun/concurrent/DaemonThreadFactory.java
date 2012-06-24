package com.buptjunjun.concurrent;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory
{

	@Override
	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}

}
