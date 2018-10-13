package com.agrostar.wallet.entity;

import com.agrostar.wallet.dto.TransactionStatus;
import com.agrostar.wallet.dto.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private TransactionType type;

  public Transaction() {
    status = TransactionStatus.ACTIVE;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "OWNER_ID")
  private Wallet wallet;

  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private TransactionStatus status;
}
