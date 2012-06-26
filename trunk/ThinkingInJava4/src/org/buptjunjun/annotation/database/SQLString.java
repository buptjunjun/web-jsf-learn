package org.buptjunjun.annotation.database;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString 
{
	int value() default 0;
	String name() default "";
	Constrains constrains() default @Constrains();
}
