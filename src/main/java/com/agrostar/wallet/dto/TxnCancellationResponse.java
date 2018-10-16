package com.agrostar.wallet.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class TxnCancellationResponse extends TxnResponse {
  TransactionStatus status;
}
