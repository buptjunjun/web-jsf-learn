package org.cb.boost;

import org.cb.data.Blog;
import org.cb.data.Bloger;

public class CsdnBoostGeter implements BoostGeter
{
	public CsdnBoostGeter()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * according to the comments , visits and timespan between posttime and crawled time ,caculate the boost value. 
	 * @param blog
	 * @return
	 */
	public float getBoostFromBlog(Blog blog)
	{
		int pictures = blog.getPictures();
		int comments = blog.getComment();
		int visits = blog.getVisit();
		
		float boostPictures = 0.0f;
		float boostComments = 0.0f;
		float boostVisits = 0.0f;
		
		if(pictures>0)   // boost from ammount of pictures
		{
			if (pictures > 20) 
				pictures = 20;
			boostPictures = 0.001f*pictures;
			
		}
		
		if(comments>0)   // boost from ammount of pictures
		{
			if (comments > 20) 
				comments = 20;
			boostComments = 0.01f*comments;
			
		}
		
		if(visits>0)   // boost from ammount of pictures
		{
			if (visits > 5000) 
				visits = 5000;
			boostVisits = 0.00001f*comments;
			
		}
		
		return boostPictures + boostComments + boostVisits;
	}
	
	
	/**
	 * according to the bloger's informations ,caculate the boost value. 
	 * @param blog
	 * @return
	 */
	public float getBoostFromBloger(Bloger bloger)
	{
		int articleAmt = bloger.getArticleAmt();
		int visits = bloger.getVisit();
		float boostVisits = 0.0f;
		float boostArticles = 0.0f; 
		
		if(articleAmt>0)   // boost from ammount of pictures
		{
			if (articleAmt > 500) 
				articleAmt = 500;
			boostArticles = 0.0002f*articleAmt;
			
		}
		
		if(visits>0)   // boost from ammount of pictures
		{
			if (visits > 50000) 
				visits = 50000;
			boostVisits = 0.000001f*visits;		
		}
		
		return boostVisits+boostArticles;
	}
	
}
