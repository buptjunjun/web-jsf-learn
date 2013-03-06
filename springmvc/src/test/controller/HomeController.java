package test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import test.flight.service.FlightService;

public class HomeController extends AbstractController
{
	private FlightService flights;
	private static int FIVE_MINUTES = 5*60;
	public HomeController()
	{
		// set the support method
		this.setSupportedMethods(new String [] {this.METHOD_GET});
		this.setCacheSeconds(this.FIVE_MINUTES);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		mav.addObject("specials", flights.getSpecialDeals());
		return mav;
	}
	

	public void setFlights(FlightService flights)
	{
		this.flights = flights;
	}
}
