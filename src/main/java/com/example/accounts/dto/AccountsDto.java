package com.example.accounts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountsDto {

    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
