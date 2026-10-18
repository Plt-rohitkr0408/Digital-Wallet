package com.example.DigitalWallet.repository;

import com.example.DigitalWallet.model.User;
import com.example.DigitalWallet.model.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Wallet findByUser(User user);
    Page<Wallet> findAll(Pageable pageable);
    Page<Wallet> findByCurrency(String currency, Pageable pageable);
    Page<Wallet> findByBalanceGreaterThan(BigDecimal balance, Pageable pageable);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT w 
            FROM Wallet w
            WHERE w.id IN :walletIds
            ORDER BY w.id ASC
            """)
    List<Wallet> findWalletsForUpdate( @Param("walletIds") Collection<Long> walletIds);
}
