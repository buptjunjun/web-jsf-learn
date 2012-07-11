package org.buptjunjun.exception;


public class ReThrowException 
{
	public void funcA() throws Exception
	{
		throw new Exception("originited from funcA");
	}
	
	/**
	 * catch 之后重新抛出Exception
	 * @throws Exception
	 */
	public void funcB() throws Exception
	{
	
		try {
			funcA();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println("-------------in funcB---------------");
			//e.printStackTrace();	
			// 直接使用到跟下一层 堆栈顶端还是在 funcA
			throw e;
		}
	}
	

	
	/**
	 * catch 之后 将当前的堆栈作为 原始堆栈 ，重新抛出Exception
	 * @throws Exception
	 */
	public void funcC() throws Exception
	{	
		try {
			funcA();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	System.out.println("-------------in funcC---------------");
		//	e.printStackTrace();
			// 使用fillInStackTrace将当前调用堆栈作为
			throw (Exception)e.fillInStackTrace();
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void funcD() throws Exception
	{
	
		funcA();	
	}
	
	static public void main( String [] args)
	{
		ReThrowException rt = new ReThrowException();
		
		try {
			rt.funcB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			rt.funcC();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			rt.funcD();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
