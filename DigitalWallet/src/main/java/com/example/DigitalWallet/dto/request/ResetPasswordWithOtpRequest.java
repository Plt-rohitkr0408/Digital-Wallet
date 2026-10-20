package com.example.DigitalWallet.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordWithOtpRequest
{
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{6}",
            message = "OTP must contain exactly 6 digits")
    private String otp;

    @NotBlank
    @Size(min = 8, max = 72,
            message = "Password must be 8-72 characters")
    private String newPassword;
}
