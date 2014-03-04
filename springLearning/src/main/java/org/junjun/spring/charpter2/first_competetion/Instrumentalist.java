package org.junjun.spring.charpter2.first_competetion;


/**
 * 乐器演奏家
 * @author junjun
 *
 */
public class Instrumentalist implements Performer 
{
	private String song = null;
	private Instrument instrument = null;
	
	
	public Instrumentalist(){}
	
	/**
	 * 唱歌和演奏樂器
	 */
	public void perform() throws PerformanceException
	{
		System.out.println("Playing a song "+this.song);
		instrument.play();
	}


	
	/**
	 * 在演奏家開始之前 調試樂器
	 * 在 init-method 中設置
	 */
	public void tuneInstrument()
	{
		System.out.println("tune Instrument");
	}
	
	/**
	 * 在演奏家表演之后 清理樂器
	 * 在destroy-method中設置
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
