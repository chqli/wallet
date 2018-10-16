package com.agrostar.wallet.controller;

import com.agrostar.wallet.converter.Converters;
import com.agrostar.wallet.dto.WalletResponse;
import com.agrostar.wallet.entity.Wallet;
import com.agrostar.wallet.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/wallet")
public class WalletController {
  @Autowired TransactionsService service;
  @Autowired Converters converters;

  @RequestMapping(method = RequestMethod.POST, produces = "application/json")
  public WalletResponse createWallet() {
    Wallet newWallet = service.saveWallet();
    return converters.convertToDto(Optional.of(newWallet));
  }

  @RequestMapping(value = "/{walletId}", method = RequestMethod.GET, produces = "application/json")
  public WalletResponse getWallet(@PathVariable String walletId) {
    Optional<Wallet> wallet = service.getWallet(walletId);
    WalletResponse walletResponse1 = converters.convertToDto(wallet);
    return walletResponse1;
  }
}
