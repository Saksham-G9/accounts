package com.example.accounts.mapper;

import java.util.Optional;

import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Customer;

public class CustomerMapper {

    private CustomerMapper() {
        // Private constructor to prevent instantiation
    }

    public static CustomerDto mapToCustomerDto(Customer customer, AccountsDto accountsDto) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    public static Customer mapToCustomerEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

}
