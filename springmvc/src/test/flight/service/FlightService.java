package test.flight.service;

import java.util.List;

import test.flight.Flight;
import test.flight.SearchFligtCriteria;
import test.flight.SpecialDeal;

public interface FlightService
{
	List<SpecialDeal> getSpecialDeals();
	List<Flight> findFlight(SearchFligtCriteria search);
}
