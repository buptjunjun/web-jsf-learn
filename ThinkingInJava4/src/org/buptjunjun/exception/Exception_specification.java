package org.buptjunjun.exception;


/**
 *   异常说明 可以再方法后面添加 异常说明，例如public void test_exception_specifiaction() throws MyException, ArrayIndexOutOfBoundsException
 *   调用它的方法必须处理这些异常，会在编译阶段检查是否处理了异常说明中的方法
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
		
		/* test_exception_specifiaction设置了 MyException，和 ArrayIndexOutOfBoundsException两个
		 *异常说明,调用者必须处理
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
		
			// 打印每一个调用堆栈的信息
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
