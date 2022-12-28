package com.vti.customvalidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vti.repository.IDepartmentRepository;

public class DepartmentNameValidator implements ConstraintValidator<DepartmentNameNotExists, String>{
	
	@Autowired
	private IDepartmentRepository dpRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) return true;
		
		return !dpRepository.existsByName(value);
	}

}
