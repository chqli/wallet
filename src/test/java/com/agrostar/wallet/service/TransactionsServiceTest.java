package com.agrostar.wallet.service;

import com.agrostar.wallet.dto.TransactionStatus;
import com.agrostar.wallet.dto.TransactionType;
import com.agrostar.wallet.dto.Txn;
import com.agrostar.wallet.entity.Transaction;
import com.agrostar.wallet.entity.Wallet;
import com.agrostar.wallet.exceptions.TransactionFailedException;
import com.agrostar.wallet.repository.TransactionRepo;
import com.agrostar.wallet.repository.WalletRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class TransactionsServiceTest {
  @Mock WalletRepo walletRepo;
  @Mock TransactionRepo transactionRepo;
  private TransactionsService transactionsService;
  private Wallet wallet;

  @Before
  public void setupMock() {
    MockitoAnnotations.initMocks(this);
    transactionsService = new TransactionsService(walletRepo, transactionRepo);
    setupWallet();
  }

  private void setupWallet() {
    wallet = new Wallet();
    wallet.setWalletId(1);
    wallet.setBalance(BigDecimal.TEN);
    wallet.addTransaction(
        new Transaction(
            1L, TransactionType.CREDIT, wallet, BigDecimal.TEN, TransactionStatus.ACTIVE));
  }

  @Test
  public void testAddingCreditTransaction() {
    Txn txn = new Txn(BigDecimal.ONE, TransactionType.CREDIT, TransactionStatus.ACTIVE);
    when(walletRepo.findByIdInWriteMode(1)).thenReturn(Optional.ofNullable(wallet));
    // Act
    transactionsService.saveTransaction(1, txn);
    // Assert
    assertThat(wallet.getTransactions().size(), is(2));
    assertThat(wallet.getBalance(), is(new BigDecimal(11)));
  }

  @Test
  public void testAddingDebitTransaction() {
    Txn txn = new Txn(BigDecimal.ONE, TransactionType.DEBIT, TransactionStatus.ACTIVE);
    when(walletRepo.findByIdInWriteMode(1)).thenReturn(Optional.ofNullable(wallet));
    // Act
    transactionsService.saveTransaction(1, txn);
    // Assert
    assertThat(wallet.getTransactions().size(), is(2));
    assertThat(wallet.getBalance(), is(new BigDecimal(9)));
  }

  @Test
  public void testLimitExceededWhileSavingTransaction() {
    Txn txn =
        new Txn(
            wallet.getBalance().add(new BigDecimal(50001)),
            TransactionType.DEBIT,
            TransactionStatus.ACTIVE);
    when(walletRepo.findByIdInWriteMode(1)).thenReturn(Optional.ofNullable(wallet));
    try {
      transactionsService.saveTransaction(1, txn);
      Assert.fail("Should throw Exception");
    } catch (TransactionFailedException tef) {
      assertThat(wallet.getTransactions().size(), is(1));
      assertThat(wallet.getBalance(), is(BigDecimal.TEN));
    }
  }

  @Test
  public void testLimitExceededWhileDeletingTransaction() {
    Transaction txn =
        new Transaction(
            1L,
            TransactionType.CREDIT,
            wallet,
            wallet.getBalance().add(new BigDecimal(50001)),
            TransactionStatus.ACTIVE);
    when(walletRepo.findByIdInWriteMode(1)).thenReturn(Optional.ofNullable(wallet));
    when(transactionRepo.findById(1L)).thenReturn(Optional.of(txn));
    try {
      transactionsService.deleteTransaction(1, String.valueOf(1L));
      Assert.fail("Should throw exception");
    } catch (TransactionFailedException tef) {
      assertThat(wallet.getTransactions().size(), is(1));
      assertThat(wallet.getBalance(), is(BigDecimal.TEN));
    }
  }
}
