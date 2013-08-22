package org.movier.service;

import java.util.List;

import org.movier.bean.Comment;

public interface  CommentService 
{
	public Comment getCommnet(String id);
	public List<Comment> getCommnets(String id);
	public String updateCommnet(Comment comment);
	public String addCommnet(Comment comment);
	public String delCommnet(String id);
}
