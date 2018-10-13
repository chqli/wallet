package com.agrostar.wallet.entity;

import com.agrostar.wallet.dto.*;

import javax.persistence.*;

@Entity
public class Transaction {
  @Id private Long tId;
  private TransactionType tType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "OWNER_ID")
  private Wallet wallet;

  private Long amount;
  private TransactionStatus tStatus;
}
