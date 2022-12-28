package com.vti.customvalidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DepartmentNameValidator.class})
public @interface DepartmentNameNotExists {
	String message() default "Department name already exists";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default{};
}
