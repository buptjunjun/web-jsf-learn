package org.buptjunjun.annotation.database;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
	Constrains constrains() default @Constrains(unique = true);
}
