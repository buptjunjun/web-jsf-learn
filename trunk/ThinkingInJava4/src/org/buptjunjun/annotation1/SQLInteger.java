package org.buptjunjun.annotation.database;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger 
{
	String name() default "";
	Constrains constrains() default @Constrains(unique=false);
}
