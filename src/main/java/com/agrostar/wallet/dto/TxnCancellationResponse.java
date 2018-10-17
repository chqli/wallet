package com.agrostar.wallet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class TxnCancellationResponse extends TxnResponse {
  TransactionStatus status;
}
