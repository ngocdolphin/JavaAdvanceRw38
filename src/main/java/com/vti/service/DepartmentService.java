package com.vti.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vti.dto.AccountDTO;
import com.vti.dto.DepartmentDTO;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.AccountFilterForm;
import com.vti.form.DepartmentFilterForm;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.AccountSpecification;
import com.vti.specification.DepartmentSpecification;

@Service
public class DepartmentService implements IDepartmentService{
	@Autowired
	private IDepartmentRepository dpRepository;
	
	@Autowired
	private IAccountRepository acRepository;
	
	@Override
	public List<Department> getListDepartments() {
		return dpRepository.findAll();
	}

	@Override
	@Transactional
	public void createDepartment(Department dp) {
		Department deparment = dpRepository.save(dp);
		
		System.out.println(deparment);
		
		List<Account> accounts = new ArrayList<>();
		
		for (Account ac : deparment.getAccounts()) {
			
			Account acTemp = acRepository.findById(ac.getId());
			
			accounts.add(acTemp);
		}
		
		for (Account ac : accounts) {
			
			ac.setDepartment(deparment);
		}
		
		acRepository.saveAll(accounts);
	}

	@Override
	public void deleteDepartment(int id) {
		dpRepository.deleteById(id);
	}

	@Override
	public void deleteMultipleDepartments(List<Integer> ids) {
		dpRepository.deleteAllById(ids);
	}
	
	@Override
	public Page<Department> getListDepartmentPaging(Pageable pageable, String nameSearching,
			DepartmentFilterForm dpFF) {
		Specification<Department> where = DepartmentSpecification.buildWhere(nameSearching, dpFF);
		return dpRepository.findAll(where, pageable);
	}

	@Override
	public void updateDepartment(Department dp) {
		dpRepository.save(dp);
	}
	
}
