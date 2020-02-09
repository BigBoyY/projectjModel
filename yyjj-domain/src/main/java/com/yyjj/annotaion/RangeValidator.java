package com.yyjj.annotaion;

import java.util.Arrays;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeValidator implements ConstraintValidator<ValidRange, String> {
	
	private ValidRange rangeValid;
	
	@Override
    public void initialize(ValidRange constraintAnnotation) {
		this.rangeValid = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String[] values = rangeValid.value();
		if(Objects.isNull(value) || "".equals(value)) {
			return true;
		}
		
		if(Arrays.asList(values).contains(value)) {
			return true;
		}
		
		validMessage("值: " + value + ", 不在范围内.", context);
		return false;
	}
	
	private void validMessage(String message, ConstraintValidatorContext cvc) {
        cvc.disableDefaultConstraintViolation();
        cvc.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

}
