package com.example.DigitalWallet.controller;



import com.example.DigitalWallet.dto.request.TransferRequest;
import com.example.DigitalWallet.dto.response.TransferResponse;
import com.example.DigitalWallet.service.TransferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferResponse transferMoney(
            @RequestBody TransferRequest request) {

        return transferService.transferMoney(request);
    }

    @GetMapping("/{id}")
    public TransferResponse getTransferById(
            @PathVariable Long id) {

        return transferService.getTransferById(id);
    }

    @GetMapping("/wallet/{walletId}")
    public List<TransferResponse> getTransfersByWalletId(
            @PathVariable Long walletId) {
        return transferService.getTransfersByWalletId(
                walletId);
    }

    @GetMapping("/user/{userId}")
    public List<TransferResponse> getTransfersByUserId(
            @PathVariable Long userId
           ) {

        return transferService.getTransfersByUserId(
                userId);
    }


    @GetMapping
    public Page<TransferResponse> getAllTransfers(
            @PageableDefault(size = 10) Pageable pageable) {

        return transferService.getAllTransfers(pageable);
    }
}
