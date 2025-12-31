package com.example.accounts.service.impl;

import org.springframework.stereotype.Service;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountsMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountsRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.IAccountsService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        boolean exists = customerRepository.findByMobileNumber(customerDto.getMobileNumber()).isPresent()
                || customerRepository.findByEmail(customerDto.getEmail()).isPresent();
        if (exists) {
            throw new CustomerAlreadyExistsException("Customer with given mobile number or email already exists.");
        }

        Customer customer = CustomerMapper.mapToCustomerEntity(customerDto);
        customer.setCreatedBy(AccountsConstants.CREATED_BY_SYSTEM);
        customerRepository.save(customer);
        Accounts account = createNewAccountForCustomer(customer.getCustomerId());
        account.setCreatedBy(AccountsConstants.CREATED_BY_SYSTEM);
        account.setUpdatedBy(AccountsConstants.CREATED_BY_SYSTEM);
        accountsRepository.save(account);
    }

    private Accounts createNewAccountForCustomer(Long customerId) {
        Accounts account = new Accounts();
        account.setCustomerId(customerId);
        account.setAccountNumber(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBalance(0.0);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        return account;
    }

    @Override
    public CustomerDto getAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "customerId",
                        customer.getCustomerId().toString()));

        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(account);
        return CustomerMapper.mapToCustomerDto(customer, accountsDto);
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts account = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Accounts", "accountNumber",
                            accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccountsEntity(accountsDto);
            accountsRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));
            CustomerMapper.mapToCustomerEntity(customerDto);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

}