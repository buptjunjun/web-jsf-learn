package org.movier.service;

import org.movier.bean.Movie;
import org.movier.bean.Rating;

public interface RatingService 
{
	public Rating getRating(String id);
	public String updateRating(Rating Rating);
	public Rating delRating(String id);
	public Rating addRating(Rating Rating);
}
