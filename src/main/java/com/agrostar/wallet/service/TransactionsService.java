package com.agrostar.wallet.service;

import com.agrostar.wallet.converter.WalletNotFoundException;
import com.agrostar.wallet.dto.TransactionType;
import com.agrostar.wallet.dto.Txn;
import com.agrostar.wallet.entity.Transaction;
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

  public Wallet saveWallet() {
    Wallet wallet = new Wallet();
    Wallet saved = walletRepo.save(wallet);
    return saved;
  }

  public BigDecimal getWalletBalance(String walletId) {
    Optional<Wallet> byId = walletRepo.findById(Integer.parseInt(walletId));
    if (!byId.isPresent()) {
      throw new WalletNotFoundException();
    }
    return transactionRepo.getBalance(Integer.parseInt(walletId));
  }

  public Wallet getWallet(String walletId) {
    Optional<Wallet> byId = walletRepo.findById(Integer.parseInt(walletId));
    if (!byId.isPresent()) {
      throw new WalletNotFoundException();
    }

    return byId.get();
  }

  public Transaction saveTransaction(String walletId, Txn txn) {

    Wallet wallet = getWallet(walletId);
    Transaction transaction = new Transaction();
    transaction.setAmount(txn.getAmount());
    transaction.setType(txn.getType());
    transaction.setWallet(wallet);
    return transactionRepo.save(transaction);
  }

  private BigDecimal applyTransactionToWallet(Txn txn, BigDecimal amount) {
    if (txn.getType() == TransactionType.CREDIT) {
      return amount.add(txn.getAmount());
    }
    return amount.subtract(txn.getAmount());
  }
}
