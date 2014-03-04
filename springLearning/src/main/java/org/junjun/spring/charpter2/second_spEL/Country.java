package org.junjun.spring.charpter2.second_spEL;

/**
 * 国家
 * @author andyWebsense
 *
 */
public class Country
{
	// 首都 
	private City capital = null;
	
	//最大城市
	private City biggestCity = null;
	
	//最小城市
	private City smallestCity = null;

	
	public City getSmallestCity()
	{
		return smallestCity;
	}

	public void setSmallestCity(City smallestCity)
	{
		this.smallestCity = smallestCity;
	}

	public City getCapital()
	{
		return capital;
	}

	public void setCapital(City capital)
	{
		this.capital = capital;
	}

	public City getBiggestCity()
	{
		return biggestCity;
	}

	public void setBiggestCity(City biggestCity)
	{
		this.biggestCity = biggestCity;
	}
	
	@Override
	public String toString()
	{
		return this.capital +"  "+this.biggestCity +" "+this.smallestCity;
	}
}
