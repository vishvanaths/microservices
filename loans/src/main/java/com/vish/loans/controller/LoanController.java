package com.vish.loans.controller;

import com.vish.loans.model.LoanInfo;
import com.vish.loans.repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanController {
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/{customerId}/loans")
    public List<LoanInfo> getAccountDetails(@PathVariable int customerId){
        logger.info("Loan details fetch started");
        return loanRepository.findByCustomerIdOrderByStartDtDesc(customerId);
    }
}
