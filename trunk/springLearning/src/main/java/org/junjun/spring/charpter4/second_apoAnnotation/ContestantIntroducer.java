package org.junjun.spring.charpter4.second_apoAnnotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ContestantIntroducer
{
	/**
	 * value �� types-matching��Ӧ
	 * defaultImpl��default-impl��Ӧ
	 * 
	 */
	@DeclareParents(value="org.junjun.spring.charpter2.first_competetion.Performer+",defaultImpl=GraciousContestant.class)
	public static Contestant contestant;
}
