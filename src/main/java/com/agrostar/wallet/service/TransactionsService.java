package com.agrostar.wallet.service;

import com.agrostar.wallet.dto.TransactionStatus;
import com.agrostar.wallet.dto.TransactionType;
import com.agrostar.wallet.dto.Txn;
import com.agrostar.wallet.entity.Transaction;
import com.agrostar.wallet.entity.Wallet;
import com.agrostar.wallet.exceptions.TransactionFailedException;
import com.agrostar.wallet.exceptions.TransactionNotFoundException;
import com.agrostar.wallet.exceptions.WalletLimitExceededException;
import com.agrostar.wallet.exceptions.WalletNotFoundException;
import com.agrostar.wallet.repository.TransactionRepo;
import com.agrostar.wallet.repository.WalletRepo;
import com.agrostar.wallet.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionsService {
  private WalletRepo walletRepo;
  private TransactionRepo transactionRepo;

  public TransactionsService(
      @Autowired WalletRepo walletRepo, @Autowired TransactionRepo transactionRepo) {
    this.walletRepo = walletRepo;
    this.transactionRepo = transactionRepo;
  }

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

  @Transactional(
      isolation = Isolation.READ_COMMITTED,
      rollbackFor = TransactionFailedException.class)
  public Transaction saveTransaction(String walletId, Txn txn) {
    Optional<Wallet> byId = walletRepo.findByIdInWriteMode(Integer.parseInt(walletId));
    if (!byId.isPresent()) {
      throw new WalletNotFoundException("sdfg");
    }
    Wallet wallet = byId.get();
    Transaction transaction = new Transaction();
    transaction.setAmount(txn.getAmount());
    transaction.setType(txn.getType());
    transaction.setWallet(wallet);
    BigDecimal balance = null;
    try {
      balance = handleTransactionToWallet(txn, wallet.getBalance());
    } catch (WalletLimitExceededException e) {
      throw new TransactionFailedException("Wallet limit exceeded");
    }
    transactionRepo.save(transaction);
    wallet.setBalance(balance);
    wallet.addTransaction(transaction);
    return transaction;
  }

  private BigDecimal handleTransactionToWallet(Txn txn, BigDecimal amount)
      throws WalletLimitExceededException {
    BigDecimal updatedBalance;
    if (txn.getStatus() == TransactionStatus.ACTIVE) {
      updatedBalance = applyTransactionToWallet(txn, amount);

    } else {
      updatedBalance = undoTransactionToWallet(txn, amount);
    }
    if (updatedBalance.doubleValue() < -1 * Constants.NEGATIVE_BALANCE_CAP) {
      throw new WalletLimitExceededException();
    }
    return updatedBalance;
  }

  private BigDecimal applyTransactionToWallet(Txn txn, BigDecimal amount) {
    if (txn.getType() == TransactionType.CREDIT) {
      return amount.add(txn.getAmount());
    }
    return amount.subtract(txn.getAmount());
  }

  private BigDecimal undoTransactionToWallet(Txn txn, BigDecimal amount) {
    if (txn.getType() == TransactionType.DEBIT) {
      return amount.add(txn.getAmount());
    }
    return amount.subtract(txn.getAmount());
  }

  @Transactional(
      isolation = Isolation.READ_COMMITTED,
      rollbackFor = TransactionFailedException.class)
  public Transaction deleteTransaction(String walletId, String transactionId) {
    Optional<Wallet> byId = walletRepo.findByIdInWriteMode(Integer.parseInt(walletId));
    if (!byId.isPresent()) {
      throw new WalletNotFoundException("Wallet not found");
    }
    Wallet wallet = byId.get();
    Optional<Transaction> byId1 = transactionRepo.findById(Long.valueOf(transactionId));
    if (byId1.isPresent() && byId1.get().getWallet().getWalletId() == Integer.parseInt(walletId)) {
      byId1.get().setStatus(TransactionStatus.CANCELLED);
      BigDecimal bigDecimal = null;
      try {
        bigDecimal =
            handleTransactionToWallet(
                new Txn(
                    byId1.get().getAmount(), byId1.get().getType(), TransactionStatus.CANCELLED),
                wallet.getBalance());
      } catch (WalletLimitExceededException e) {
        throw new TransactionFailedException("Wallet limit exceeded");
      }
      wallet.setBalance(bigDecimal);
      return byId1.get();
    } else {
      throw new TransactionNotFoundException("Transaction not found");
    }
  }
}
