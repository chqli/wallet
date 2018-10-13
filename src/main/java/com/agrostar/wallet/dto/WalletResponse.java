package com.agrostar.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class WalletResponse {
  @Getter BigDecimal amount;
  Integer walletId;

  static WalletResponse notFound() {
    WalletResponse walletResponse = new WalletResponse();
    walletResponse.amount = BigDecimal.ZERO;
    return walletResponse;
  }
}
