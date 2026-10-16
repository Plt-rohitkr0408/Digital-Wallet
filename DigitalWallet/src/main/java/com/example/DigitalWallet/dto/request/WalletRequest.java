package com.example.DigitalWallet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class WalletRequest {
    private BigDecimal balance;
    private String currency;
}
