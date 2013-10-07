package org.junjun.bean.part1;

import java.util.List;

public class UIItem {
	private Item item = null;
	private List<Comment> comments = null;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
