package com.example.DigitalWallet.service.Impl;

import com.example.DigitalWallet.dto.request.LoginRequest;
import com.example.DigitalWallet.dto.request.UserRequest;
import com.example.DigitalWallet.dto.response.UserResponse;
import com.example.DigitalWallet.model.Role;
import com.example.DigitalWallet.model.User;
import com.example.DigitalWallet.repository.UserRepository;
import com.example.DigitalWallet.security.JwtTokenService;
import com.example.DigitalWallet.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserSericeImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService jwtTokenService ;

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public UserSericeImpl(PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("User Already Exist");
        }

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .build();
        userRepository.save(user);

        return userResponse(user);

    }

    @Override
    public String login(LoginRequest request) {

        System.out.println("Authenticate manager calling ......");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        System.out.println("user email "+request.getEmail());
        System.out.println("user password "+ request.getPassword());

        if(!authentication.isAuthenticated()){
            throw new RuntimeException("Bad Credential");
        }


        return jwtTokenService.generateAccessToken(request.getEmail());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> allUser() {
        List<User> users = userRepository.findAll();
        return allUserResponse(users);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse userById(Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findById(id)
                .orElseThrow(()-> new BadCredentialsException("No found "));
        return userResponse(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    public List<UserResponse> allUserResponse(List<User> users)  {
        return users.stream().map(this::userResponse
                ).toList();
    }
    public UserResponse userResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreateAt(user.getCreateAt());
        userResponse.setRole(String.valueOf(user.getRole()));

        return userResponse;
    }
}
