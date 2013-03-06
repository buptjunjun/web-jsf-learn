package test.flight;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

public class Flight
{
	private List<FlightLeg> legs = null;
	private BigDecimal totalCost;
	
	public Flight(List<FlightLeg> legs,BigDecimal totalCost)
	{
		Assert.notNull(legs);
		Assert.isTrue(legs.size() >= 1,"Flight must have at least one leg");
		
		this.legs = legs;
		this.totalCost = totalCost;
	}
	
	public List<FlightLeg> getLegs()
	{
		return legs;
	}

	public BigDecimal getTotalCost()
	{
		return totalCost;
	}
	
	public boolean isNonStop()
	{
		return this.legs.size() == 1;
	}
	
	public Airport getDepartFrom()
	{
		return this.legs.get(0).getDepartFrom();
	}
	
	public FlightLeg getFirtLeg()
	{
		return this.legs.get(0);
	}
	
	public FlightLeg getLastLeg()
	{
		return this.legs.get(legs.size() - 1);
	}
	
	public Date getArriveAt()
	{
		return this.getLastLeg().getArriveOn();
		
	}
	
	public long getTotalTravelTime()
	{
		Date start = this.getFirtLeg().getDepartOn();
		Date end = this.getLastLeg().getArriveOn();
		Assert.isTrue(end.compareTo(start) > 0,"Start date must be before end date");
		return (end.getTime() - start.getTime());
		}
	
}
