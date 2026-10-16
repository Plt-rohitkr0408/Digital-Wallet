package com.example.DigitalWallet.repository;

import com.example.DigitalWallet.model.LadgerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerRepository extends JpaRepository<LadgerEntity, Long> {

    @Query("""
            SELECT l FROM LadgerEntity l 
            WHERE l.wallet.id = :walletId 
            ORDER BY  l.createAt DESC
            """)
    Page<LadgerEntity> findByWallet_IdOrderByCreatedAtDesc(
            @Param("walletId") Long walletId, Pageable pageable);

    Page<LadgerEntity> findByTransfer_Id(
            Long transferId, Pageable pageable);
}
