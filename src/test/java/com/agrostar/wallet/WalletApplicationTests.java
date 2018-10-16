package com.agrostar.wallet;

import com.agrostar.wallet.dto.TransactionType;
import com.agrostar.wallet.dto.Txn;
import com.agrostar.wallet.dto.TxnResponse;
import com.agrostar.wallet.dto.WalletResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletApplicationTests {

  @Autowired private TestRestTemplate restTemplate;

  public WalletResponse createClient() {
    ResponseEntity<WalletResponse> responseEntity =
        restTemplate.postForEntity("/wallet", null, WalletResponse.class);
    return responseEntity.getBody();
  }

  public BigDecimal getAmountForWallet(String walletId) {
    ResponseEntity<WalletResponse> responseEntity =
        restTemplate.getForEntity("/wallet/" + walletId, WalletResponse.class);
    return responseEntity.getBody().getAmount();
  }

  @Test
  public void contextLoads() {
    WalletResponse wallet = this.createClient();
    assertThat(wallet.getAmount(), CoreMatchers.is(BigDecimal.ZERO));
    ArrayList<Txn> txns1 = new ArrayList<>();
    Txn add10 = new Txn(BigDecimal.TEN, TransactionType.CREDIT);
    List<Txn> txns = batchTransactions(100, BigDecimal.TEN);
    txns1.add(add10);
    txns1.addAll(txns);
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    for (Txn txn : txns1) {
      executorService.submit(
          () -> {
            restTemplate.postForEntity(
                String.format("/wallet/%s/transaction", String.valueOf(wallet.getWalletId())),
                txn,
                TxnResponse.class);
          });
    }
    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(800, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
      }
    } catch (InterruptedException e) {
      executorService.shutdownNow();
    }
    BigDecimal amountForWallet = getAmountForWallet(String.valueOf(wallet.getWalletId()));
    assertThat(amountForWallet, CoreMatchers.is(BigDecimal.TEN));
    System.out.println(amountForWallet);
  }

  public static List<Txn> batchTransactions(int num, BigDecimal amount) {
    ArrayList<Txn> txns = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      if (i % 2 == 0) {
        txns.add(new Txn(amount, TransactionType.CREDIT));
      } else {
        txns.add(new Txn(amount, TransactionType.DEBIT));
      }
    }
    return txns;
  }
}
