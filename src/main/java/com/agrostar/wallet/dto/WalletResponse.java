package com.agrostar.wallet.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
public class WalletResponse extends Response {
  @Getter BigDecimal amount;
}
