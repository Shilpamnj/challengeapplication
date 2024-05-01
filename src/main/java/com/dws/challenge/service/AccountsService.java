package com.dws.challenge.service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.service.NotificationService;
import com.dws.challenge.exception.AccountAmountLessException;
import com.dws.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;

  @Getter
  private final NotificationService notificationService;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository,NotificationService notificationService) {
    this.accountsRepository = accountsRepository;
    this.notificationService = notificationService;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }
  public synchronized String  transferAmount(String fromAccountId, String toAccountId, String amount) {
    BigDecimal transferAmount = new BigDecimal(amount);
    Account fromAccount = accountsRepository.getAccount(fromAccountId);

    if(fromAccount.getBalance().compareTo(transferAmount)>0)
    {
      Account toAccount= accountsRepository.getAccount(toAccountId);
      fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
      accountsRepository.updateAccount(fromAccount, fromAccount);

      toAccount.setBalance(toAccount.getBalance().add(transferAmount));
      accountsRepository.updateAccount(toAccount, toAccount);
      notificationService.notifyAboutTransfer(fromAccount,"Amount of "+amount+ " is debited ");
      notificationService.notifyAboutTransfer(toAccount,"Amount of "+amount+ " is credited");
      return "Amount is successfully transferred";
    }
    else {
      throw new AccountAmountLessException("Amount cannot be transferred since from account has less amount");
    }
    }
}
