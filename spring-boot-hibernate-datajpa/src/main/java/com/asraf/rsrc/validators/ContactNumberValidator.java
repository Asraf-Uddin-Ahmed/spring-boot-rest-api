package com.asraf.rsrc.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.asraf.rsrc.utils.StringUtils;

public class ContactNumberValidator implements ConstraintValidator<ContactNumberConstraint, String> {

	@Override
	public void initialize(ContactNumberConstraint contactNumber) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return StringUtils.isNullOrEmpty(contactField)
				|| (contactField.matches("[0-9]+") && (contactField.length() > 10) && (contactField.length() < 14));
	}

}