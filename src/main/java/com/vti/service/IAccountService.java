package com.vti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;

public interface IAccountService extends UserDetailsService{

	List<Account> getListAccount();

	Account getAccountById(int id);

	Account getAccountByUsername(String username);

	Page<Account> getListAccountsPaging(Pageable pageable);

	Page<Account> getListAccountsPaging(Pageable pageable, String nameSearching, AccountFilterForm acFF);

	void createAccount(Account ac);

	void updateAccount(Account ac);

	void deleteAccount(int id);

	void deleteAccountMultiple(List<Integer> ids);

	Account findByUsername(String name);

	List<Account> getListAccountsAddDepartment();

	void changePassword(Account ac);

}
