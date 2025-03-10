package com.Banking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Banking.entity.BankAccount;
import com.Banking.entity.BankTransaction;
import com.Banking.entity.Customer;
import com.Banking.entity.Login;
import com.Banking.exception.MyException;
import com.Banking.helper.MailVerification;
import com.Banking.helper.ResponseStructure;
import com.Banking.repository.BankRepository;
import com.Banking.repository.CustomerRepository;

@Service
public class CustomerService 
{
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	BankRepository bankRepository;

	@Autowired
	MailVerification mailVerification;

	@Autowired
	BankAccount bankAccount;

	@Autowired
	BankTransaction bankTransaction;

	public ResponseStructure<Customer> save(Customer customer) throws MyException 
	{
		ResponseStructure<Customer> structure = new ResponseStructure();
		int age = Period.between(customer.getDob().toLocalDate(), LocalDate.now()).getYears();
		customer.setAge(age);

		if (age > 18) 
		{
			throw new MyException("you should be 18+ to create Account");
		} 
		else
		{
			Random random = new Random();
			int otp = random.nextInt(10000, 99999);
			customer.setOtp(otp);

			structure.setMessage("Verification Mail sent");
			structure.setCode(HttpStatus.PROCESSING.value());
			structure.setData(customerRepository.save(customer));
		}
		return structure;
	}

	public ResponseStructure<Customer> verify(int custid, int otp) throws MyException
	{
		ResponseStructure<Customer> structure = new ResponseStructure<Customer>();

		Optional<Customer> optional = customerRepository.findById(custid);

		if (optional.isEmpty())
		{
			throw new MyException("Check Id and Try Again");
		} 
		else
		{
			Customer customer = optional.get();
			if (customer.getOtp() == otp) {
				structure.setCode(HttpStatus.CREATED.value());
				structure.setMessage("Account Created Successfully");
				customer.setStatus(true);
				structure.setData(customerRepository.save(customer));
			}
			else
			{
				throw new MyException("OTP MISSMATCH");
			}
		}

		return structure;
	}

	public ResponseStructure<Customer> login(Login login) throws MyException
	{
		ResponseStructure<Customer> structure = new ResponseStructure<Customer>();

		Optional<Customer> optional = customerRepository.findById(login.getId());

		if (optional.isEmpty()) 
		{
			throw new MyException("Invalid Customer Id ");
		} 
		else 
		{
			Customer customer = optional.get();
			if (customer.getPassword().equals(login.getPassword()))
			{
				if (customer.isStatus())
				{
					structure.setCode(HttpStatus.ACCEPTED.value());
					structure.setMessage("Login Success");
					structure.setData(customer);
				} 
				else
				{
					throw new MyException("Verify your email first");
				}
			} 
			else
			{
				throw new MyException("Invalid Password");
			}
		}
		return structure;
	}

	public ResponseStructure<Customer> createAccount(int cust_id, String type) throws MyException
	{
		ResponseStructure<Customer> structure = new ResponseStructure<Customer>();

		Optional<Customer> optional = customerRepository.findById(cust_id);

		if (optional.isEmpty()) {
			throw new MyException("Invalid Customer Id ");
		}
		else 
		{
			Customer customer = optional.get();
			List<BankAccount> list = customer.getAccounts();

			boolean flag = true;
			for (BankAccount account : list) 
			{
				if (account.getType().equals(type)) 
				{
					flag = false;
					break;
				}
			}

			if (!flag) 
			{
				throw new MyException(type + " Account Already Exists");
			} 
			else
			{
				bankAccount.setType(type);
				if (type.equals("savings"))
				{
					bankAccount.setBanklimit(5000);
				}
				else
				{
					bankAccount.setBanklimit(10000);
				}

				list.add(bankAccount);
				customer.setAccounts(list);
			}
			structure.setCode(HttpStatus.ACCEPTED.value());
			structure.setMessage("Account created wait for management to approve");
			structure.setData(customerRepository.save(customer));
		}
		return structure;
	}

	public ResponseStructure<List<BankAccount>> fetchAllTrue(int custid) throws MyException
	{
		ResponseStructure<List<BankAccount>> structure = new ResponseStructure<List<BankAccount>>();

		Optional<Customer> optional = customerRepository.findById(custid);
		Customer customer = optional.get();
		List<BankAccount> list = customer.getAccounts();

		List<BankAccount> res = new ArrayList<BankAccount>();

		for (BankAccount account : list)
		{
			if (account.isStatus())
			{
				res.add(account);
			}
		}

		if (res.isEmpty())
		{
			throw new MyException("No Active Accounts Found");
		} 
		else
		{
			structure.setCode(HttpStatus.FOUND.value());
			structure.setMessage("Accounts FOund");
			structure.setData(res);
		}
		return structure;
	}

	public ResponseStructure<Double> checkBalance(long acno) 
	{
		ResponseStructure<Double> structure = new ResponseStructure<Double>();

		Optional<BankAccount> optional = bankRepository.findById(acno);
		BankAccount account = optional.get();

		structure.setCode(HttpStatus.FOUND.value());
		structure.setMessage("Data Found");
		structure.setData(account.getAmount());
		return structure;
	}

	public ResponseStructure<BankAccount> deposit(long accno, double amount) 
	{
		ResponseStructure<BankAccount> structure = new ResponseStructure<BankAccount>();

		BankAccount account = bankRepository.findById(accno).get();
		account.setAmount(account.getAmount() + amount);

		bankTransaction.setDateTime(LocalDateTime.now());
		bankTransaction.setDeposite(amount);
		bankTransaction.setBalance(account.getAmount());

		List<BankTransaction> transactions = account.getBankTransactions();
		transactions.add(bankTransaction);

		account.setBankTransactions(transactions);

		structure.setCode(HttpStatus.ACCEPTED.value());
		structure.setMessage("Amount added Successfully");
		structure.setData(bankRepository.save(account));

		return structure;
	}

	public ResponseStructure<BankAccount> withdraw(long acno, double amount) throws MyException 
	{
		ResponseStructure<BankAccount> structure = new ResponseStructure<BankAccount>();

		BankAccount account = bankRepository.findById(acno).get();

		if (amount > account.getBanklimit()) 
		{
			throw new MyException("Out of Limit");
		}
		else 
		{
			if (amount > account.getAmount()) 
			{
				throw new MyException("Insufficient funds");
			} 
			else
			{
				account.setAmount(account.getAmount() - amount);

				bankTransaction.setDateTime(LocalDateTime.now());
				bankTransaction.setDeposite(0);
				bankTransaction.setWithdrawl(amount);
				bankTransaction.setBalance(account.getAmount());

				List<BankTransaction> transactions = account.getBankTransactions();
				transactions.add(bankTransaction);

				account.setBankTransactions(transactions);

				structure.setCode(HttpStatus.ACCEPTED.value());
				structure.setMessage("Amount withdrwan Successfully");
				structure.setData(bankRepository.save(account));
			}
		}
		return structure;
	}

	public ResponseStructure<List<BankTransaction>> viewtransaction(long acno) throws MyException 
	{

		ResponseStructure<List<BankTransaction>> structure = new ResponseStructure<List<BankTransaction>>();

		BankAccount account = bankRepository.findById(acno).get();
		List<BankTransaction> list = account.getBankTransactions();
		if (list.isEmpty()) 
		{
			throw new MyException("No Transaction");
		}
		else 
		{
			structure.setCode(HttpStatus.FOUND.value());
			structure.setMessage("Data Found");
			structure.setData(list);
		}
		return structure;
	}
}
