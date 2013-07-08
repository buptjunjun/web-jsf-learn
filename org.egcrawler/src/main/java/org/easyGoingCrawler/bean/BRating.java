package org.easyGoingCrawler.bean;

public class BRating
{

	private int min = 0;
	private int max = 0;
	private float average = 0.0f;
	private int stars = 0;
	
	public int getMin()
	{
		return min;
	}
	public void setMin(int min)
	{
		this.min = min;
	}
	public int getMax()
	{
		return max;
	}
	public void setMax(int max)
	{
		this.max = max;
	}
	public float getAverage()
	{
		return average;
	}
	public void setAverage(float average)
	{
		this.average = average;
	}
	public int getStars()
	{
		return stars;
	}
	public void setStars(int stars)
	{
		this.stars = stars;
	}
}
