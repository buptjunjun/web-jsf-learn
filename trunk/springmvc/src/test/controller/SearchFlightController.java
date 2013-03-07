package test.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import test.flight.SearchFligtCriteria;
import test.flight.service.FlightService;

public class SearchFlightController extends SimpleFormController
{
	private FlightService flights;


	public SearchFlightController()
	{
		// commond bean to hold the form submitted
		this.setCommandName("searchFligtCriteria");
		this.setCommandClass(SearchFligtCriteria.class);
		// if error goto fromview
		this.setFormView("searchBegin");
		// if successed goto success view
		this.setSuccessView("listFlights");		
	}
	
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception
	{
		//ע���Զ�������Ա༭��  
	    //1������  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    //��ʾ������������Date���͵����ԣ���ʹ�ø����Ա༭����������ת��  
	    binder.registerCustomEditor(Date.class, dateEditor);      
	}
	
	
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception
	{
		SearchFligtCriteria search = (SearchFligtCriteria)command;
		ModelAndView mav = new ModelAndView();
		mav.setViewName(this.getSuccessView());
		mav.addObject("flights", this.flights.findFlight(search));
		mav.addObject("searchFligtCriteria", search);
		return mav;
	}
	
	public void setFlights(FlightService flights)
	{
		this.flights = flights;
	}
	
}
