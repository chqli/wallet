package com.agrostar.wallet.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Component
@EqualsAndHashCode
public class WalletResponse extends Response {
  @Getter BigDecimal amount;
}
