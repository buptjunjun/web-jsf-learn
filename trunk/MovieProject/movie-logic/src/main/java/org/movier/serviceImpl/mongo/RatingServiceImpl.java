package org.movier.serviceImpl.mongo;

import java.util.ArrayList;
import java.util.List;

import org.movier.bean.BResource;
import org.movier.bean.Comment;
import org.movier.bean.Rating;
import org.movier.service.CommentService;
import org.movier.service.RatingService;
import org.movier.service.ResourceService;

public class RatingServiceImpl implements RatingService{

	public Rating getRating(String id) {
		// TODO Auto-generated method stub
		return mock();
	}

	public String updateRating(Rating rating) {
		// TODO Auto-generated method stub
		System.out.println(rating);
		return null;
	}

	public Rating delRating(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Rating addRating(Rating Rating) {
		// TODO Auto-generated method stub
		return null;
	}

	private Rating mock()
	{
		Rating rat = new Rating();
		rat.setBad(100);
		rat.setGood(200);
		
		return rat;
	}

	
}
