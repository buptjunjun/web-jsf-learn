package org.junjun.bean.part1;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Form1
{
	@Pattern(regexp = "[a-zA-Z0-9]+" , message="name should only contains a-z A-Z 0-9")
	@Size(min=5,max=10,message="length of name should be controlled within 5 to 10")
	private String name;
	
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name:"+name+" age:"+age;
	}
	
}
