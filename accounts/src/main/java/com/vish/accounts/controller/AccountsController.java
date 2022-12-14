package com.vish.accounts.controller;

import com.vish.accounts.clients.CardsFeignClient;
import com.vish.accounts.clients.LoanFeignClient;
import com.vish.accounts.model.AccountInfo;
import com.vish.accounts.model.CardInfo;
import com.vish.accounts.model.CustomerDetails;
import com.vish.accounts.model.LoanInfo;
import com.vish.accounts.repository.AccountRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {
    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardsFeignClient cardsFeignClient;

    @Autowired
    private LoanFeignClient loanFeignClient;

    @GetMapping("/{customerId}")
    @Timed(value = "getAccountDetails.time", description = "Time taken too to run getAccountDetails")
    public AccountInfo getAccountDetails(@PathVariable int customerId){
        return accountRepository.getByCustomerId(customerId);
    }

    @PostMapping("/{customerId}/allaccounts")
    @CircuitBreaker(name ="customerDetailsFetchCB", fallbackMethod = "customerDetailsFallBack")
    public CustomerDetails getMyCustomerDetails(@PathVariable int customerId){
        logger.info("Customer details fetch started");
        AccountInfo accountInfo = accountRepository.getByCustomerId(customerId);
        List<LoanInfo> loans = loanFeignClient.getLoadDetails(customerId);
        List<CardInfo> cards = cardsFeignClient.getCardDetails(customerId);

        CustomerDetails customerDetails =  new CustomerDetails();
        customerDetails.setAccountInfo(accountInfo);
        customerDetails.setCardInfos(cards);
        customerDetails.setLoanInfos(loans);
        logger.info("Customer details fetch ended");
        return customerDetails;
    }

    public  CustomerDetails customerDetailsFallBack(int customerId, Throwable throwable){
        AccountInfo accountInfo = accountRepository.getByCustomerId(customerId);
        List<LoanInfo> loans = loanFeignClient.getLoadDetails(customerId);
        //List<CardInfo> cards = cardsFeignClient.getCardDetails(customerId);

        CustomerDetails customerDetails =  new CustomerDetails();
        customerDetails.setAccountInfo(accountInfo);
        //customerDetails.setCardInfos(cards);
        customerDetails.setLoanInfos(loans);

        return customerDetails;
    }

    @PostMapping("/{customerId}/accountsall")
    @Retry(name = "retryCustomerDetails", fallbackMethod = "customerDetailsFallBack")
    public  CustomerDetails getAllCustomerAccounts(@PathVariable int customerId){
        AccountInfo accountInfo = accountRepository.getByCustomerId(customerId);
        List<LoanInfo> loans = loanFeignClient.getLoadDetails(customerId);
        List<CardInfo> cards = cardsFeignClient.getCardDetails(customerId);

        CustomerDetails customerDetails =  new CustomerDetails();
        customerDetails.setAccountInfo(accountInfo);
        customerDetails.setCardInfos(cards);
        customerDetails.setLoanInfos(loans);

        return customerDetails;
    }

    AtomicInteger i = new AtomicInteger(0);
    @GetMapping("/sayHello")
    @RateLimiter(name = "sayHelloRateLimiter", fallbackMethod = "sayHelloRateLimiterFallBack")
    public String sayHello(){
        System.out.println(i.addAndGet(1));
        return "Hello Customer welcome!";
    }

    public String sayHelloRateLimiterFallBack(Throwable throwable){
        return "Hello Bank User welcome!";
    }
}
