package org.junjun.spring.charpter2.first_competetion;

/**
 * ������̨�� ��һ��������	
 * @author junjun
 *
 */
public class Stage
{
	private Stage(){}
	private static class StageHolder
	{
		static Stage instance = new Stage();
	}
	
	public static Stage getInstance()
	{
		return StageHolder.instance;
	}
}
