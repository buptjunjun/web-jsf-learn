import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @description:
 * build a search tree for every hotel like bellow:
 * 				Lakewood
 *				/       \ 
 * 	      regular        rewards
 *          /    \       /     \
 *     weekday weekend  weekday weekend
 *       |       |       |        |
 *       110    90      80       80 
 *       
 * when a customer is reserating a hotel ,just search the tree and got the total cost.
 * @author junjun
 *
 */

public class HotelReservation {
	
	public HotelReservation() {
		this.createTree();
	}
	
	/**
	 * create a tree like  
	 *  			Lakewood
	 *				/       \ 
	 * 	      regular        rewards
	 *          /    \       /     \
	 *     weekday weekend  weekday weekend
	 *       |       |         |       |
	 *       110    90        80       80 
	 * @param nh
	 * @param nodeCustomerType
	 * @param nodeDayType
	 * @param cost
	 * @return
	 */
	private NodeHotel createTree(NodeHotel nh,String nodeCustomerType, String nodeDayType,int cost)
	{
		NodeCustomerType nct = null;
		
		//reguar
		if(nodeCustomerType.contains("regular"))
		{
			if(nh.getChild_regular() == null)
				nh.setChild_regular(new NodeCustomerType());
			
			nct = nh.getChild_regular();
		}
		else //rewards
		{
			if(nh.getChild_rewards() == null)
				nh.setChild_rewards(new NodeCustomerType());
			 nct = nh.getChild_rewards();
		}
		
		//weekday
		if(nodeDayType.contains("weekday"))
		{
			if(nct.getChild_weekday() == null)
			{
				NodeDayType ndt = new NodeDayType();
				ndt.setCost(cost);
				nct.setChild_weekday(ndt);
			}
		}
		else //weekend
		{
			if(nct.getChild_weekend() == null)
			{
				NodeDayType ndt = new NodeDayType();
				ndt.setCost(cost);
				nct.setChild_weekend(ndt);
			}
		}
		return nh;
	}

	
	/**
	 * get cost from hotle, customer type, and daytype
	 * @param nh
	 * @param nodeCustomerType
	 * @param nodeDayType
	 */
	private int searchCost(NodeHotel nh,String nodeCustomerType, String nodeDayType)
	{
		NodeCustomerType nct = null;
		if(nodeCustomerType.contains("regular"))
			nct = nh.getChild_regular();
		else
			nct = nh.getChild_rewards();
		
		NodeDayType ndt = null;
		if(nodeDayType.contains("weekend"))
			ndt = nct.getChild_weekend();
		else 
			ndt = nct.getChild_weekday();
		
		return ndt.getCost();
		
	}
	
	
	// array storing tree of  Lakewood Bridgewood Ridgewood 
	private NodeHotel [] hotels = new NodeHotel[3];
	
	// file contains datas
	String filename = "data.txt";
	private void createTree()
	{
		// init array of hotels
		for(int i = 0; i < hotels.length;i++)
			hotels[i] = new NodeHotel();
		
		// read data from a file and create tree
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
			 String line = null;
			 int linecount = 0;
			 while((line = reader.readLine())!=null)
			 {
				 //System.out.println(linecount+":"+line);
				 int hotelindex = linecount / 4;
				 NodeHotel hotel = hotels[hotelindex];
				 String [] infors = line.split(",");
				 
				 // create the tree;
				 this.createTree(hotel, infors[1], infors[2], Integer.parseInt(infors[3]));
				 linecount++;
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(reader != null)
				{
					reader.close();
					reader = null;
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * input :Regular: 16Mar2009(mon), 17Mar2009(tues), 18Mar2009(wed)
	 * ouput :Lakewood
	 * @param line
	 * @return hotel name
	 */
	public String findHotel(String line)
	{
		String [] parts1 = line.split(":");
		String [] parts2 = parts1[1].split(",");
		
		String customerType = parts1[0].trim().toLowerCase();
		String [] dayType = new String[parts2.length];
		for(int i = 0;i < dayType.length;i++)
		{
			if(parts2[i].contains("sat") || parts2[i].contains("fri")) //weekend
			{
				dayType[i] = "weekend";
			}
			else
			{
				dayType[i] = "weekday";
			}
		}
		
		int hotelNum = 0;
		int lowestCost = Integer.MAX_VALUE;
		for(int i = 0;i < hotels.length;i++)
		{
			// caculate the total cost of certain Hotel
			int currentCost = 0;
			NodeHotel nh = hotels[i];
			for(int j = 0;j < dayType.length;j++)
			{
				currentCost += this.searchCost(nh, customerType, dayType[j]);
			}
			
			// save the Hotel who costs least
			if(currentCost < lowestCost)
			{
				lowestCost = currentCost;
				hotelNum = i;
			}
		}
		
		// get hotelName from hotelNum
		String hotelName = null;
		switch(hotelNum)
		{
			case 0:
				hotelName = "Lakewood ";break;
			case 1:
				hotelName = "Bridgewood";break;
			case 2:
				hotelName = "Ridgewood";break;
			default:
				hotelName = "Lakewood ";
		}
		
		return hotelName;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String [] inputs = {"Regular: 16Mar2009(mon), 17Mar2009(tues), 18Mar2009(wed)",
		                    "Regular: 20Mar2009(fri), 21Mar2009(sat), 22Mar2009(sun)",
		                    "Rewards: 26Mar2009(thur), 27Mar2009(fri), 28Mar2009(sat)"
							};
		HotelReservation hr = new HotelReservation();
		
		int i = 1;
		for(String input:inputs)
		{
			System.out.println("INPUT "+i+":");
			System.out.println(input);
			System.out.println("OUTPUT "+i+":");
			System.out.println(hr.findHotel(input));	
			i++;
		}
	}

	
}
	

/**
 * hotel name
 * @author junjun
 *
 */
class NodeHotel
{
	private NodeCustomerType child_regular = null;
	private NodeCustomerType child_rewards = null;
	
	public NodeCustomerType getChild_regular() {
		return child_regular;
	}
	public void setChild_regular(NodeCustomerType child_regular) {
		this.child_regular = child_regular;
	}
	public NodeCustomerType getChild_rewards() {
		return child_rewards;
	}
	public void setChild_rewards(NodeCustomerType child_rewards) {
		this.child_rewards = child_rewards;
	}	
}

/**
 * regular or rewards
 * @author junjun
 *
 */
class NodeCustomerType
{
	private NodeDayType child_weekday = null;
	private NodeDayType child_weekend = null;
	public NodeDayType getChild_weekday() {
		return child_weekday;
	}
	public void setChild_weekday(NodeDayType child_weekday) {
		this.child_weekday = child_weekday;
	}
	public NodeDayType getChild_weekend() {
		return child_weekend;
	}
	public void setChild_weekend(NodeDayType child_weekend) {
		this.child_weekend = child_weekend;
	}
	

	
}

/**
 * weekday or weekend
 * @author junjun
 *
 */
class NodeDayType
{
	private int cost = 0;

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
}

