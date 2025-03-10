package com.Banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.entity.BankAccount;
import com.Banking.entity.Management;
import com.Banking.exception.MyException;
import com.Banking.helper.ResponseStructure;
import com.Banking.service.ManagementService;

@RestController
@RequestMapping("management")
public class ManagementController 
{
	@Autowired
    ManagementService managementService;
	
	@PostMapping("add")
	public ResponseStructure<Management> save(@RequestBody Management management)
	{
		return managementService.save(management);
	}
	
	@PostMapping("login")
	public ResponseStructure<Management> login(Management management) throws MyException
	{
		return managementService.login(management);
	}
	
	@GetMapping("accounts")
	public ResponseStructure<List<BankAccount>> fetchAllAccounts() throws MyException
	{
		return managementService.fetchAllAccounts();
	}
	
	@PutMapping("accountchange/{acno}")
	public ResponseStructure<BankAccount> changeStatus(@PathVariable long acno)
	{
		return managementService.changeStatus(acno);
	}
}
