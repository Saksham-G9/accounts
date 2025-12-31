package com.example.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing customer accounts.
 * 
 * Provides endpoints for creating, retrieving, updating, and deleting customer
 * accounts.
 * All responses are returned in JSON format.
 */
@Tag(name = "Accounts Controller", description = "APIs for managing customer accounts")
@RestController
@RequestMapping(path = "api/accounts", produces = "application/json")
@RequiredArgsConstructor
public class AccountsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
    private final IAccountsService accountsService;

    /**
     * Creates a new customer account.
     * 
     * @param customerDto the customer data transfer object containing customer
     *                    details
     * @return ResponseEntity with status 201 and success message
     */
    @PostMapping
    @Operation(summary = "Create Account", description = "Creates a new customer account")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        logger.info("Creating account for customer with mobile number: {}", customerDto.getMobileNumber());
        accountsService.createAccount(customerDto);
        logger.info("Account created successfully for mobile number: {}", customerDto.getMobileNumber());
        return buildResponse(HttpStatus.CREATED, AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201);
    }

    /**
     * Retrieves account details for a specific customer.
     * 
     * @param mobileNumber the 10-digit mobile number of the customer
     * @return ResponseEntity with status 200 and customer account details
     */
    @GetMapping("/fetch")
    @Operation(summary = "Get Account Details", description = "Retrieves account details for a specific customer")
    public ResponseEntity<CustomerDto> getAccountDetails(
            @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") String mobileNumber) {
        logger.info("Fetching account details for mobile number: {}", mobileNumber);
        CustomerDto customerDto = accountsService.getAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    /**
     * Updates an existing customer account.
     * 
     * @param customerDto the customer data transfer object containing updated
     *                    customer details
     * @return ResponseEntity with status 200 if successful, or 404 if account not
     *         found
     */
    @PutMapping
    @Operation(summary = "Update Account", description = "Updates an existing customer account")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        logger.info("Updating account for customer with mobile number: {}", customerDto.getMobileNumber());
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if (isUpdated) {
            logger.info("Account updated successfully for mobile number: {}", customerDto.getMobileNumber());
            return buildResponse(HttpStatus.OK, AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200);
        } else {
            logger.warn("Account not found for mobile number: {}", customerDto.getMobileNumber());
            return buildResponse(HttpStatus.NOT_FOUND, AccountsConstants.STATUS_404, "Account not found.");
        }
    }

    /**
     * Deletes a customer account.
     * 
     * @param mobileNumber the 10-digit mobile number of the customer
     * @return ResponseEntity with status 200 if successful, or 404 if account not
     *         found
     */
    @DeleteMapping
    @Operation(summary = "Delete Account", description = "Deletes a customer account")
    public ResponseEntity<ResponseDto> deleteAccount(
            @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") String mobileNumber) {
        logger.info("Deleting account for mobile number: {}", mobileNumber);
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            logger.info("Account deleted successfully for mobile number: {}", mobileNumber);
            return buildResponse(HttpStatus.OK, AccountsConstants.STATUS_200, "Account deleted successfully.");
        } else {
            logger.warn("Account not found for deletion with mobile number: {}", mobileNumber);
            return buildResponse(HttpStatus.NOT_FOUND, AccountsConstants.STATUS_404, "Account not found.");
        }
    }

    /**
     * Helper method to build a consistent ResponseEntity.
     * 
     * @param status     the HTTP status code
     * @param statusCode the custom status code
     * @param message    the response message
     * @return ResponseEntity with the specified status and response body
     */
    private ResponseEntity<ResponseDto> buildResponse(HttpStatus status, String statusCode, String message) {
        return ResponseEntity.status(status).body(new ResponseDto(statusCode, message));
    }
}
