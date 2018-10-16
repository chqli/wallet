package com.agrostar.wallet.converter;

import com.agrostar.wallet.dto.*;
import com.agrostar.wallet.entity.Transaction;
import com.agrostar.wallet.entity.Wallet;
import com.agrostar.wallet.exceptions.WalletNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Converters {
  @Autowired ModelMapper modelMapper;

  public WalletResponse convertToDto(Optional<Wallet> walletEntity) {
    if (!walletEntity.isPresent()) {
      throw new WalletNotFoundException();
    }
    Wallet wallet = walletEntity.get();
    WalletResponse walletResponse = modelMapper.map(wallet, WalletResponse.class);
    walletResponse.setAmount(wallet.getBalance());
    return walletResponse;
  }

  public TxnResponse convertToDto(Transaction transactionEntity) {

    TxnResponse txnResponse = modelMapper.map(transactionEntity, TxnResponse.class);
    return txnResponse;
  }

  public TxnCancellationResponse converttoFullTransactionResponse(Transaction transactionEntity) {

    TxnCancellationResponse txnCancellationResponse = new TxnCancellationResponse();
    txnCancellationResponse.setTransactionId(transactionEntity.getId());
    txnCancellationResponse.setStatus(transactionEntity.getStatus());
    return txnCancellationResponse;
  }

  public PassBookResponse convertToPassBook(Wallet wallet) {
    List<Txn> txns =
        wallet
            .getTransactions()
            .stream()
            .map(t -> new Txn(t.getAmount(), t.getType(), t.getStatus()))
            .collect(Collectors.toList());
    PassBookResponse passBookResponse = new PassBookResponse();
    passBookResponse.setTransactions(txns);
    return passBookResponse;
  }
}
