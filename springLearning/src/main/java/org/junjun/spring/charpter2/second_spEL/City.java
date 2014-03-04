package org.junjun.spring.charpter2.second_spEL;

/**
 * ³ÇÊÐ
 * @author andyWebsense
 *
 */
public class City
{
	private String name;
	private String state;
	private int population;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public int getPopulation()
	{
		return population;
	}

	public void setPopulation(int population)
	{
		this.population = population;
	}

	@Override
	public String toString()
	{
		return this.name +","+this.state +","+this.population+"  ";
	}
}
