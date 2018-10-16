package com.agrostar.wallet.controller;

import com.agrostar.wallet.converter.Converters;
import com.agrostar.wallet.dto.PassBookResponse;
import com.agrostar.wallet.dto.Txn;
import com.agrostar.wallet.dto.TxnCancellationResponse;
import com.agrostar.wallet.dto.TxnResponse;
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
  public TxnResponse createTransaction(@PathVariable String walletId, @RequestBody Txn txn) {

    Transaction newTransaction = service.saveTransaction(walletId, txn);
    return converters.convertToDto(newTransaction);
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public PassBookResponse getPassBook(@PathVariable String walletId) {

    Optional<Wallet> newTransaction = service.getWallet(walletId);
    if (!newTransaction.isPresent()) {
      throw new WalletNotFoundException();
    }
    Wallet wallet = newTransaction.get();
    return converters.convertToPassBook(wallet);
  }

  @RequestMapping(
      value = "/{transactionId}",
      method = RequestMethod.DELETE,
      produces = "application/json")
  public TxnResponse deleteTransaction(
      @PathVariable String walletId, @PathVariable String transactionId) {

    Transaction newTransaction = service.deleteTransaction(walletId, transactionId);
    TxnResponse txnResponse = converters.convertToDto(newTransaction);
    TxnCancellationResponse txnCancellationResponse = new TxnCancellationResponse();
    txnCancellationResponse.setTransactionId(newTransaction.getId());
    txnCancellationResponse.setStatus(newTransaction.getStatus());
    return txnCancellationResponse;
  }
}
