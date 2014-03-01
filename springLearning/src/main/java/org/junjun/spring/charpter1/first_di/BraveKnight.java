package org.junjun.spring.charpter1.first_di;

/**
 * BraveKnight ֻ���� Qeust�ӿ� ������ Qest��ʵ�� ���ٵ����
 * ע���ʱ����Ը��õ�ʹ������ע��
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
