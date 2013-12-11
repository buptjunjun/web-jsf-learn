package com.buptjunjun.charpter1;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author junjun
 * ≤‚ ‘ OutOFMemoryError VM≤Œ ˝£∫-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class TestOutOfMemoryError {
	
	byte [] bytes = new byte[1024*1024];
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<TestOutOfMemoryError> list = new ArrayList<TestOutOfMemoryError>();


		while(true)
		{
			list.add(new TestOutOfMemoryError());
            System.out.println("");
		}
	}

}
