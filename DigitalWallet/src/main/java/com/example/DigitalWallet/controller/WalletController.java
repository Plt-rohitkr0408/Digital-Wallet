package com.example.DigitalWallet.controller;


import com.example.DigitalWallet.dto.response.WalletResponse;
import com.example.DigitalWallet.service.WalletService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<WalletResponse> createWallet(@PathVariable Long userId) {
        WalletResponse response = walletService.createWallet(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getWalletById(@PathVariable Long walletId) {
        WalletResponse response = walletService.getWalletById(walletId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<WalletResponse> getWalletByUserId(@PathVariable Long userId) {
        WalletResponse response = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long walletId) {
        BigDecimal balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }


    @GetMapping
    public ResponseEntity<Page<WalletResponse>> getAllWallets(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<WalletResponse> wallets = walletService.getAllWallets(pageable);
        return ResponseEntity.ok(wallets);
    }
}
