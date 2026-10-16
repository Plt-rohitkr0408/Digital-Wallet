package com.example.DigitalWallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class WalletResponse {
    private Long walletId;
    private Long userID;
    private BigDecimal balance;
    private String currency;
}
