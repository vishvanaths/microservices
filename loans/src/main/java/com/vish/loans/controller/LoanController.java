package com.vish.loans.controller;

import com.vish.loans.model.LoanInfo;
import com.vish.loans.repository.LoanRepository;
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

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/{customerId}/loans")
    public List<LoanInfo> getAccountDetails(@PathVariable int customerId){
        return loanRepository.findByCustomerIdOrderByStartDtDesc(customerId);
    }
}
