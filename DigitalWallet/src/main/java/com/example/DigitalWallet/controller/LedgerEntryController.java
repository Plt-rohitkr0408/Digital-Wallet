package com.example.DigitalWallet.controller;

import com.example.DigitalWallet.model.LadgerEntity;
import com.example.DigitalWallet.service.Impl.LedgerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ledger")
public class LedgerEntryController {
    private final LedgerService ledgerService;

    public LedgerEntryController(LedgerService ledgerService){
        this.ledgerService = ledgerService;
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<Page<LadgerEntity>> getEntriesByWalletId(@PathVariable("walletId") Long walletId,
                                                                   @PageableDefault(size = 5)
                                                                   Pageable pageable){
        Page<LadgerEntity> ladgerEntities = ledgerService.getEntriesByWalletId(walletId, pageable);

        return ResponseEntity.ok(ladgerEntities);
    }


    @GetMapping("/transfer/{transferId}")
    public ResponseEntity<Page<LadgerEntity>> getEntriesByTransferId(@PathVariable("transferId") Long transferId,
                                                                     @PageableDefault(size = 5) Pageable pageable){
        Page<LadgerEntity> ladgerEntities = ledgerService.getEntriesByTransferId(transferId, pageable);

        return ResponseEntity.ok(ladgerEntities);
    }


}
