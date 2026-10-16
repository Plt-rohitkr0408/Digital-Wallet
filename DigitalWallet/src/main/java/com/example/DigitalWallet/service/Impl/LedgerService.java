package com.example.DigitalWallet.service.Impl;



import com.example.DigitalWallet.model.LadgerEntity;
import com.example.DigitalWallet.model.LadgerType;
import com.example.DigitalWallet.model.Transfer;
import com.example.DigitalWallet.model.Wallet;
import com.example.DigitalWallet.repository.LedgerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class LedgerService {

    private final LedgerRepository ledgerEntryRepository;

    public LedgerService(
            LedgerRepository ledgerEntryRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Transactional
    public LadgerEntity createLedgerEntry(
            Wallet wallet,
            Transfer transfer,
            LadgerType entryType,
            BigDecimal amount) {

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }

        LadgerEntity entry = new LadgerEntity();
        entry.setWallet(wallet);
        entry.setTransfer(transfer);
        entry.setLadgerType(entryType);
        entry.setAmount(amount);
        entry.setBalanceAfter(wallet.getBalance());

        return ledgerEntryRepository.save(entry);
    }

    @Transactional(readOnly = true)
    public Page<LadgerEntity> getEntriesByWalletId(
            Long walletId, Pageable pageable) {

        return ledgerEntryRepository
                .findByWallet_IdOrderByCreatedAtDesc(
                        walletId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<LadgerEntity> getEntriesByTransferId(
            Long transferId, Pageable pageable) {

        return ledgerEntryRepository
                .findByTransfer_Id(transferId, pageable);
    }
}
