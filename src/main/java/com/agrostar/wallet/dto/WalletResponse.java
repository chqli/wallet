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
public class WalletResponse extends Response {
  @Getter BigDecimal amount;
}
