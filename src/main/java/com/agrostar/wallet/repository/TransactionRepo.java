package com.agrostar.wallet.repository;

import com.agrostar.wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
  @Query(
      value =
          "SELECT SUM(CASE WHEN type = 'CREDIT' THEN amount WHEN type = 'DEBIT' THEN -1\\*amount END)\n"
              + "FROM transaction where owner_id=:id and status='ACTIVE'",
      nativeQuery = true)
  BigDecimal getBalance(@Param("id") int id);
}
