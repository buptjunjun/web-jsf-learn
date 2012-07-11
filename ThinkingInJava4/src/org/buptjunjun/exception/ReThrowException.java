package org.buptjunjun.exception;


public class ReThrowException 
{
	public void funcA() throws Exception
	{
		throw new Exception("originited from funcA");
	}
	
	/**
	 * catch ֮�������׳�Exception
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
			// ֱ��ʹ�õ�����һ�� ��ջ���˻����� funcA
			throw e;
		}
	}
	

	
	/**
	 * catch ֮�� ����ǰ�Ķ�ջ��Ϊ ԭʼ��ջ �������׳�Exception
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
			// ʹ��fillInStackTrace����ǰ���ö�ջ��Ϊ
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
