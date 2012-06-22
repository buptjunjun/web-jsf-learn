package org.buptjunjun.annotation;

public class Testable {
	public void execute()
	{
		System.out.println("Executing..");
	}
	
	@Test void testExecute(){this.execute();};

}
