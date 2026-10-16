package com.example.DigitalWallet.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor
public class TransferRequest {
    private Long receiverId;
    private BigDecimal amount;
}
