package com.example.DigitalWallet.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String email;
    private String name;
    private Long userId;
    private LocalDateTime createAt;
    private String role;
}
