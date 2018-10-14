package com.agrostar.wallet.controller;

import com.agrostar.wallet.converter.Converters;
import com.agrostar.wallet.dto.Txn;
import com.agrostar.wallet.dto.TxnResponse;
import com.agrostar.wallet.entity.Transaction;
import com.agrostar.wallet.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
