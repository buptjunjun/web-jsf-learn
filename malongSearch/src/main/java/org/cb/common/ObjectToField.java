package org.cb.common;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectToField {
	public String type() ;
	public String fieldName();
	public boolean store();
	public boolean analyzed();
}
