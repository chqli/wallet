package com.agrostar.wallet.controller;

import com.agrostar.wallet.converter.Converters;
import com.agrostar.wallet.dto.*;
import com.agrostar.wallet.entity.Transaction;
import com.agrostar.wallet.entity.Wallet;
import com.agrostar.wallet.exceptions.WalletNotFoundException;
import com.agrostar.wallet.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/wallet/{walletId}/transaction")
public class TransactionController {
  @Autowired TransactionsService service;
  @Autowired Converters converters;

  @RequestMapping(method = RequestMethod.POST, produces = "application/json")
  public TxnResponse createTransaction(@PathVariable Integer walletId, @RequestBody Txn txn) {

    txn.setStatus(TransactionStatus.ACTIVE);
    Transaction newTransaction = service.saveTransaction(walletId, txn);
    return converters.toDto(newTransaction);
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public PassBookResponse getPassBook(@PathVariable Integer walletId) {

    Optional<Wallet> newTransaction = service.getWallet(walletId);
    if (!newTransaction.isPresent()) {
      throw new WalletNotFoundException("Wallet not found");
    }
    Wallet wallet = newTransaction.get();
    return converters.toPassBook(wallet);
  }

  @RequestMapping(
      value = "/{transactionId}",
      method = RequestMethod.DELETE,
      produces = "application/json")
  public TxnResponse deleteTransaction(
      @PathVariable Integer walletId, @PathVariable String transactionId) {

    Transaction newTransaction = service.deleteTransaction(walletId, transactionId);
    return converters.toCancellation(newTransaction);
  }
}
