package org.junjun.spring.charpter2.second_spEL;

/**
 * ����
 * @author andyWebsense
 *
 */
public class Country
{
	// �׶� 
	private City capital = null;
	
	//������
	private City biggestCity = null;
	
	//��С����
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
