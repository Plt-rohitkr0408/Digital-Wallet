package com.example.DigitalWallet.controller;


import com.example.DigitalWallet.dto.request.ForgotPasswordRequest;
import com.example.DigitalWallet.dto.request.ResetPasswordWithOtpRequest;
import com.example.DigitalWallet.service.Impl.PasswordResetService1;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetOtpController {

    private final PasswordResetService1 passwordResetService;

    public PasswordResetOtpController(
            PasswordResetService1 passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        passwordResetService.sendOtp(request);

        return ResponseEntity.ok(
                "If the account exists, a password reset OTP will be sent."
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordWithOtpRequest request) {

        passwordResetService.resetPassword(request);

        return ResponseEntity.ok(
                "Password reset successfully."
        );
    }
}
