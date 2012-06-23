package org.buptjunjun.annotation.database;

@DBTable(name="UserMember")
public class UserMember 
{
	private @SQLString(30) String firstName;
	private @SQLString(50) String lastName;
	private @SQLInteger Integer age;
	private @SQLString(value = 30,constrains=@Constrains(primaryKey = true)) String handle;
	
	static int memberCount;
	public String getHandle() { return handle; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String toString() { return handle; }
	public Integer getAge() { return age; }
	
	public UserMember(){};
	
	public static void main(String [] args)
	{
		System.out.println("hello");
	}

}
