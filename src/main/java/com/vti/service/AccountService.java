package com.vti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;
import com.vti.repository.IAccountRepository;
import com.vti.specification.AccountSpecification;

@Service
public class AccountService implements IAccountService{
	@Autowired
	private IAccountRepository acRepository;
	
	@Override
	public List<Account> getListAccount() {
		return acRepository.findAll();
	}

	@Override
	public Account getAccountById(int id) {
		//C1 sử dụng optionl
		//Optional<Account> opAc = acRepository.findById(id);
		//return opAc.get();
		
		//C2 override getById data jpa
		Account ac = acRepository.findById(id);
		return ac;
	}

	@Override
	public Account getAccountByUsername(String username) {
		return acRepository.getAccountByUsername(username);
	}

	@Override
	public Page<Account> getListAccountsPaging(Pageable pageable) {
		return acRepository.findAll(pageable);
	}
	
	@Override
	public Page<Account> getListAccountsPaging(Pageable pageable, String nameSearching, AccountFilterForm acFF) {
		Specification<Account> where = AccountSpecification.buildWhere(nameSearching, acFF);
		return acRepository.findAll(where, pageable);
	}

	@Override
	public void createAccount(Account ac) {
		//Mã hóa password trước khi lưu account
		BCryptPasswordEncoder bpEncoder = new BCryptPasswordEncoder();
		String encryptPassword = bpEncoder.encode(ac.getPassword());
		ac.setPassword(encryptPassword);
		//
		acRepository.save(ac);
	}

	@Override
	public void updateAccount(Account ac) {
		acRepository.save(ac);
	}

	@Override
	public void deleteAccount(int id) {
		acRepository.deleteById(id);
	}

	@Override
	public void deleteAccountMultiple(List<Integer> ids) {
		//acRepository.deleteAllById(ids);//Sử dụng hàm dựng sẵn
		
		acRepository.deleteMultipleAccounts(ids);//Sử dụng hàm custom
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account ac = acRepository.findByUsername(username);
		
		if (ac == null) {
			System.out.println("user not found");
			throw new UsernameNotFoundException(username);
		}
		
		if (ac.getRole() != null) {
			return new User(
				ac.getUsername(),
				ac.getPassword(),
				AuthorityUtils.createAuthorityList(ac.getRole().toString())
			);
		}else {
			return new User(
					ac.getUsername(),
					ac.getPassword(),
					AuthorityUtils.createAuthorityList("EMPLOYEE")
				);
		}
	}

	@Override
	public Account findByUsername(String name) {
		return acRepository.findByUsername(name);
	}

	@Override
	public List<Account> getListAccountsAddDepartment() {
		return acRepository.getListAccountsAddDepartment();
	}

	@Override
	public void changePassword(Account ac) {
		acRepository.save(ac);
	}
}

















