package com.vti.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;

public class DepartmentSpecification {
	
	@SuppressWarnings({ "deprecation", "unused" })
	public static Specification<Department> buildWhere(String nameSearching, DepartmentFilterForm dpFF) {
		Specification<Department> where = null;
		
		if (!StringUtils.isEmpty(nameSearching)) {
			nameSearching = nameSearching.trim();
			CustomDepartmentSpecification searchByUsername = new CustomDepartmentSpecification("nameSearching", nameSearching);
			where = Specification.where(searchByUsername);
		}
		
		if (dpFF != null && !StringUtils.isEmpty(dpFF.getType())) {
			CustomDepartmentSpecification filterByType = new CustomDepartmentSpecification("type", dpFF.getType());
			if (where == null) where = filterByType;
			else where = where.and(filterByType);
		}
		
		if (dpFF != null && dpFF.getMinDate() != null) {
			CustomDepartmentSpecification minDate = new CustomDepartmentSpecification("minDate", dpFF.getMinDate());
			if (where == null) where = minDate;
			else where = where.and(minDate);
		}
		
		if (dpFF != null && dpFF.getMinDate() != null) {
			CustomDepartmentSpecification maxDate = new CustomDepartmentSpecification("maxDate", dpFF.getMaxDate());
			if (where == null) where = maxDate;
			else where = where.and(maxDate);
		}
		
		return where;
	}
	
}
