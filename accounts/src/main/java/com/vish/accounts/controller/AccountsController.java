package com.vish.accounts.controller;

import com.vish.accounts.clients.CardsFeignClient;
import com.vish.accounts.clients.LoanFeignClient;
import com.vish.accounts.model.AccountInfo;
import com.vish.accounts.model.CardInfo;
import com.vish.accounts.model.CustomerDetails;
import com.vish.accounts.model.LoanInfo;
import com.vish.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardsFeignClient cardsFeignClient;

    @Autowired
    private LoanFeignClient loanFeignClient;

    @GetMapping("/{customerId}")
    public AccountInfo getAccountDetails(@PathVariable int customerId){
        return accountRepository.getByCustomerId(customerId);
    }

    @PostMapping("/{customerId}/allaccounts")
    public  CustomerDetails getMyCustomerDetails(@PathVariable int customerId){
        AccountInfo accountInfo = accountRepository.getByCustomerId(customerId);
        List<LoanInfo> loans = loanFeignClient.getLoadDetails(customerId);
        List<CardInfo> cards = cardsFeignClient.getCardDetails(customerId);

        CustomerDetails customerDetails =  new CustomerDetails();
        customerDetails.setAccountInfo(accountInfo);
        customerDetails.setCardInfos(cards);
        customerDetails.setLoanInfos(loans);

        return customerDetails;
    }
}
