package com.example.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.accounts.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}
