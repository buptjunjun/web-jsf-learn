package org.buptjunjun.exception;


/**
 *   �쳣˵�� �����ٷ���������� �쳣˵��������public void test_exception_specifiaction() throws MyException, ArrayIndexOutOfBoundsException
 *   �������ķ������봦����Щ�쳣�����ڱ���׶μ���Ƿ������쳣˵���еķ���
 */
public class Exception_specification {

	
	
	static public void test_exception_specifiaction() throws MyException, ArrayIndexOutOfBoundsException
	{
		System.out.println("in test_exception_specifiaction\n");
		int [] a = new int[1];
		a[2] = 1;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* test_exception_specifiaction������ MyException���� ArrayIndexOutOfBoundsException����
		 *�쳣˵��,�����߱��봦��
		*/
		try 
		{
			test_exception_specifiaction();						
		} 
		catch (ArrayIndexOutOfBoundsException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Caught Exception");
			System.out.println("getMessage():" + e.getMessage());
			System.out.println("getLocalizedMessage():" +
			e.getLocalizedMessage());
			System.out.println("toString():" + e);
			System.out.println("printStackTrace():");
			e.printStackTrace(System.out);
			System.out.println("------------------");
		
			// ��ӡÿһ�����ö�ջ����Ϣ
			for(StackTraceElement ste : e.getStackTrace())
			    System.out.println(" ## " + ste.getClassName()+":" + ste.getLineNumber()+": "+ste.getMethodName());
		}
		catch (MyException e) 
		{
			// TODO Auto-generated catch block			
			e.printStackTrace();
			
		}
		
	}

}
