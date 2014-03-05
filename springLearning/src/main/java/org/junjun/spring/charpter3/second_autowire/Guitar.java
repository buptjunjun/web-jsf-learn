package org.junjun.spring.charpter3.second_autowire;

import org.springframework.stereotype.Component;

/**
 * ¼ªËû
 * @author andyWebsense
 *
 */
//idÄ¬ÈÏÊÇguitar
@Component
public class Guitar implements Instrument
{

	public void play()
	{
		System.out.println(" da da da");
	}

}
