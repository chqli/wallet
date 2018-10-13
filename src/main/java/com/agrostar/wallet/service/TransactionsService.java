package com.agrostar.wallet.service;

import com.agrostar.wallet.entity.Wallet;
import com.agrostar.wallet.repository.TransactionRepo;
import com.agrostar.wallet.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionsService {
  @Autowired private WalletRepo walletRepo;
  @Autowired private TransactionRepo transactionRepo;

  public Wallet createNewWallet() {
    Wallet wallet = new Wallet();
    Wallet saved = walletRepo.save(wallet);
    saved.setAmount(BigDecimal.ZERO);
    return saved;
  }

  public Optional<Wallet> getWallet(String walletId) {
    Optional<Wallet> byId = walletRepo.findById(Integer.parseInt(walletId));
    return byId;
  }
}
