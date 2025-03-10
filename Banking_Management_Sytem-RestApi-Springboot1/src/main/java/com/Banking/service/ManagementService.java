package com.Banking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.Banking.entity.BankAccount;
import com.Banking.entity.Management;
import com.Banking.exception.MyException;
import com.Banking.helper.ResponseStructure;
import com.Banking.repository.BankRepository;
import com.Banking.repository.ManagementRepository;

@Service
public class ManagementService 
{
	@Autowired
    ManagementRepository managementRepository;
    
	@Autowired
    BankRepository bankRepository;
	
	public ResponseStructure<Management> save(Management management)
	{
		ResponseStructure<Management> structure=new ResponseStructure();
		structure.setCode(HttpStatus.CREATED.value());
		structure.setMessage("Data Added Successfully");
		structure.setData(managementRepository.save(management));
		return structure;
	}
	
	public ResponseStructure<Management> login(Management management) throws MyException
	{
		ResponseStructure<Management> structure=new ResponseStructure();
		
		Management management1=managementRepository.findByEmail(management.getEmail());
		
		if(management1 == null)
		{
			throw new MyException("Invalid Management email");
		}
		else
		{
			if(management1.getPassword().equals(management.getPassword()))
			{
				structure.setCode(HttpStatus.ACCEPTED.value());
				structure.setMessage("Login Success");
				structure.setData(management1);
			}
			else
			{
				throw new MyException("Invalid Password");
			}
		}
		return structure;
	}
	
	public ResponseStructure<List<BankAccount>> fetchAllAccounts() throws MyException
	{
		ResponseStructure<List<BankAccount>> structure=new ResponseStructure<List<BankAccount>>();
		
		List<BankAccount> list=bankRepository.findAll();
		if(list.isEmpty())
		{
			throw new MyException("No Accounts Present");
		}
		else
		{
			structure.setCode(HttpStatus.FOUND.value());
			structure.setMessage("Data Found");
			structure.setData(list);
		}
		return structure;
	}
	
	public ResponseStructure<BankAccount> changeStatus(long acno) {
		ResponseStructure<BankAccount> structure=new ResponseStructure<BankAccount>();
		
		Optional<BankAccount> optional=bankRepository.findById(acno);
		BankAccount account=optional.get();
		if(account.isStatus())
		{
			account.setStatus(false);
		}
		else{
		account.setStatus(true);
		}
		structure.setCode(HttpStatus.OK.value());
		structure.setMessage("Changed Status Success");
		structure.setData(bankRepository.save(account));
		return structure;
	}
}
