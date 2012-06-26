package com.buptjunjun.annotation.apt;

import java.lang.annotation.*;
/**
 * 从一个类中抽取它的接口
 * @author junjun
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtractInterface 
{
	String value()default "";
}
