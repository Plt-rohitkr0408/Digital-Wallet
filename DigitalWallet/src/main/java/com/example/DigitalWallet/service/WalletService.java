package com.example.DigitalWallet.service;


import com.example.DigitalWallet.dto.response.WalletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface WalletService {
    WalletResponse createWallet(Long userId);

    WalletResponse getWalletById(Long walletId);

    WalletResponse getWalletByUserId(Long userId);

    BigDecimal getBalance(Long walletId);

    Page<WalletResponse> getAllWallets(Pageable pageable);
}
