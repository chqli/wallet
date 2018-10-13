package com.agrostar.wallet.controller;

import com.agrostar.wallet.converter.Converters;
import com.agrostar.wallet.converter.WalletNotFoundException;
import com.agrostar.wallet.dto.Txn;
import com.agrostar.wallet.dto.TxnResponse;
import com.agrostar.wallet.entity.Transaction;
import com.agrostar.wallet.entity.Wallet;
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
    Optional<Wallet> optWallet = service.getWallet(walletId);
    if (!optWallet.isPresent()) {
      throw new WalletNotFoundException();
    }
    Wallet wallet = optWallet.get();
    Transaction newTransaction = service.saveTransaction(wallet, txn);
    return converters.convertToDto(newTransaction);
  }
}
