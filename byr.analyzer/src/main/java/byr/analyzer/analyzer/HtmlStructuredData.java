package byr.analyzer.analyzer;

import java.util.Date;

public class HtmlStructuredData {

	private String id = null;
	private String url = null;
	private String title =null;
	private String content = null;
	private int imgs = -1;
	private int videos = -1;
	private Date createdDate = null;
	private Date collectedDate = new Date();
	
	
	public int getImgs() {
		return imgs;
	}
	public void setImgs(int imgs) {
		this.imgs = imgs;
	}
	public int getVideos() {
		return videos;
	}
	public void setVideos(int videos) {
		this.videos = videos;
	}
	public Date getCollectedDate() {
		return collectedDate;
	}
	public void setCollectedDate(Date collectedDate) {
		this.collectedDate = collectedDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	

}
