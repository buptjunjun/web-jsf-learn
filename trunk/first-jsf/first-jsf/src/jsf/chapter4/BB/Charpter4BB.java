package jsf.chapter4.BB;

import java.util.List;

public class Charpter4BB
{
	private List favoriteSites;
	private int times = 3;
	
	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String nextPage()
	{
		return "next";
	}
	
	public String prePage()
	{
		return "pre";
	}
	
	public List getFavoriteSites() {
		return favoriteSites;
	}

	public void setFavoriteSites(List favoriteSites) {
		this.favoriteSites = favoriteSites;
	}
}
