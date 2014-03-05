package org.junjun.spring.charpter3.second_autowire;

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
