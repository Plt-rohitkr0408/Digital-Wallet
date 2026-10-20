package com.example.DigitalWallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Token is Required")
    String token;

    @NotBlank(message = "New Password is Required")
            @Size(min = 6, max = 64, message = "Password must be between 8 to 72")
    String newPassword;
}
