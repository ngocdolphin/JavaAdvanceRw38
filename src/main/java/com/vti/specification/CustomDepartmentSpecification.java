package com.vti.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vti.entity.Department;
import com.vti.entity.Department.DepartmentType;

import lombok.Data;
import lombok.NonNull;

@SuppressWarnings("serial")
@Data
public class CustomDepartmentSpecification implements Specification<Department>{
	@NonNull
	private String field;
	
	@NonNull
	private Object value;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (field.equalsIgnoreCase("nameSearching")) {
			
			return criteriaBuilder.like(root.get("name"), "%" + value + "%");
			
		}else if (field.equalsIgnoreCase("type")) {
			
			return criteriaBuilder.equal(root.get("type"), DepartmentType.toEnum2(value.toString()));
			
		}else if (field.equalsIgnoreCase("minDate")) {
			
			Expression ex = root.<Date>get("createdAt");
			return criteriaBuilder.greaterThanOrEqualTo(ex, (Date)value);
			
		}else if (field.equalsIgnoreCase("maxDate")) {
			Expression ex = root.<Date>get("createdAt");
			return criteriaBuilder.lessThanOrEqualTo(ex, (Date)value);
		}
		return null;
	}
}






