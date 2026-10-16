package com.example.DigitalWallet.service;

import com.example.DigitalWallet.dto.request.LoginRequest;
import com.example.DigitalWallet.dto.request.UserRequest;
import com.example.DigitalWallet.dto.response.UserResponse;
import java.util.*;

public interface UserService {
    UserResponse createUser(UserRequest request);
    String login(LoginRequest request);
    List<UserResponse> allUser();
    UserResponse userById(Long id);
    void deleteById(Long id);
}
