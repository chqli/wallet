package com.agrostar.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer walletId;

  public Wallet() {}

  @OneToMany(mappedBy = "wallet")
  private List<Transaction> transactions;
}
