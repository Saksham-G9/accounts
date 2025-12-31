package com.example.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountsDto {
    @Pattern(regexp = "^\\d{12}$", message = "Account number must be 12 digits")
    private Long accountNumber;
    
    @NotEmpty(message = "Account type must not be empty")
    private String accountType;

    @NotEmpty(message = "Branch address must not be empty")
    private String branchAddress;

    @NotEmpty(message = "Balance must not be empty")
    private Double balance;
}
