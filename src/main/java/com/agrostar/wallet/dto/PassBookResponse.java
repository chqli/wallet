package com.agrostar.wallet.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Data
public class PassBookResponse extends Response {
  List<Txn> transactions;
}
