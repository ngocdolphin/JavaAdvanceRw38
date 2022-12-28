package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vti.entity.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account>{
	Account findById(int id);
	
	@Query("SELECT ac FROM Account ac WHERE ac.username =:usernmameParameter")
	Account getAccountByUsername(@Param("usernmameParameter") String username);
	
	@Modifying
	@Transactional
	@Query("DELETE Account ac WHERE ac.id IN(:ids)")
	public void deleteMultipleAccounts(List<Integer> ids);

	Account findByUsername(String username);
	
	@Modifying
	@Query("SELECT ac FROM Account ac WHERE ac.department IS NULL")
	List<Account> getListAccountsAddDepartment();
}
