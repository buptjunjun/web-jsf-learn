package org.junjun.bean.part1;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class User 
{
	public static final String GOOGLE = "1";
	public static final String TWITTER = "2";
	public static final String FACEBOOK = "3";
	public static final String THIS = "0";
	
	@Id
	private String id =null;
	
	private String name = null;
	private String password  = null;
	private String gender = null; //"m","f","o"
	private String pic =null;
	private String url = null;	
	private String source = "";  // google facebook or twitter.
	private String idSource = null; // the id of the source website.
	
	@Temporal(TemporalType.DATE)
	private Date date = new Date();
	
	
	@Lob
	private Object otherInfo = null;   // other useful info 
	
	public Object getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(Object otherInfo) {
		this.otherInfo = otherInfo;
	}
	public String getIdSource() {
		return idSource;
	}
	public void setIdSource(String idSource) {
		this.idSource = idSource;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public static String getGoogle() {
		return GOOGLE;
	}
	public static String getTwitter() {
		return TWITTER;
	}
	public static String getFacebook() {
		return FACEBOOK;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
