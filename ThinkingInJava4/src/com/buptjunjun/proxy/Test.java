package com.buptjunjun.proxy;

import java.lang.reflect.Proxy;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BusinessProcessor psimpl = new BusinessProcessorImpl();
		BusinessProcessorHandler pshdl = new  BusinessProcessorHandler(psimpl);
		BusinessProcessor bp = (BusinessProcessor) Proxy.newProxyInstance(psimpl.getClass().getClassLoader(), psimpl.getClass().getInterfaces(), pshdl);
		bp.process1();
		bp.process2();
	}

}
