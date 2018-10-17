package com.agrostar.wallet.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class PassBookResponse extends Response {
  List<Txn> transactions;
}
