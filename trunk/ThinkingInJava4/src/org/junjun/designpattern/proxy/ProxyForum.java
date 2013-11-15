package org.junjun.designpattern.proxy;

/**
 * ProxyForum �� Forum�Ĵ��� Forum�ĸ�������ʱû�����Ƶģ����ǲ�ͬ���û���Forum�Ĳ����в�ͬ������
 * ����VISITORֻ����������޸ģ�User��ADMIN����������޸ġ����ǿ���ʹ��һ�����������������Ȩ��
 * @author junjun
 *
 */
public class ProxyForum 
{
	public static final int VISITOR = 0; //�οͣ�
	public static final int USER = 0; //��ͨ�û�
	public static final int ADMIN = 0; //����Ա
	
	private Forum forum = null;
	private int role = VISITOR;
	
	public ProxyForum(Forum forum,int role)
	{
		this.role = role;
	}
	
	public void readItem()
	{
		this.forum.readItem();
	}
	
	/**
	 * ������,ͨ��role�������û���Ȩ��
	 * @param conent
	 */
	public void writeItem(String conent)
	{
		// if the role is Visitor , prevent the write process;
		if(this.role == VISITOR)
		{
			System.out.println("VISITOR can't modify content!");
		}
		else // if the role is ADMIN or USER , just do it
		{
			// use the proxy write content. 
			this.forum.writeItem(conent);
		}
	}
	
}
