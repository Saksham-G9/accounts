package com.example.accounts.service;

import com.example.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     * Create a new account for the given customer.
     *
     * @param customerDto Data transfer object containing customer details.
     */
    void createAccount(CustomerDto customerDto);

    // CustomerDto getAccountDetails(Long customerId);
}
