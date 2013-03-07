package test.flight.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import test.flight.Airport;
import test.flight.Flight;
import test.flight.FlightLeg;
import test.flight.SearchFligtCriteria;
import test.flight.SpecialDeal;
import test.flight.service.FlightService;

public class DumyFlightService implements FlightService
{
	public List<SpecialDeal> getSpecialDeals()
	{
		List<SpecialDeal> ret = new ArrayList<SpecialDeal>();
		for(int i = 0; i< 5; i++)
		{
			Airport start = new Airport("beijing","111");
			Airport arrive = new Airport("tianjing","222");
			Date begin = new Date(2013,3,5);
			Date end = new Date(2013,3,5);
			SpecialDeal s = new SpecialDeal(start,arrive,new BigDecimal(100),begin,end);
			ret.add(s);
		}
		
		return ret;
	}
	
	@Override
	public List<Flight> findFlight(SearchFligtCriteria search)
	{
		Airport start = new Airport("beijing","111");
		Airport arrive = new Airport("tianjing","222");
		Date begin = new Date(2013,3,5);
		Date end = new Date(2013,3,5);
		
		// TODO Auto-generated method stub
		FlightLeg fl = new FlightLeg(start,arrive,begin,end);
		List<FlightLeg> fls = new ArrayList<FlightLeg>();
		fls.add(fl);
		Flight f = new Flight(fls,new BigDecimal(100));
		List<Flight> fs = new ArrayList<Flight>();
		fs.add(f);
		return fs;
	}
}
