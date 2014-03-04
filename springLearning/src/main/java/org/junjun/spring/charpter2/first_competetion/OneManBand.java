package org.junjun.spring.charpter2.first_competetion;

import java.util.Collection;

/**
 * һ���˵��ֶ� ����ע��һ��list
 * @author andyWebsense
 *
 */
public class OneManBand implements Performer
{
	/**
	 * ����
	 */
	private Collection<Instrument>  instruments = null;
	
	public OneManBand()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ʹ��setterע��
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
