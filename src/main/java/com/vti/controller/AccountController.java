package com.vti.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDTO;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.AccountFilterForm;
import com.vti.form.AccountForm;
import com.vti.form.AccountSigninForm;
import com.vti.repository.IAccountRepository;
import com.vti.service.IAccountService;

@RestController
@RequestMapping(value = "api/accounts")
@Validated
public class AccountController {
	
	@Autowired
	private IAccountService acService;
	
	@Autowired
	private IAccountRepository acRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<AccountDTO> getListAccounts() {
		
		List<Account> accounts = acService.getListAccount();
		
		List<AccountDTO> listAcDTO = modelMapper.map(
			accounts,  
			new TypeToken <List<AccountDTO>> () {}.getType()
		);
		
		return listAcDTO;
	}
	
	@GetMapping("/departmentId-null")
	public List<AccountDTO> getListAccountsAddDepartment() {
		List<Account> listAccount = acService.getListAccountsAddDepartment();
		
		List<AccountDTO> listAccountDTO = modelMapper.map(listAccount, new TypeToken< List<AccountDTO> >(){}.getType());
		
		return listAccountDTO;
	}
	
	@PostMapping
	public ResponseEntity<?> createAccount(@RequestBody AccountForm acForm) {
		System.out.println("AccountFrom received from client: ");
		System.out.println(acForm);
		
		//Using typemap of the modelMapper to skip property
			// code here...
		//
		Account ac = modelMapper.map(acForm, Account.class);
		
		if (ac.getDepartment().getId() == 0) ac.setDepartment(null);
		
		System.out.println(ac);
		
		//Mã hóa password trước khi lưu account
		BCryptPasswordEncoder bpEncoder = new BCryptPasswordEncoder();
		String encryptPassword = bpEncoder.encode(ac.getPassword());
		ac.setPassword(encryptPassword);
		//
		acService.createAccount(ac);
		
		return ResponseEntity.status(HttpStatus.OK).body(ac);
	}
	
	@PutMapping("/{id}")//Chú ý chỉ udpate những trường cần thiết và giữ nguyên những trường còn lại
	public ResponseEntity<?> updateAccount(@PathVariable(name = "id") int id, @RequestBody AccountForm acForm) {
		System.out.println("accountId received from client: ");
		System.out.println(id);
		
		System.out.println("AccountFrom received from client: ");
		System.out.println(acForm);
		
		Account acTemp = modelMapper.map(acForm, Account.class);
		System.out.println("Account after mapper: ");
		System.out.println(acTemp);
		
		Account ac = acService.getAccountById(id);
		
		ac.setUsername(acTemp.getUsername());
		ac.setFirstName(acTemp.getFirstName());
		ac.setLastName(acTemp.getLastName());
		ac.setRole(acTemp.getRole());
		ac.setDepartment(acTemp.getDepartment());
		
		acService.updateAccount(ac);
	
		return ResponseEntity.status(HttpStatus.OK).body(ac);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAccount(@PathVariable(name = "id") int id) {
		System.out.println("accountId received from request delete by client: ");
		System.out.println(id);
		
		if (acRepository.existsById(id) == false) {
			return ResponseEntity.status(HttpStatus.OK).body("Account does not exists.");
		}
		
		acService.deleteAccount(id);
		
		JSONObject message = new JSONObject();
		message.put("message", "Account has deleted successfully.");
		message.put("status", HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAccountMultiple(@RequestBody List<Integer> ids) {
		System.out.println("Array accountIds received from request delete by client: ");
		System.out.println(ids);
		
		acService.deleteAccountMultiple(ids);
		
		JSONObject message = new JSONObject();
		message.put("message", "Accounts has deleted successfully.");
		message.put("status", HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@GetMapping("/paging")
//	public Page<AccountDTO> getListAccountsPaging(Pageable pageable) {
//		
//		Page<Account> pageAccount = acService.getListAccountsPaging(pageable);
//		
//		List<AccountDTO> listAcDTO = modelMapper.map(
//			pageAccount.getContent(),  
//			new TypeToken <List<AccountDTO>> () {}.getType()
//		);
//		Page<AccountDTO> pageAcDTO = new PageImpl(listAcDTO, pageable, pageAccount.getTotalElements());
//		
//		return pageAcDTO;
//	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/paging")
	public Page<AccountDTO> getListAccountsPaging(
			Pageable pageable,
			@RequestParam(value = "search", required = false) String nameSearching,
			AccountFilterForm acFF
	) {
		
//		System.out.println("Search name: " + nameSearching);
//		
//		System.out.println("account filter form: ");
//		System.out.println(acFF);
		
		Page<Account> pageAccount = acService.getListAccountsPaging(pageable, nameSearching, acFF);
		
		List<AccountDTO> listAcDTO = modelMapper.map(
			pageAccount.getContent(),  
			new TypeToken <List<AccountDTO>> () {}.getType()
		);
		Page<AccountDTO> pageAcDTO = new PageImpl(listAcDTO, pageable, pageAccount.getTotalElements());
		
		return pageAcDTO;
		
		//return null;
	}
	
	
	@GetMapping("/{id}")
	public AccountDTO getAccountById(@PathVariable(name = "id") int id) {
		Account ac = acService.getAccountById(id);
		AccountDTO acDTO = modelMapper.map(ac, AccountDTO.class);
		return acDTO;
	}
	
	@GetMapping("/searching")
	public ResponseEntity<?> getAccountByUsername(@RequestParam(value = "username") String username) {
		
		System.out.println("Rearching username: " + username);
		
		Account ac = acService.getAccountByUsername(username);
		
		System.out.println("Result get account by username: ");
		System.out.println(ac);//Entity account must override method toString()
		System.out.println("-----------------------------------");
		
		if (ac == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found");
		
		System.out.println("Mapp account DTO by username: ");
		
		AccountDTO acDTO = modelMapper.map(ac, AccountDTO.class);
		
		System.out.println(acDTO);
		System.out.println("-----------------------------------");
		
		JSONObject message = new JSONObject();
		message.put("ResultText", "Account found");
		message.put("status", HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
	
//	@GetMapping("/searching")
//	public AccountDTO getAccountByUsername(@RequestParam(value = "username") String username) {
//		System.out.println("Rearching username: " + username);
//		Account ac = acService.getAccountByUsername(username);
//		System.out.println("Result get account by username: ");
//		System.out.println(ac.getUsername());
//		System.out.println("-----------------------------------");
//		AccountDTO acDTO = modelMapper.map(ac, AccountDTO.class);
//		return acDTO;
//	}
	
	@PostMapping("/changing-password")
	public ResponseEntity<?> changePassword(@RequestBody @Valid AccountSigninForm acSgnForm) {
		System.out.println("Username, password received from client: ");
		System.out.println(acSgnForm);
		
		Account ac = acRepository.findByUsername(acSgnForm.getUsername());
		
		if (ac == null) throw new UsernameNotFoundException(acSgnForm.getUsername());
		
		BCryptPasswordEncoder bpEncoder = new BCryptPasswordEncoder();
		String encryptPassword = bpEncoder.encode(acSgnForm.getPassword());
		ac.setPassword(encryptPassword);
		//
		acService.changePassword(ac);
		
		JSONObject message = new JSONObject();
		message.put("message", "Account's password has just changed successfully");
		message.put("status", HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(message.toString());
	}
	
}
