package com.vish.accounts.controller;

import com.vish.accounts.model.AccountInfo;
import com.vish.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/{customerId}")
    public AccountInfo getAccountDetails(@PathVariable int customerId){
        return accountRepository.getByCustomerId(customerId);
    }
}
