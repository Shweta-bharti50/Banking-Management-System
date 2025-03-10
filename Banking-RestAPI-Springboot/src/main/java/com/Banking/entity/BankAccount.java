package com.Banking.entity;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
@Component
public class BankAccount 
{
	@Id
	@SequenceGenerator(name="acno",sequenceName = "acno",initialValue = 1002121111,allocationSize = 1)
	@GeneratedValue(generator = "acno")
    long number;
    String type;
    double banklimit;
    double amount;
    boolean status;
    
    @OneToMany(cascade = CascadeType.ALL)
    List<BankTransaction> bankTransactions;

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBanklimit() {
		return banklimit;
	}

	public void setBanklimit(double banklimit) {
		this.banklimit = banklimit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<BankTransaction> getBankTransactions() {
		return bankTransactions;
	}

	public void setBankTransactions(List<BankTransaction> bankTransactions) {
		this.bankTransactions = bankTransactions;
	}
}
