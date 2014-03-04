package org.junjun.spring.charpter2.first_competetion;

import java.util.Collection;

/**
 * 一个人的乐队 可以注入一个list
 * @author andyWebsense
 *
 */
public class OneManBand implements Performer
{
	/**
	 * 乐器
	 */
	private Collection<Instrument>  instruments = null;
	
	public OneManBand()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 使用setter注入
	 * @param instruments
	 */
	public void setInstruments(Collection<Instrument> instruments)
	{
		this.instruments = instruments;
	}
	
	public void perform() throws PerformanceException
	{
		for(Instrument instrument : instruments)
		{
			instrument.play();
		}
		
	}

}
