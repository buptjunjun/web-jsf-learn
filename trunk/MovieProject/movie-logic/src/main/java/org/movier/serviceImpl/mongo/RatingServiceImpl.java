package org.movier.serviceImpl.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.movier.bean.BResource;
import org.movier.bean.Comment;
import org.movier.bean.Rating;
import org.movier.service.CommentService;
import org.movier.service.RatingService;
import org.movier.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;

public class RatingServiceImpl implements RatingService{

	@Autowired
	private DAOMongo mongo;
	
	public Rating getRating(String id) {
		
		Map constrains = new HashMap();
		constrains.put("movieId", id);
		List<Rating> ret = mongo.search(null, null, constrains, null, -1, 1, Rating.class);
		if(ret == null || ret.size() <= 0)
		{
			Rating newRating = new Rating();
			newRating.setId(id);
			this.addRating(newRating);		
			return newRating;
		}
		else
		{
			return ret.get(0);
		}		
		//return mock();
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

	public Rating addRating(Rating rating) {
		// TODO Auto-generated method stub
		if(rating!=null)
			this.mongo.insert(rating);
		else 
			System.out.println("addRating: rating==null");
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
