package com.example.DigitalWallet.service.Impl;


import com.example.DigitalWallet.dto.request.ForgotPasswordRequest;
import com.example.DigitalWallet.dto.request.ResetPasswordWithOtpRequest;
import com.example.DigitalWallet.model.PasswordResetOtp;
import com.example.DigitalWallet.model.User;
import com.example.DigitalWallet.repository.PasswordResetOtpRepository;
import com.example.DigitalWallet.repository.UserRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class PasswordResetService1 {

    private final UserRepository userRepository;
    private final PasswordResetOtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    private final SecureRandom secureRandom = new SecureRandom();

    public PasswordResetService1(UserRepository userRepository, PasswordResetOtpRepository otpRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    public void sendOtp(ForgotPasswordRequest request){
        String email = request.getEmail().trim();
        var user = userRepository.findByEmail(email);

        if(user == null){
            return;
        }

        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1000000)
        );

        PasswordResetOtp passwordResetOtp =  otpRepository.findByEmail(email)
                .orElseGet(PasswordResetOtp::new);

        passwordResetOtp.setEmail(email);
        passwordResetOtp.setOtpHash(passwordEncoder.encode(otp));
        passwordResetOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        passwordResetOtp.setAttempts(0);

        otpRepository.save(passwordResetOtp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Digital Wallet - Password Reset OTP");
        message.setText(
                "Your password reset OTP is: " + otp
                        + "\n\nThis OTP expires in 5 minutes."
                        + "\nIf you did not request this, ignore this email."
        );

        mailSender.send(message);

    }

    public void resetPassword(ResetPasswordWithOtpRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        PasswordResetOtp resetOtp = otpRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid or expired OTP"
                        ));

        if (resetOtp.getExpiryTime().isBefore(LocalDateTime.now())
                || resetOtp.getAttempts() >= 5) {

            otpRepository.delete(resetOtp);

            throw new IllegalArgumentException(
                    "Invalid or expired OTP"
            );
        }

        if (!passwordEncoder.matches(
                request.getOtp(),
                resetOtp.getOtpHash())) {

            resetOtp.setAttempts(resetOtp.getAttempts() + 1);
            otpRepository.save(resetOtp);

            throw new IllegalArgumentException(
                    "Invalid or expired OTP"
            );
        }

        User user = userRepository.findByEmail(email);

        if(user == null){
           throw new IllegalArgumentException(
                    "Invalid or expired OTP"
            );
        }

        // Store the encoded password, never the raw password.
        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);
        otpRepository.delete(resetOtp);
    }
}