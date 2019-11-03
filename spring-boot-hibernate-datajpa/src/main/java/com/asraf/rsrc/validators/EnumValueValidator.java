package com.asraf.rsrc.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.asraf.rsrc.utils.StringUtils;

public class EnumValueValidator implements ConstraintValidator<EnumValueConstraint, String> {
	private EnumValueConstraint annotation;

	@Override
	public void initialize(EnumValueConstraint annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtils.isNullOrWhitespace(valueForValidation)) {
			return true;
		}

		Object[] enumValues = this.annotation.enumClass().getEnumConstants();
		if (enumValues == null) {
			return false;
		}

		for (Object enumValue : enumValues) {
			if (valueForValidation.equals(enumValue.toString())
					|| (this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString()))) {
				return true;
			}
		}
		return false;
	}

}