package com.dws.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.AccountAmountLessException;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.repository.AccountsRepository;
import com.dws.challenge.service.AccountsService;
import com.dws.challenge.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AccountsServiceTest {

  @Autowired
  private AccountsService accountsService;


  @Test
  void addAccount() {
    Account account = new Account("Id-123456");
    account.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(account);

    assertThat(this.accountsService.getAccount("Id-123456")).isEqualTo(account);
  }

  @Test
  void addAccount_failsOnDuplicateId() {
    String uniqueId = "Id-" + System.currentTimeMillis();
    Account account = new Account(uniqueId);
    this.accountsService.createAccount(account);

    try {
      this.accountsService.createAccount(account);
      fail("Should have failed when adding duplicate account");
    } catch (DuplicateAccountIdException ex) {
      assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
    }
  }

  @DisplayName("Successfully transfer amount from one account to other")
  @Test
  void validateTransferAmountSuccessfullyTransferAmount() {
    Account fromAccount = new Account("Id-123");
    fromAccount.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(fromAccount);
    Account toAccount = new Account("Id-1345");
    toAccount.setBalance(new BigDecimal(2000));
    this.accountsService.createAccount(toAccount);

    String str=accountsService.transferAmount("Id-123","Id-1345","500");
    assertEquals("Amount is successfully transferred",str);
  }

  @DisplayName("Exception while  transferring amount from one account to other")
  @Test
  void validateTransferAmountForException() {
    Account fromAccount = new Account("Id-1");
    fromAccount.setBalance(new BigDecimal(0));
    this.accountsService.createAccount(fromAccount);
    Account toAccount = new Account("Id-2");
    toAccount.setBalance(new BigDecimal(2000));
    this.accountsService.createAccount(toAccount);

    try {
      String str = accountsService.transferAmount("Id-123", "Id-1345", "500");
    }
    catch(AccountAmountLessException accountAmountLessException)
    {
      assertThat(accountAmountLessException.getMessage()).isEqualTo("Amount cannot be transferred since from account has less amount");
    }
  }


}
