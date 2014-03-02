package org.junjun.spring.charpter2.first_competetion;

/**
 * 表演者接口 所有表演者都必须继承
 * @author junjun
 *
 */
public interface Performer {

	void perform() throws PerformanceException;
}
