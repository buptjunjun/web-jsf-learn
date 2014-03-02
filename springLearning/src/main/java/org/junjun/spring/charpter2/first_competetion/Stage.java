package org.junjun.spring.charpter2.first_competetion;

/**
 * 比赛舞台类 是一个单例类	
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
