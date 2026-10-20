package com.example.DigitalWallet.ExceptionHandler;


import com.example.DigitalWallet.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeException(RuntimeException message, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                message.getCause().toString(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                message.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException message ,  HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                message.getCause().toString(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                message.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
