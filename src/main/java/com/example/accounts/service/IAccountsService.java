package com.example.accounts.service;

import com.example.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     * Create a new account for the given customer.
     *
     * @param customerDto Data transfer object containing customer details.
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Retrieve account details based on the provided mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer.
     * @return CustomerDto containing customer and account details.
     */
    CustomerDto getAccountDetails(String mobileNumber);
}
