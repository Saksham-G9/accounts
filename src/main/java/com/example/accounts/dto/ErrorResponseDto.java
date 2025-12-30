package com.example.accounts.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private String apiPath;
    private String errorMessage;
    private String errorCode;
    private LocalDateTime timestamp;
}
