package com.agrostar.wallet.repository;

import com.agrostar.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, Integer> {
  @Query(value = "FROM Wallet W where W.walletId = :id")
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Wallet> findByIdInWriteMode(@Param("id") Integer id);
}
