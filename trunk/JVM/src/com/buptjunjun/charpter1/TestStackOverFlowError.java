package com.buptjunjun.charpter1;

/**
 * VM args: -Xss128k
 *
 * @author andyWebsense
 *
 */
public class TestStackOverFlowError {

	private int deep = 0;
	public  void foo()
	{
		deep++;
		//byte [] bytes = new byte[1024*1024];
		
		foo();
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestStackOverFlowError ts = new TestStackOverFlowError();
		try
		{
			ts.foo();
		}
		catch(Throwable e)
		{
			System.out.println("deep:"+ts.deep);
			e.printStackTrace();
			System.exit(0);
		}
	}

}
