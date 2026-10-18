package com.example.DigitalWallet.repository;

import com.example.DigitalWallet.model.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
    List<Transfer> findByReceiverId(Long receiverId);
    Page<Transfer> findAll(Pageable pageable);
    List<Transfer> findBySenderId(Long walletId);

    Optional<Transfer> findByIdempotencyKey(String key);
}
