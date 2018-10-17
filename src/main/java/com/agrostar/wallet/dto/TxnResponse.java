package com.agrostar.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
@EqualsAndHashCode
public class TxnResponse extends Response {
  Long transactionId;
}
