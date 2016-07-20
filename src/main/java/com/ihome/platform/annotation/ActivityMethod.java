package com.ihome.platform.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.ihome.platform.enums.MethodType;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActivityMethod {

	String value();

	MethodType type() default MethodType.LOCAL;

	String description() default "";
}
