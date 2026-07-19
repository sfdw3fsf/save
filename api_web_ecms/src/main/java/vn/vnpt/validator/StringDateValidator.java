package vn.vnpt.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.GenericValidator;

import vn.vnpt.common.Patterns;

public class StringDateValidator implements ConstraintValidator<StringDateTimeConstraint, String>{

	private boolean isShortDate = false;
	
	@Override
	public void initialize(StringDateTimeConstraint constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		this.isShortDate = constraintAnnotation.isShortDate();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null)
			return true;
		return GenericValidator.isDate(value, this.isShortDate ? Patterns.YYYYMMDD : Patterns.YYYYMMDDHHMMSS, true);
	}

}
