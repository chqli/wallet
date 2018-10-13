package com.agrostar.wallet.service;

import com.agrostar.wallet.dto.TransactionType;
import com.agrostar.wallet.dto.Txn;
import com.agrostar.wallet.entity.Transaction;
import com.agrostar.wallet.entity.Wallet;
import com.agrostar.wallet.repository.TransactionRepo;
import com.agrostar.wallet.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionsService {
  @Autowired private WalletRepo walletRepo;
  @Autowired private TransactionRepo transactionRepo;

  public Wallet saveWallet() {
    Wallet wallet = new Wallet();
    Wallet saved = walletRepo.save(wallet);
    saved.setAmount(BigDecimal.ZERO);
    return saved;
  }

  public Optional<Wallet> getWallet(String walletId) {
    Optional<Wallet> byId = walletRepo.findById(Integer.parseInt(walletId));
    return byId;
  }

  @Transactional
  public Transaction saveTransaction(Wallet wallet, Txn txn) {
    Transaction transaction = new Transaction();
    transaction.setAmount(txn.getAmount());
    transaction.setType(txn.getType());
    transaction.setWallet(wallet);
    Transaction save = transactionRepo.save(transaction);
    BigDecimal balance = applyTransactionToWallet(txn, wallet.getAmount());
    wallet.setAmount(balance);
    walletRepo.save(wallet);
    return save;
  }

  private BigDecimal applyTransactionToWallet(Txn txn, BigDecimal amount) {
    if (txn.getType() == TransactionType.CREDIT) {
      return amount.add(txn.getAmount());
    }
    return amount.subtract(txn.getAmount());
  }
}
