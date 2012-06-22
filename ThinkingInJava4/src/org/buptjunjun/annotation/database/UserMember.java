package org.buptjunjun.annotation.database;

@DBTable(name="Member")
public class UserMember 
{
	@SQLString(30) String firstName;
	@SQLString(50) String lastName;
	@SQLInteger Integer age;
	@SQLString(value = 30,constrains=@Constrains(primaryKey = true)) String handle;
	
	static int memberCount;
	public String getHandle() { return handle; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String toString() { return handle; }
	public Integer getAge() { return age; }

}
