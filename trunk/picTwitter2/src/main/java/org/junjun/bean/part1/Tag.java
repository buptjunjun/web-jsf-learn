package org.junjun.bean.part1;

public class Tag 
{
	private String id =null;  // id = type;


	private String name;  //picture
	private String desc;   // grils  animals 
	private String type;  // picture anlimal cute
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		if(type!=null)
			return type.hashCode();
		else 
			return 100;
		
	}
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 == null)
			return false;
		
		if(!Tag.class.isInstance(arg0))
			return false;
		Tag tag = (Tag)arg0;
		
		if(this.type==null && tag.type==null)
			return true;
		
		if(this.type!=null)
			return this.type.equals(tag.getType());
		else 
			return  tag.getType().equals(this.type);
			
	}
}
