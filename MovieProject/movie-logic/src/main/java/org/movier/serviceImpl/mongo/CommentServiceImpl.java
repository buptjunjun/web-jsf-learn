package org.movier.serviceImpl.mongo;

import java.util.ArrayList;
import java.util.List;

import org.movier.bean.BResource;
import org.movier.bean.Comment;
import org.movier.service.CommentService;
import org.movier.service.ResourceService;

public class CommentServiceImpl implements CommentService{

	public Comment getCommnet(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Comment> getCommnets(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateCommnet(Comment comment) {
		// TODO Auto-generated method stub
		return null;
	}

	public String addCommnet(Comment comment) {
		// TODO Auto-generated method stub
		return null;
	}

	public String delCommnet(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Comment> mock()
	{
		List<Comment> ret = new ArrayList<Comment>();
		
		Comment com = new Comment();
		com.setContent("不错");
		return ret;
	}

	
}
