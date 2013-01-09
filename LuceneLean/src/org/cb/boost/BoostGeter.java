package org.cb.boost;

import org.cb.data.Blog;
import org.cb.data.Bloger;

public interface BoostGeter
{
	
	/**
	 * according to the comments , visits and timespan between posttime and crawled time ,caculate the boost value. 
	 * @param blog
	 * @return
	 */
	public float getBoostFromBlog(Blog blog);
	
	/**
	 * according to the bloger's informations ,caculate the boost value. 
	 * @param blog
	 * @return
	 */
	public float getBoostFromBloger(Bloger bloger);
	
	
}

