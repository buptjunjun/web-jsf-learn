package org.junjun.spring.charpter4.second_apoAnnotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ContestantIntroducer
{
	/**
	 * value 与 types-matching对应
	 * defaultImpl与default-impl对应
	 * 
	 */
	@DeclareParents(value="org.junjun.spring.charpter2.first_competetion.Performer+",defaultImpl=GraciousContestant.class)
	public static Contestant contestant;
}
