package com.vti.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.vti.entity.Department.DepartmentType;

@Converter(autoApply = true)
public class DepartmentTypeConverter implements AttributeConverter<DepartmentType, String>{

	@Override
	public String convertToDatabaseColumn(DepartmentType attribute) {
		if (attribute != null) return attribute.getType();
		return null;
	}

	@Override
	public DepartmentType convertToEntityAttribute(String dbData) {
		if (dbData != null) return DepartmentType.toEnum(dbData);
		return null;
	}

}
