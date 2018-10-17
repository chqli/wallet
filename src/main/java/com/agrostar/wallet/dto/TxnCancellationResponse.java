package com.agrostar.wallet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Data
public class TxnCancellationResponse extends TxnResponse {
  TransactionStatus status;
}
