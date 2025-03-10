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
import com.Banking.entity.BankTransaction;
import com.Banking.entity.Customer;
import com.Banking.entity.Login;
import com.Banking.exception.MyException;
import com.Banking.helper.ResponseStructure;
import com.Banking.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController 
{
	@Autowired
    CustomerService customerService;
	
	@PostMapping("add")
	public ResponseStructure<Customer> save(@RequestBody Customer customer) throws MyException
	{
		return customerService.save(customer);
	}
	
	@PutMapping("otp/{custid}/{otp}")
	public ResponseStructure<Customer> otpVerify(@PathVariable int custid,@PathVariable int otp) throws MyException
	{
		return customerService.verify(custid, otp);
	}
	
	@PostMapping("login")
	public ResponseStructure<Customer> login(@RequestBody Login login) throws MyException
	{
		return customerService.login(login);
	}
	
	@PostMapping("account/{cust_id}/{type}")
	public ResponseStructure<Customer> createAccount(@PathVariable int cust_id,@PathVariable String type) throws MyException
	{
		return customerService.createAccount(cust_id,type);
	}
	
	@GetMapping("accounts/{custid}")
	public ResponseStructure<List<BankAccount>> fetchAllTrue(@PathVariable int custid) throws MyException
	{
		return customerService.fetchAllTrue(custid);
	}
	
	@GetMapping("account/check/{acno}")
	public ResponseStructure<Double> checkBalance(@PathVariable long acno)
	{
		return customerService.checkBalance(acno);
	}
	
	@PutMapping("account/deposit/{acno}/{amount}")
	public ResponseStructure<BankAccount> deposit(@PathVariable long acno,@PathVariable double amount)
	{
		return customerService.deposit(acno,amount);
	}
	
	@PutMapping("account/withdraw/{acno}/{amount}")
	public ResponseStructure<BankAccount> withdraw(@PathVariable long acno,@PathVariable double amount) throws MyException
	{
		return customerService.withdraw(acno,amount);
	}
	
	@GetMapping("account/viewtransaction/{acno}")
	public ResponseStructure<List<BankTransaction>> viewTransaction(@PathVariable long acno) throws MyException
	{
		return customerService.viewtransaction(acno);
	}
}
