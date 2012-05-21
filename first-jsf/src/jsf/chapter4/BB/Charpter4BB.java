package jsf.chapter4.BB;

import java.util.List;

public class Charpter4BB
{
	private List favoriteSites;

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
