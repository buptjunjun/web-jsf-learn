package test.flight;

import java.math.BigDecimal;
import java.util.Date;

public class FlightLeg
{
	
	private Airport departFrom;
	private Airport arriveAt;
	private Date departOn;
	private Date arriveOn;
	public FlightLeg(Airport departFrom,Airport arriveA,Date departOn,Date arriveOn)
	{
		this.departFrom=departFrom;
		this.arriveAt=arriveAt;
		this.departOn=departOn;
		this.arriveOn=arriveOn;
	}
	
	public Airport getDepartFrom()
	{
		return departFrom;
	}
	public void setDepartFrom(Airport departFrom)
	{
		this.departFrom = departFrom;
	}
	public Airport getArriveAt()
	{
		return arriveAt;
	}
	public void setArriveAt(Airport arriveAt)
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
	public Date getArriveOn()
	{
		return arriveOn;
	}
	public void setArriveOn(Date arriveOn)
	{
		this.arriveOn = arriveOn;
	}
}
