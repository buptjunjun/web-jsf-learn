package org.buptjunjun.annotation.database;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constrains {
	boolean primaryKey() default false;
	boolean allowNull() default true;
	boolean unique() default false;
}
