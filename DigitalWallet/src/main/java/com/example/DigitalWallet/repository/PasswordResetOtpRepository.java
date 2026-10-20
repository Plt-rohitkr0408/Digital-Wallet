package com.example.DigitalWallet.repository;

import com.example.DigitalWallet.model.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp , Long> {
    Optional<PasswordResetOtp> findByEmail(String email);
}
