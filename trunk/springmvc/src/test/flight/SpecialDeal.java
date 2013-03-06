package test.flight;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.util.Assert;

public class SpecialDeal
{
	private Airport departFrom;
	private Airport arriveAt;
	private BigDecimal cost;
	private Date beginOn;
	private Date endOn;
	
	public SpecialDeal(	  Airport departFrom, Airport arriveAt, BigDecimal cost,  Date beginOn, Date endOn)
	{
		this.departFrom = departFrom;
		this.arriveAt = arriveAt;
		this.cost = cost;
		this.beginOn = beginOn;
		this.endOn = endOn;
	}
	
	
	public boolean isValidNow()
	{
		return this.isValidOn(new Date());
	}
	
	public boolean isValidOn(Date date)
	{
		Assert.notNull(date,"date must be not null");
		Date copy = new Date(date.getTime());
		return (copy.equals(beginOn) || copy.after(beginOn)) && (copy.equals(endOn) || copy.before(endOn)); 
	}
	
	public Airport getDepartFrom()
	{
		return departFrom;
	}

	public Airport getArriveAt()
	{
		return arriveAt;
	}

	public BigDecimal getCost()
	{
		return cost;
	}

	public Date getBeginOn()
	{
		return beginOn;
	}

	public Date getEndOn()
	{
		return endOn;
	}

}
