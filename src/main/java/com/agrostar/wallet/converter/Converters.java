package com.agrostar.wallet.converter;

import com.agrostar.wallet.dto.TxnResponse;
import com.agrostar.wallet.dto.WalletResponse;
import com.agrostar.wallet.entity.Transaction;
import com.agrostar.wallet.entity.Wallet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Converters {
  @Autowired ModelMapper modelMapper;

  public WalletResponse convertToDto(Wallet walletEntity, BigDecimal balance) {
    WalletResponse walletResponse = modelMapper.map(walletEntity, WalletResponse.class);
    walletResponse.setMessage(HttpStatus.OK.getReasonPhrase());
    walletResponse.setAmount(balance);
    return walletResponse;
  }

  public TxnResponse convertToDto(Transaction transactionEntity) {

    TxnResponse txnResponse = modelMapper.map(transactionEntity, TxnResponse.class);
    txnResponse.setMessage(HttpStatus.OK.getReasonPhrase());
    return txnResponse;
  }
}
