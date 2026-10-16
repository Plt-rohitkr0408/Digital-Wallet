package com.example.DigitalWallet.security;

import com.example.DigitalWallet.model.User;
import com.example.DigitalWallet.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    public final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null ){
            throw new RuntimeException("User not found");
        }

        System.out.println("user name"+ user.getName());
        System.out.println("user email"+ user.getEmail());
        System.out.println("user passowrd "+ user.getPassword());

        return new CustomUserDetail(user);
    }
}
