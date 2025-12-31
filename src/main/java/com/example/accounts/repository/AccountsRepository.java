package com.example.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.accounts.entity.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    
    Optional<Accounts> findByCustomerId(Long customerId);
    Optional<Accounts> findByAccountNumber(Long accountNumber);
}
