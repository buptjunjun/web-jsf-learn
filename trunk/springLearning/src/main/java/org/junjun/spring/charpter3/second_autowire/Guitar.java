package org.junjun.spring.charpter3.second_autowire;

import org.springframework.stereotype.Component;

/**
 * ����
 * @author andyWebsense
 *
 */
//idĬ����guitar
@Component
public class Guitar implements Instrument
{

	public void play()
	{
		System.out.println(" da da da");
	}

}
