package com.example.DigitalWallet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data @NoArgsConstructor
@AllArgsConstructor @Builder
public class TransferResponse {
    private Long id;
    private String sendName;
    private String receiverName;
    private BigDecimal amount;
    private Long receiverId;
    private LocalDateTime sendAt;
}
