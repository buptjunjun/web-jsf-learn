package com.buptjunjun.annotation.apt;

import java.lang.annotation.*;
/**
 * ��һ�����г�ȡ���Ľӿ�
 * @author junjun
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtractInterface 
{
	String value()default "";
}
