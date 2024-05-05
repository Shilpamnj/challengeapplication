package com.dws.challenge.web;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.service.AccountsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/accounts")
@Slf4j
public class AccountsController {

  private final AccountsService accountsService;

  @Autowired
  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createAccount(@Validated @RequestBody Account account) {

    log.info("Creating account {}", account);

    try {
    this.accountsService.createAccount(account);
    return new ResponseEntity<>("Account created",HttpStatus.CREATED);
    } catch (DuplicateAccountIdException daie) {
      return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping(path = "/{accountId}")
  public Account getAccount(@PathVariable String accountId) {
    log.info("Retrieving account for id {}", accountId);
    return this.accountsService.getAccount(accountId);
  }

  @PostMapping(path = "/transfer-amount")
  public ResponseEntity<String> transferAmount(@RequestParam("fromAccountID") String fromAccountId,@RequestParam("toAccountId") String toAccountId,@RequestParam("amount") String amount) {
    log.info("Transfer amount from account Id {} : to account Id {}",fromAccountId,toAccountId);
    String rst=accountsService.transferAmount(fromAccountId,toAccountId,amount);
    return new ResponseEntity<>(rst,HttpStatus.OK);
  }

   @PostMapping(path = "/transfer-amount1")
  public ResponseEntity<String> transferAmount1(@RequestParam("fromAccountID") String fromAccountId,@RequestParam("toAccountId") String toAccountId,@RequestParam("amount") String amount) {
    log.info("Transfer amount from account Id {} : to account Id {}",fromAccountId,toAccountId);
    accountsService.transferAmountViaThreadPool(fromAccountId,toAccountId,amount);
    return new ResponseEntity<>("Job running to transfer job",HttpStatus.OK);
  }

}
