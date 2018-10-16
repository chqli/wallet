package com.agrostar.wallet.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class PassBookResponse extends Response {
  List<Txn> transactions;
}
