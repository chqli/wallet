package com.agrostar.wallet.repository;

import com.agrostar.wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sun.print.resources.serviceui_zh_TW;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {}
