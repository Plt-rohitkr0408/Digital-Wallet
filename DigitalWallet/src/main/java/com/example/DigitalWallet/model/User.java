package com.example.DigitalWallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "authUsers")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String name;
    @Column(nullable = false)
    private String email;
    private String password;
    private LocalDateTime createAt;

    @PrePersist
    public void createAt(){
        this.createAt = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    private Role role;




}
