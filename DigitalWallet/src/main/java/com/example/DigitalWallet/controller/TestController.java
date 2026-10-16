package com.example.DigitalWallet.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/print")
    public ResponseEntity<String> print(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Controller class working good");
    }

}
