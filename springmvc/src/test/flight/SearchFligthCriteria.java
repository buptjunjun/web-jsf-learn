package test.flight;

import java.math.BigDecimal;
import java.util.Date;

public class SearchFligthCriteria
{
	private String departFrom;
	private String arriveAt;
	private Date departOn;
	private Date returnOn;
	
	public String getDepartFrom()
	{
		return departFrom;
	}
	public void setDepartFrom(String departFrom)
	{
		this.departFrom = departFrom;
	}
	public String getArriveAt()
	{
		return arriveAt;
	}
	public void setArriveAt(String arriveAt)
	{
		this.arriveAt = arriveAt;
	}
	public Date getDepartOn()
	{
		return departOn;
	}
	public void setDepartOn(Date departOn)
	{
		this.departOn = departOn;
	}
	public Date getReturnOn()
	{
		return returnOn;
	}
	public void setReturnOn(Date returnOn)
	{
		this.returnOn = returnOn;
	}
}
