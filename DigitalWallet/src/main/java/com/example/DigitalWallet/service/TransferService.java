package com.example.DigitalWallet.service;

import com.example.DigitalWallet.dto.request.TransferRequest;
import com.example.DigitalWallet.dto.response.TransferResponse;
import com.example.DigitalWallet.model.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransferService {

    TransferResponse transferMoney(TransferRequest request);

    TransferResponse getTransferById(Long transferId);

    List<TransferResponse> getTransfersByWalletId(Long walletId);

    List<TransferResponse> getTransfersByUserId(Long userId);

    Page<TransferResponse> getAllTransfers(Pageable pageable);
}
