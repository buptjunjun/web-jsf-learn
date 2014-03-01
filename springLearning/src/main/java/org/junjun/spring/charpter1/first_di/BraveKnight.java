package org.junjun.spring.charpter1.first_di;

/**
 * BraveKnight 只依赖 Qeust接口 不依赖 Qest的实现 减少的耦合
 * 注入的时候可以更好滴使用依赖注入
 * @author junjun
 *
 */
public class BraveKnight implements Knight{
	
	private Quest quest = null; 
	
	public BraveKnight(Quest quest)
	{
		this.quest = quest;
		
	}
	
	public void embarkOnQuest() {
		
		this.quest.embark();
	}
}
