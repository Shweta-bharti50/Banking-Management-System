package com.Banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Banking.entity.BankAccount;

@Repository
public interface BankRepository extends JpaRepository<BankAccount, Long>{

}
