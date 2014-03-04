package org.junjun.spring.charpter2.first_competetion;


/**
 * ���������
 * @author junjun
 *
 */
public class Instrumentalist implements Performer 
{
	private String song = null;
	private Instrument instrument = null;
	
	
	public Instrumentalist(){}
	
	/**
	 * �������������
	 */
	public void perform() throws PerformanceException
	{
		System.out.println("Playing a song "+this.song);
		instrument.play();
	}


	
	/**
	 * ��������_ʼ֮ǰ �{ԇ����
	 * �� init-method ���O��
	 */
	public void tuneInstrument()
	{
		System.out.println("tune Instrument");
	}
	
	/**
	 * ������ұ���֮�� ���혷��
	 * ��destroy-method���O��
	 */
	public void cleanInstrument()
	{
		System.out.println("clean Instrument");
	}
	
	public String screamSong()
	{
		return song;
	}
	public String getSong()
	{
		return song;
	}

	public void setSong(String song)
	{
		this.song = song;
	}

	public void setInstrument(Instrument instrument)
	{
		this.instrument = instrument;
	}
}
