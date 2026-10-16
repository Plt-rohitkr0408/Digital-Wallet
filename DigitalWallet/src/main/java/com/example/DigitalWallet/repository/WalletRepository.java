package com.example.DigitalWallet.repository;

import com.example.DigitalWallet.model.User;
import com.example.DigitalWallet.model.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Wallet findByUser(User user);
    Page<Wallet> findAll(Pageable pageable);
    Page<Wallet> findByCurrency(String currency, Pageable pageable);

    Page<Wallet> findByBalanceGreaterThan(BigDecimal balance, Pageable pageable);
}
