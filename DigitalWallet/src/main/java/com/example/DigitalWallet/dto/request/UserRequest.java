package com.example.DigitalWallet.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserRequest {
    private String email;
    private String name;
    private String password;
    private String role;

    public UserRequest(){}

    public UserRequest(String email, String name, String password, String role){
        this.email =email;
        this.name = name;
        this.password = password;
        this.role = role;
    }


}
