package com.Banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Banking.entity.Management;

@Repository
public interface ManagementRepository extends JpaRepository<Management, Integer>
{
    Management findByEmail(String email);
}
