package com.yyjj.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeValidator.class)
public @interface ValidRange {

	String[] value() default {};
	
	String message() default "{the value not in range.}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
