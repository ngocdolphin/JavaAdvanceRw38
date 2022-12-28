package com.vti.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;

public class AccountSpecification {
	
	@SuppressWarnings({ "deprecation", "unused" })
	public static Specification<Account> buildWhere(String nameSearching, AccountFilterForm acFF) {
		Specification<Account> where = null;
		
		if (!StringUtils.isEmpty(nameSearching)) {
			nameSearching = nameSearching.trim();
			CustomAccountSpecification searchByUsername = new CustomAccountSpecification("username", nameSearching);
			CustomAccountSpecification searchByfirstName = new CustomAccountSpecification("firstName", nameSearching);
			CustomAccountSpecification searchBylastName = new CustomAccountSpecification("lastName", nameSearching);
			where = Specification.where(searchByfirstName).or(searchByfirstName).or(searchBylastName);
		}
		
		if (acFF != null && !StringUtils.isEmpty(acFF.getRole())) {
			System.out.println("Filter by role");
			System.out.println(acFF);
			CustomAccountSpecification filterByRole = new CustomAccountSpecification("role", acFF.getRole());
			if (where == null) where = filterByRole;
			else where = where.and(filterByRole);
		}
		
		if (acFF != null && acFF.getDepartmentId() != 0) {
			CustomAccountSpecification filterByDepartment = new CustomAccountSpecification("departmentId", acFF.getDepartmentId());
			if (where == null) where = filterByDepartment;
			else where = where.and(filterByDepartment);
		}
		
		return where;
	}
	
}
