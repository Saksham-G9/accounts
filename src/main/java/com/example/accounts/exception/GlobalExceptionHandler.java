package com.example.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.accounts.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex,
            WebRequest request) {
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setApiPath(request.getDescription(false));
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setTimestamp(java.time.LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

}
