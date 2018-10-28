package com.agrostar.wallet.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Amount {
  private BigDecimal amount;
}
