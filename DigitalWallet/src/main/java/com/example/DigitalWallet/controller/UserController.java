
package com.example.DigitalWallet.controller;

import com.example.DigitalWallet.dto.request.LoginRequest;
import com.example.DigitalWallet.dto.request.UserRequest;
import com.example.DigitalWallet.dto.response.UserResponse;
import com.example.DigitalWallet.model.User;
import com.example.DigitalWallet.repository.UserRepository;
import com.example.DigitalWallet.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService ,  UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody UserRequest request) {

        UserResponse response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/id")
    public ResponseEntity<Long> getID(){
      Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
       String email = authentication.getName();
        User user =userRepository.findByEmail(email);
        return  ResponseEntity.ok(user.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.userById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(userService.allUser());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
