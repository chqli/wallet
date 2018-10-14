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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionsService {
  @Autowired private WalletRepo walletRepo;
  @Autowired private TransactionRepo transactionRepo;

  public Wallet saveWallet() {
    Wallet wallet = new Wallet();
    Wallet saved = walletRepo.save(wallet);
    saved.setBalance(BigDecimal.ZERO);
    return saved;
  }

  public Optional<Wallet> getWallet(String walletId) {
    Optional<Wallet> byId = walletRepo.findById(Integer.parseInt(walletId));
    return byId;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
  public Transaction saveTransaction(String walletId, Txn txn) {
    Optional<Wallet> byId = walletRepo.findById(Integer.parseInt(walletId));
    if (!byId.isPresent()) {
      throw new WalletNotFoundException();
    }
    Wallet wallet = byId.get();
    Transaction transaction = new Transaction();
    transaction.setAmount(txn.getAmount());
    transaction.setType(txn.getType());
    transaction.setWallet(wallet);
    BigDecimal balance = applyTransactionToWallet(txn, wallet.getBalance());
    wallet.setBalance(balance);
    Transaction save = transactionRepo.save(transaction);
    transactionRepo.flush();
    walletRepo.flush();
    return save;
  }

  private BigDecimal applyTransactionToWallet(Txn txn, BigDecimal amount) {
    if (txn.getType() == TransactionType.CREDIT) {
      return amount.add(txn.getAmount());
    }
    return amount.subtract(txn.getAmount());
  }
}
