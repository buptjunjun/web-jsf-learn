package org.junjun.designpattern.proxy;

/**
 * ProxyForum 是 Forum的代理， Forum的各个操作时没有限制的，但是不同的用户对Forum的操作有不同的限制
 * 比如VISITOR只能浏览不能修改，User和ADMIN可以浏览和修改。我们可以使用一个代理类来管理这个权限
 * @author junjun
 *
 */
public class ProxyForum 
{
	public static final int VISITOR = 0; //游客；
	public static final int USER = 0; //普通用户
	public static final int ADMIN = 0; //管理员
	
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
	 * 代理方法,通过role来控制用户的权限
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
